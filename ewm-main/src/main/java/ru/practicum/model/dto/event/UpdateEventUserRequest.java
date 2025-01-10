package ru.practicum.model.dto.event;

import jakarta.validation.constraints.Size;
import lombok.Data;
import ru.practicum.enums.UserStateAction;
import ru.practicum.model.entity.Location;

import java.time.LocalDateTime;

@Data
public class UpdateEventUserRequest {
    @Size(min = 20, max = 2000)
    private String annotation;
    private Long category;
    @Size(min = 20, max = 7000)
    private String description;
    private LocalDateTime eventDate;
    private Location location;
    private Boolean paid;
    private Integer participantLimit;
    private Boolean requestModeration;
    private UserStateAction stateAction;
    @Size(min = 3, max = 120)
    private String title;
}
