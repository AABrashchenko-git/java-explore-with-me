package ru.practicum.model.dto.location;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LocationShortResponseDto {
    private Long id;
    @NotBlank
    private String name;
    @NotNull
    private Double lat;
    @NotNull
    private Double lon;
}
