package ru.practicum.model.entity;


import lombok.Getter;
import lombok.Setter;
import ru.practicum.enums.RequestStatus;

import java.time.LocalDateTime;

@Getter
@Setter
public class ParticipationRequest {
    private Long id;
    private LocalDateTime created;
    private Event event;
    private User requester;
    private RequestStatus status;
}
