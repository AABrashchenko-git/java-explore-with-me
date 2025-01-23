package ru.practicum.model.dto.location;

import lombok.Data;
import ru.practicum.utils.NotBlankIfPresent;

@Data
public class UpdateLocationAdminRequest {
    @NotBlankIfPresent
    private String name;
    private Double lat;
    private Double lon;
    private Boolean available;
}
