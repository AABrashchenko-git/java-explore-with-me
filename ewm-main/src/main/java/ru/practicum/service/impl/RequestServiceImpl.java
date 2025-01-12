package ru.practicum.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.enums.EventState;
import ru.practicum.enums.RequestStatus;
import ru.practicum.exception.ConflictException;
import ru.practicum.exception.NotFoundException;
import ru.practicum.mapper.RequestMapper;
import ru.practicum.model.dto.request.ParticipationRequestDto;
import ru.practicum.model.entity.Event;
import ru.practicum.model.entity.ParticipationRequest;
import ru.practicum.model.entity.User;
import ru.practicum.repository.ParticipationRequestRepository;
import ru.practicum.repository.UserRepository;
import ru.practicum.repository.event.EventRepository;
import ru.practicum.service.RequestService;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RequestServiceImpl implements RequestService {
    private final ParticipationRequestRepository requestRepository;
    private final UserRepository userRepository;
    private final EventRepository eventRepository;
    private final RequestMapper requestMapper;
    @Override
    @Transactional
    public ParticipationRequestDto cancelParticipationRequest(Long userId, Long requestId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User is not found"));
        ParticipationRequest request = requestRepository.findById(requestId)
                .orElseThrow(() -> new NotFoundException("Request is not found"));

        Event event = request.getEvent();
        request.setStatus(RequestStatus.CANCELED);
        event.setConfirmedRequests(event.getConfirmedRequests()-1);

        return requestMapper.toDto(requestRepository.save(request));
    }

    @Override
    @Transactional
    public ParticipationRequestDto addParticipationRequest(Long userId, Long eventId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User is not found"));
        Event event = eventRepository.findById(eventId).orElseThrow(() -> new NotFoundException("Event is not found"));
        List<ParticipationRequest> requests = requestRepository.findAllByEventId(eventId);
        boolean isUserRequesterAlready = requests.stream()
                .map(r -> r.getRequester().getId()).anyMatch(id -> id.equals(userId));
        if(isUserRequesterAlready)
            throw new ConflictException("нельзя добавить повторный запрос");
        if(event.getInitiator().getId().equals(userId))
            throw new ConflictException("инициатор события не может добавить запрос на участие в своём событии");
        if(!event.getState().equals(EventState.PUBLISHED))
            throw new ConflictException("нельзя участвовать в неопубликованном событии");
        if(event.getParticipantLimit().equals(event.getConfirmedRequests()))
            throw new ConflictException("у события достигнут лимит запросов на участие");
        ParticipationRequest newRequest = ParticipationRequest.builder()
                .created(LocalDateTime.now())
                .event(event)
                .requester(user).build();

        if(!event.getRequestModeration()) {
            newRequest.toBuilder().status(RequestStatus.CONFIRMED);
        } else {
            newRequest.toBuilder().status(RequestStatus.PENDING);
        }
        return requestMapper.toDto(requestRepository.save(newRequest));
    }

    @Override
    public List<ParticipationRequestDto> getUserRequests(Long userId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User is not found"));
        return requestRepository.findDtoByUserId(userId);
    }
}
