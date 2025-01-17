package ru.practicum.model.dto.location;

import jakarta.validation.constraints.Positive;
import lombok.Data;
import ru.practicum.utils.NotBlankIfPresent;

@Data
public class UpdateLocationAdminRequest {
    @NotBlankIfPresent
    private String name;
    @Positive
    private Double radius;
    private Double lat;
    private Double lon;
    private Boolean available;
}
