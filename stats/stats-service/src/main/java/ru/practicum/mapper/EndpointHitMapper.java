package ru.practicum.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.practicum.dto.EndpointHitDto;
import ru.practicum.model.EndpointHit;

import static ru.practicum.config.DateTimeFormats.DATE_TIME_PATTERN;

@Mapper(componentModel = "spring")
public interface EndpointHitMapper {

    @Mapping(source = "timestamp", target = "timestamp", dateFormat = DATE_TIME_PATTERN)
    EndpointHit hitDtoToEntity(EndpointHitDto endpointHitDto);

    @Mapping(source = "timestamp", target = "timestamp", dateFormat = DATE_TIME_PATTERN)
    EndpointHitDto endpointHitToDto(EndpointHit endpointHit);
}
