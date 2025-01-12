package ru.practicum.service;

import ru.practicum.model.dto.request.ParticipationRequestDto;

import java.util.List;

public interface RequestService {
    ParticipationRequestDto cancelParticipationRequest(Long userId, Long requestId);

    ParticipationRequestDto addParticipationRequest(Long userId, Long eventId);

    List<ParticipationRequestDto> getUserRequests(Long userId);
}
