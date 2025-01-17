package ru.practicum.model.dto.location;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import ru.practicum.utils.LocationDtoDeserializer;

@Data
@JsonDeserialize(using = LocationDtoDeserializer.class)
public class LocationDto {
    private Long id;
    @NotNull
    private Double lat;
    @NotNull
    private Double lon;
}
