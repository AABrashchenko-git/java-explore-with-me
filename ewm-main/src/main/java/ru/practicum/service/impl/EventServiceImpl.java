package ru.practicum.service.impl;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.client.StatsClient;
import ru.practicum.dto.EndpointHitDto;
import ru.practicum.enums.AdminStateAction;
import ru.practicum.enums.EventState;
import ru.practicum.enums.RequestStatus;
import ru.practicum.enums.UserStateAction;
import ru.practicum.exception.*;
import ru.practicum.mapper.EventMapper;
import ru.practicum.mapper.LocationMapper;
import ru.practicum.mapper.RequestMapper;
import ru.practicum.model.dto.event.*;
import ru.practicum.model.dto.location.LocationDto;
import ru.practicum.model.dto.request.EventRequestStatusUpdateRequest;
import ru.practicum.model.dto.request.EventRequestStatusUpdateResult;
import ru.practicum.model.dto.request.ParticipationRequestDto;
import ru.practicum.model.entity.*;
import ru.practicum.repository.CategoryRepository;
import ru.practicum.repository.ParticipationRequestRepository;
import ru.practicum.repository.UserRepository;
import ru.practicum.repository.event.EventAdminSpecifications;
import ru.practicum.repository.event.EventPublicSpecifications;
import ru.practicum.repository.event.EventRepository;
import ru.practicum.repository.location.LocationRepository;
import ru.practicum.service.EventService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import static ru.practicum.config.DateTimeFormats.DATE_TIME_PATTERN;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EventServiceImpl implements EventService {
    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final ParticipationRequestRepository requestRepository;
    private final LocationRepository locationRepository;
    private final EventMapper eventMapper;
    private final LocationMapper locationMapper;
    private final RequestMapper requestMapper;
    private final StatsClient statsClient;

    @Override
    public List<EventShortDto> getPrivateUserEvents(Long userId, Integer from, Integer size) {
        Pageable pageable = PageRequest.of(from / size, size);
        Page<Event> eventsPage = eventRepository.findByInitiatorId(userId, pageable);

        return eventsPage.getContent().stream()
                .map(eventMapper::toEventShortDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public EventFullDto addPrivateUserEvent(Long userId, NewEventDto newEventDto) {
        if (newEventDto.getEventDate().isBefore(LocalDateTime.now().plusHours(2)))
            throw new TimestampViolationException("Время события должно быть не менее, чем через 2 часа");
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("user is not Found"));
        Category category = categoryRepository.findById(newEventDto.getCategory())
                .orElseThrow(() -> new NotFoundException("category is not found"));

        Location location;
        Object locationInput = newEventDto.getLocation();

        if (locationInput instanceof Long locationId) {
            location = locationRepository.findById(locationId)
                    .orElseThrow(() -> new NotFoundException("Location not found with id = " + locationId));
        } else {
            LocationDto locationDto = (LocationDto) locationInput;
            location = locationMapper.dtoToLocation(locationDto);
            location = locationRepository.save(location);
        }

        Event eventToSave = eventMapper.toEntity(newEventDto);
        eventToSave.setInitiator(user);
        eventToSave.setCategory(category);
        eventToSave.setLocation(location);
        eventToSave.setViews(0L);
        eventToSave.setConfirmedRequests(0L);
        return eventMapper.toEventFullDto(eventRepository.save(eventToSave));
    }

    @Override
    public EventFullDto getPrivateUserEventById(Long userId, Long eventId) {
        userExistsCheck(userId);
        Event event = findEventIfExists(userId, eventId);
        return eventMapper.toEventFullDto(event);
    }

    @Override
    @Transactional
    public EventFullDto editPrivateUserEvent(Long userId, Long eventId, UpdateEventUserRequest request) {
        userExistsCheck(userId);
        if ((request.getEventDate() != null) && request.getEventDate().isBefore(LocalDateTime.now().plusHours(2)))
            throw new TimestampViolationException("Время события должно быть не менее, чем через 2 часа");
        if ((request.getParticipantLimit() != null) && (request.getParticipantLimit() < 0))
            throw new BadRequestException("некорректное значение лимита участников");
        Event event = findEventIfExists(userId, eventId);
        cheeckIfUserIsInitiator(userId, event);

        if (!EventState.PENDING.equals(event.getState()) && !EventState.CANCELED.equals(event.getState()))
            throw new ConflictException("изменить можно только отмененные события или в состоянии ожидания модерации");

        eventMapper.updateEventFromDtoByUser(request, event);

        if (request.getLocation() != null) {
            Location location = event.getLocation();
            location.setLat(request.getLocation().getLat());
            location.setLon(request.getLocation().getLon());
        }

        if (request.getStateAction() != null) {
            if (request.getStateAction().equals(UserStateAction.SEND_TO_REVIEW)) {
                event.setState(EventState.PENDING);
            } else {
                event.setState(EventState.CANCELED);
            }
        }
        return eventMapper.toEventFullDto(event);
    }

    @Override
    public List<ParticipationRequestDto> getPrivateUserParticipationRequest(Long userId, Long eventId) {
        cheeckIfUserIsInitiator(userId, findEventIfExists(userId, eventId));
        List<ParticipationRequest> requests = requestRepository.findAllByEventInitiatorId(userId);
        return requests.stream()
                .map(requestMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public EventRequestStatusUpdateResult updateEventRequestStatusPrivate(Long userId, Long eventId,
                                                                          EventRequestStatusUpdateRequest request) {
        Event event = findEventIfExists(userId, eventId);

        if (event.getParticipantLimit() == 0 || !event.getRequestModeration())
            throw new ConflictException("Лимит заявок равен 0 или отключена премодерация заявок");

        if (event.getParticipantLimit().equals(event.getConfirmedRequests()))
            throw new ConflictException(String.format("Event with id=%d has reached participant limit", eventId));

        List<ParticipationRequest> requestsToUpdate = requestRepository.findAllByEventInitiatorId(userId).stream()
                .filter(r -> request.getRequestIds().contains(r.getId())).toList();

        if (request.getStatus().equals(RequestStatus.REJECTED)) {
            if (requestsToUpdate.stream()
                    .anyMatch(r -> r.getStatus().equals(RequestStatus.CONFIRMED))) {
                throw new ConflictException("статус можно изменить только у заявок, находящихся в состоянии ожидания");
            }
        }
        if (request.getStatus().equals(RequestStatus.CONFIRMED)
                && (event.getParticipantLimit() - event.getConfirmedRequests()) < (requestsToUpdate.size())) {
            throw new ConflictException("лимит заявок для события исчерпан");
        }

        requestsToUpdate.forEach(r -> r.setStatus(request.getStatus()));

        EventRequestStatusUpdateResult result = new EventRequestStatusUpdateResult();

        if (request.getStatus().equals(RequestStatus.CONFIRMED)) {
            event.setConfirmedRequests(event.getConfirmedRequests() + request.getRequestIds().size());
            result.setConfirmedRequests(requestsToUpdate.stream()
                    .map(requestMapper::toDto).collect(Collectors.toList()));
        }

        if (request.getStatus().equals(RequestStatus.REJECTED)) {
            result.setRejectedRequests(requestsToUpdate.stream()
                    .map(requestMapper::toDto)
                    .collect(Collectors.toList()));
        }

        return result;
    }

    @Override
    @Transactional
    public EventFullDto updateEventByAdmin(Long eventId, UpdateEventAdminRequest request) {
        if ((request.getEventDate() != null) && request.getEventDate().isBefore(LocalDateTime.now().plusHours(1)))
            throw new ConflictException("Время события должно быть не менее, чем через 1 час");

        Event event = eventRepository.findById(eventId).orElseThrow(() -> new NotFoundException("event is not found"));

        if ((request.getStateAction() != null) && request.getStateAction().equals(AdminStateAction.PUBLISH_EVENT)) {
            if (event.getState().equals(EventState.PUBLISHED) || event.getState().equals(EventState.CANCELED)) {
                throw new ConflictException("событие можно публиковать, только если оно в состоянии ожидания публикации");
            }
            event.setState(EventState.PUBLISHED);
            event.setPublishedOn(LocalDateTime.now());
        }
        if ((request.getStateAction() != null) && request.getStateAction().equals(AdminStateAction.REJECT_EVENT)) {
            if (event.getState().equals(EventState.PUBLISHED))
                throw new ConflictException("событие можно отклонить, только если оно еще не опубликовано");

            event.setState(EventState.CANCELED);
        }
        eventMapper.updateEventFromDtoByAdmin(request, event);
        if (request.getLocation() != null) {
            Location location = event.getLocation();
            location.setLat(request.getLocation().getLat());
            location.setLon(request.getLocation().getLon());
        }

        return eventMapper.toEventFullDto(event);
    }

    @Override
    public List<EventFullDto> getEventsByAdmin(List<Long> users, List<String> states, List<Long> categories,
                                               String rangeStart, String rangeEnd, Integer from, Integer size) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_TIME_PATTERN);
        LocalDateTime start = rangeStart != null ? LocalDateTime.parse(rangeStart, formatter) : null;
        LocalDateTime end = rangeEnd != null ? LocalDateTime.parse(rangeEnd, formatter) : null;

        Specification<Event> spec = Specification.where(EventAdminSpecifications.withUsers(users))
                .and(EventAdminSpecifications.withStates(states))
                .and(EventAdminSpecifications.withCategories(categories))
                .and(EventAdminSpecifications.withRangeStart(start))
                .and(EventAdminSpecifications.withRangeEnd(end));

        Pageable pageable = PageRequest.of(from / size, size);

        List<Event> events = eventRepository.findAll(spec, pageable).getContent();

        return events.stream()
                .map(eventMapper::toEventFullDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<EventShortDto> getPublicEvents(String text, List<Long> categories, Boolean paid, String rangeStart,
                                               String rangeEnd, Boolean onlyAvailable, String sort, Integer from,
                                               Integer size, HttpServletRequest request) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_TIME_PATTERN);
        LocalDateTime start = rangeStart != null ? LocalDateTime.parse(rangeStart, formatter) : null;
        LocalDateTime end = rangeEnd != null ? LocalDateTime.parse(rangeEnd, formatter) : null;

        if (start == null && end == null)
            start = LocalDateTime.now();

        if (start != null && end != null && start.isAfter(end))
            throw new BadRequestException("Дата окончания должна быть позже, чем дата начала");

        Specification<Event> spec = Specification.where(EventPublicSpecifications.withText(text))
                .and(EventPublicSpecifications.withCategories(categories))
                .and(EventPublicSpecifications.withPaid(paid))
                .and(EventPublicSpecifications.withRangeStart(start))
                .and(EventPublicSpecifications.withRangeEnd(end))
                .and(EventPublicSpecifications.withOnlyAvailable(onlyAvailable))
                .and(EventPublicSpecifications.withPublishedState());

        Sort sorting = Sort.unsorted();
        if ("EVENT_DATE".equals(sort)) {
            sorting = Sort.by(Sort.Direction.ASC, "eventDate");
        } else if ("VIEWS".equals(sort)) {
            sorting = Sort.by(Sort.Direction.DESC, "views");
        }
        Pageable pageable = PageRequest.of(from / size, size, sorting);

        List<Event> events = eventRepository.findAll(spec, pageable).getContent();
        sendStatsForEventList(request);
        return events.stream()
                .peek(e -> e.setViews(e.getViews() + 1))
                .map(eventMapper::toEventShortDto)
                .collect(Collectors.toList());
    }

    @Override
    public EventFullDto getPublicEventById(Long id, HttpServletRequest request) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Event is not found"));
        if (!event.getState().equals(EventState.PUBLISHED))
            throw new InvalidStateException("Event is not published");
        event.setViews(event.getViews() + 1);
        sendStatsForEvent(event.getId(), request);
        return eventMapper.toEventFullDto(event);
    }

    private void userExistsCheck(Long userId) {
        if (!userRepository.existsById(userId))
            throw new NotFoundException("user is not Found");
    }

    private Event findEventIfExists(Long userId, Long eventId) {
        return eventRepository.findEventByIdAndInitiatorId(eventId, userId)
                .orElseThrow(() -> new NotFoundException("Event is not found or You are not the initiator of this event"));
    }

    private void cheeckIfUserIsInitiator(Long userId, Event event) {
        if (!event.getInitiator().getId().equals(userId))
            throw new ForbiddenException("You are not the initiator of this event");
    }

    private void sendStatsForEvent(Long eventId, HttpServletRequest request) {
        EndpointHitDto hit = EndpointHitDto.builder()
                .app("main-service")
                .uri("/events/" + eventId)
                .ip(request.getRemoteAddr())
                .timestamp(LocalDateTime.now().format(DateTimeFormatter.ofPattern(DATE_TIME_PATTERN))).build();

        statsClient.addStats(hit);
    }

    private void sendStatsForEventList(HttpServletRequest request) {
        EndpointHitDto hit = EndpointHitDto.builder()
                .app("main-service")
                .uri("/events")
                .ip(request.getRemoteAddr())
                .timestamp(LocalDateTime.now().format(DateTimeFormatter.ofPattern(DATE_TIME_PATTERN))).build();

        statsClient.addStats(hit);
    }
}
