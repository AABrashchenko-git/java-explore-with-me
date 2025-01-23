package ru.practicum.model.dto.location;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class NewAdminLocationDto {
    private Long id;
    @NotNull
    private Double lat;
    @NotNull
    private Double lon;
    @NotBlank
    private String name;
    @NotNull
    private Boolean available;
}
