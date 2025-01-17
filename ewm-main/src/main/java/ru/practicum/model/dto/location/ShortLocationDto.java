package ru.practicum.model.dto.location;

import lombok.Data;

@Data
public class ShortLocationDto {
    private Long id;
    private String name;
    private Double lat;
    private Double lon;
}
