package ru.practicum.model.dto.event;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Size;
import lombok.Data;
import ru.practicum.enums.AdminStateAction;
import ru.practicum.model.dto.location.LocationDto;
import ru.practicum.utils.NotBlankIfPresent;

import java.time.LocalDateTime;

import static ru.practicum.config.DateTimeFormats.DATE_TIME_PATTERN;

@Data
public class UpdateEventAdminRequest {
    @Size(min = 20, max = 2000)
    @NotBlankIfPresent
    private String annotation;
    private Long category;
    @Size(min = 20, max = 7000)
    @NotBlankIfPresent
    private String description;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_TIME_PATTERN)
    @Future
    private LocalDateTime eventDate;
    private LocationDto location;
    private Boolean paid;
    private Integer participantLimit;
    private Boolean requestModeration;
    private AdminStateAction stateAction;
    @Size(min = 3, max = 120)
    @NotBlankIfPresent
    private String title;
}