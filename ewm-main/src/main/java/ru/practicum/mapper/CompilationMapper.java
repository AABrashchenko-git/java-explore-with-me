package ru.practicum.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.Context;
import org.mapstruct.factory.Mappers;
import ru.practicum.model.dto.compilation.CompilationDto;
import ru.practicum.model.dto.compilation.NewCompilationDto;
import ru.practicum.model.dto.event.EventShortDto;
import ru.practicum.model.entity.Compilation;
import ru.practicum.model.entity.Event;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", uses = {EventMapper.class})
public interface CompilationMapper {
    Compilation toEntity(NewCompilationDto newCompilationDto);

    @Mapping(source = "events", target = "events", qualifiedByName = "mapEventsToShortDto")
    CompilationDto toDto(Compilation compilation, @Context EventMapper eventMapper);

    @Named("mapEventsToShortDto")
    default List<EventShortDto> mapEventsToShortDto(Set<Event> events, @Context EventMapper eventMapper) {
        if (events == null) return Collections.emptyList();
        return events.stream()
                .map(eventMapper::toEventShortDto)
                .collect(Collectors.toList());
    }
}
