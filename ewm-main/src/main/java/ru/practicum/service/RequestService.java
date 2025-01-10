package ru.practicum.service;

import ru.practicum.model.dto.request.ParticipationRequestDto;

import java.util.List;

public interface RequestService {
    ParticipationRequestDto cancelParticipationRequest(Integer userId, Integer requestId);

    ParticipationRequestDto addParticipationRequest(Integer userId, Integer eventId);

    List<ParticipationRequestDto> getUserRequests(Integer userId);
}
