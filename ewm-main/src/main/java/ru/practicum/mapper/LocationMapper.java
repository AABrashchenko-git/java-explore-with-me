package ru.practicum.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.practicum.model.dto.location.ExtendedLocationDto;
import ru.practicum.model.dto.location.LocationDto;
import ru.practicum.model.dto.location.LocationFullResponseDto;
import ru.practicum.model.dto.location.LocationShortResponseDto;
import ru.practicum.model.entity.Location;

@Mapper(componentModel = "spring")
public interface LocationMapper {
    @Mapping(target = "name", ignore = true)
    @Mapping(target = "available", ignore = true)
    Location dtoToLocation(LocationDto locationDto);

    Location extendedDtoToLocation(ExtendedLocationDto extendedLocationDto);

    LocationDto locationToDto(Location location);

    ExtendedLocationDto locationToExtendedDto(Location location);

    LocationFullResponseDto toLocationFullResponseDto(Location location);

    LocationShortResponseDto toLocationShortResponseDto(Location location);
}
