package ru.practicum.model.dto.location;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import ru.practicum.utils.NotBlankIfPresent;

@Data
public class LocationShortResponseDto {
    private Long id;
    @NotBlankIfPresent
    private String name;
    @NotNull
    private Double lat;
    @NotNull
    private Double lon;
}
