package ru.practicum.controller.privateApi;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.model.dto.event.EventFullDto;
import ru.practicum.model.dto.event.EventShortDto;
import ru.practicum.model.dto.event.NewEventDto;
import ru.practicum.model.dto.event.UpdateEventUserRequest;
import ru.practicum.model.dto.request.EventRequestStatusUpdateRequest;
import ru.practicum.model.dto.request.EventRequestStatusUpdateResult;
import ru.practicum.model.dto.request.ParticipationRequestDto;
import ru.practicum.service.EventService;

import java.util.List;

@RestController
@Slf4j
@RequestMapping(path = "/users/{userId}/events")
@RequiredArgsConstructor
public class PrivateEventsController {
    private final EventService eventService;

    @GetMapping
    public List<EventShortDto> getPrivateUserEvents(@PathVariable Long userId,
                                                    @RequestParam(defaultValue = "0") Integer from,
                                                    @RequestParam(defaultValue = "10") Integer size) {
        log.info("GET /users/{}/events from {} sized {}  is accessed", userId, from, size);
        return eventService.getPrivateUserEvents(userId, from, size);
    }

    @PostMapping
    public EventFullDto addPrivateUserEvent(@PathVariable Long userId,
                                            @RequestBody @Valid NewEventDto newEventDto) {
        log.info("POST /users/{}/events : {} is accessed", userId, newEventDto);
        return eventService.addPrivateUserEvent(userId, newEventDto);
    }

    @GetMapping("/{eventId}")
    public EventFullDto getPrivateUserEventById(@PathVariable Long userId, @PathVariable Long eventId) {
        log.info("GET /users/{}/events/{}  is accessed", userId, eventId);
        return eventService.getPrivateUserEventById(userId, eventId);
    }

    @PatchMapping("/{eventId}")
    public EventFullDto editPrivateUserEvent(@PathVariable Long userId, @PathVariable Long eventId,
                                             @Valid @RequestBody UpdateEventUserRequest request) {
        log.info("PATCH /users/{}/events/{}  is accessed: {}", userId, eventId, request);
        return eventService.editPrivateUserEvent(userId, eventId, request);
    }

    @GetMapping("/{eventId}/requests")
    public List<ParticipationRequestDto> getPrivateUserParticipationRequest(@PathVariable Long userId,
                                                                      @PathVariable Long eventId) {
        log.info("GET /users/{}/events/{}/requests is accessed", userId, eventId);
        return eventService.getPrivateUserParticipationRequest(userId, eventId);
    }

    @PatchMapping("/{eventId}/requests")
    public EventRequestStatusUpdateResult updateEventRequestStatusPrivate(@Valid @RequestBody EventRequestStatusUpdateRequest request,
                                                                          @PathVariable Long userId,
                                                                          @PathVariable Long eventId) {
        log.info("PATCH /users/{}/events/{}/requests is accessed: {}", userId, eventId, request);
        return eventService.updateEventRequestStatusPrivate(userId, eventId, request);
    }
}
