package ru.practicum.mapper;

import org.mapstruct.Mapper;
import ru.practicum.model.dto.location.LocationDto;
import ru.practicum.model.entity.Location;

@Mapper(componentModel = "spring")
public interface LocationMapper {
    Location dtoToLocation(LocationDto locationDto);
    LocationDto locationToDto(Location location);
}
