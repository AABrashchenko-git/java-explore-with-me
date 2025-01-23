package ru.practicum.model.dto.location;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LocationFullResponseDto {
    private Long id;
    @NotBlank
    private String name;
    @NotNull
    private Boolean available;
    @NotNull
    private Double lat;
    @NotNull
    private Double lon;
}
