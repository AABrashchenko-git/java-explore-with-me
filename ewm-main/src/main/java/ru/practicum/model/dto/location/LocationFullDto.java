package ru.practicum.model.dto.location;

import jakarta.validation.constraints.Positive;
import ru.practicum.utils.NotBlankIfPresent;

public class LocationFullDto {
    private Long id;
    @NotBlankIfPresent
    private String name;
    @Positive
    private Double radius;
    private Double lat;
    private Double lon;
}
