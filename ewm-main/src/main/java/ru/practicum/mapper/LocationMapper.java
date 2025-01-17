package ru.practicum.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.practicum.model.dto.location.ExtendedLocationDto;
import ru.practicum.model.dto.location.LocationDto;
import ru.practicum.model.dto.location.LocationFullDto;
import ru.practicum.model.dto.location.ShortLocationDto;
import ru.practicum.model.entity.Location;

@Mapper(componentModel = "spring")
public interface LocationMapper {
  //  @Mapping(target = "id", ignore = true)
    @Mapping(target = "name", ignore = true)
    @Mapping(target = "radius", ignore = true)
    @Mapping(target = "available", ignore = true)
    Location dtoToLocation(LocationDto locationDto);

   // @Mapping(target = "id", ignore = true)
   // // @Mapping(target = "available", ignore = true)
    Location extendedDtoToLocation(ExtendedLocationDto extendedLocationDto);

    @Mapping(target = "name", ignore = true)
    @Mapping(target = "radius", ignore = true)
    @Mapping(target = "available", ignore = true)
    LocationDto locationToDto(Location location);

    @Mapping(target = "available", ignore = true)
    ExtendedLocationDto locationToExtendedDto(Location location);

    LocationFullDto toLocationFullDto(Location location);

    ShortLocationDto toShortLocationDto(Location location);
}
