package ru.practicum.controller.privateApi;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.model.dto.request.ParticipationRequestDto;
import ru.practicum.service.RequestService;

import java.util.List;

@RestController
@Slf4j
@RequestMapping(path = "/users/{userId}/requests")
@RequiredArgsConstructor
public class PrivateRequestsController {
    private final RequestService requestService;

    @GetMapping
    public List<ParticipationRequestDto> getUserRequests(@PathVariable Integer userId) {
        log.info("GET /users/{}/requests is accessed", userId);
        return requestService.getUserRequests(userId);
    }

    @PostMapping
    public ParticipationRequestDto addParticipationRequest(@PathVariable Integer userId,
                                                           @RequestParam Integer eventId) {
        log.info("POST /users/{}/requests is accessed", userId);
        return requestService.addParticipationRequest(userId, eventId);
    }

    @PatchMapping("/{requestId}/cancel")
    public ParticipationRequestDto cancelParticipationRequest(@PathVariable Integer userId,
                                                              @PathVariable Integer requestId) {
        log.info("PATCH /users/{}/requests/{}/cancel is accessed", userId, requestId);
        return requestService.cancelParticipationRequest(userId, requestId);
    }
}
