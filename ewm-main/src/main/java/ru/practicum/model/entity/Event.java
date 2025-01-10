package ru.practicum.model.entity;


import lombok.Getter;
import lombok.Setter;
import ru.practicum.enums.EventState;

import java.time.LocalDateTime;

@Getter
@Setter
public class Event {
    private Long id;
    private String annotation;
    private Long confirmedRequests;
    private LocalDateTime createdOn;
    private String description;
    private LocalDateTime eventDate;
    private Boolean paid;
    private Long participantLimit;
    private LocalDateTime publishedOn;
    private Boolean requestModeration;
    private String title;
    private Long views;
    private Category category;
    private User initiator;
    private Location location;
    private EventState state;

}
