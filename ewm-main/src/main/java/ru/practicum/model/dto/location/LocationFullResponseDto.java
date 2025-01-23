package ru.practicum.model.dto.location;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import ru.practicum.utils.NotBlankIfPresent;

@Data
public class LocationFullResponseDto {
    private Long id;
    @NotBlankIfPresent
    private String name;
    private Boolean available;
    @NotNull
    private Double lat;
    @NotNull
    private Double lon;
}
