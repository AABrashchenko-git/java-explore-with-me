package ru.practicum.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.model.dto.request.ParticipationRequestDto;
import ru.practicum.repository.ParticipationRequestRepository;
import ru.practicum.service.RequestService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RequestServiceImpl implements RequestService {
    private final ParticipationRequestRepository requestRepository;
    @Override
    public ParticipationRequestDto cancelParticipationRequest(Integer userId, Integer requestId) {
        return null;
    }

    @Override
    public ParticipationRequestDto addParticipationRequest(Integer userId, Integer eventId) {
        return null;
    }

    @Override
    public List<ParticipationRequestDto> getUserRequests(Integer userId) {
        return null;
    }
}
