package ru.practicum.service;

import ru.practicum.dto.ViewStatsDto;
import ru.practicum.model.dto.event.*;
import ru.practicum.model.dto.request.EventRequestStatusUpdateRequest;
import ru.practicum.model.dto.request.EventRequestStatusUpdateResult;
import ru.practicum.model.dto.request.ParticipationRequestDto;

import java.util.List;

public interface EventService {
    List<EventShortDto> getPrivateUserEvents(Long userId, Integer from, Integer size);

    EventFullDto addPrivateUserEvent(Long userId, NewEventDto newEventDto);

    EventFullDto getPrivateUserEventById(Long userId, Long eventId);

    EventFullDto editPrivateUserEvent(Long userId, Long eventId, UpdateEventUserRequest request);

    List<ParticipationRequestDto> getPrivateUserParticipationRequest(Long userId, Long eventId);

    EventRequestStatusUpdateResult updateEventRequestStatusPrivate(Long userId, Long eventId,
                                                                   EventRequestStatusUpdateRequest request);

    EventFullDto updateEventByAdmin(Long eventId, UpdateEventAdminRequest request);

    List<EventFullDto> getEventsByAdmin(List<Long> users, List<String> states, List<Long> categories,
                                        String rangeStart, String rangeEnd, Integer from, Integer size);

    List<EventShortDto> getPublicEvents(String text, List<Long> categories, Boolean paid, String rangeStart,
                                        String rangeEnd, Boolean onlyAvailable, String sort, Integer from, Integer size);

    EventFullDto getPublicEventById(Long id);
}
