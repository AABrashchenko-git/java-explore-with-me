package ru.practicum.model.dto.event;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.Data;
import ru.practicum.enums.EventState;
import ru.practicum.model.dto.category.CategoryDto;
import ru.practicum.model.dto.location.LocationDto;
import ru.practicum.model.dto.user.UserShortDto;

import java.time.LocalDateTime;

import static ru.practicum.config.DateTimeFormats.DATE_TIME_PATTERN;

@Data
public class EventFullDto {
    private Long id;
    @NotBlank
    @Size(min = 20, max = 2000)
    private String annotation;
    @NotNull
    private CategoryDto category;
    private Long confirmedRequests;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_TIME_PATTERN)
    private LocalDateTime createdOn;
    @NotBlank
    @Size(min = 20, max = 7000)
    private String description;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_TIME_PATTERN)
    @NotNull
    private LocalDateTime eventDate;
    @NotNull
    private UserShortDto initiator;
    @NotNull
    private LocationDto location;
    @NotNull
    private Boolean paid;
    @PositiveOrZero
    private Integer participantLimit;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_TIME_PATTERN)
    private LocalDateTime publishedOn;
    @NotNull
    private Boolean requestModeration;
    @NotNull
    private EventState state;
    @NotBlank
    @Size(min = 3, max = 120)
    private String title;
    @NotNull
    private Long views;
}
