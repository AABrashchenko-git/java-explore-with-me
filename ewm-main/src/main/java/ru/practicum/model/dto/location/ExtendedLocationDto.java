package ru.practicum.model.dto.location;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ExtendedLocationDto extends LocationDto {
    @NotBlank
    private String name;
    @NotNull
    private Boolean available;
}
