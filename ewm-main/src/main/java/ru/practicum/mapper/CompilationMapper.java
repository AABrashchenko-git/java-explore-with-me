package ru.practicum.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import ru.practicum.model.dto.compilation.CompilationDto;
import ru.practicum.model.dto.compilation.NewCompilationDto;
import ru.practicum.model.entity.Compilation;
import ru.practicum.model.entity.Event;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", uses = {EventMapper.class})
public interface CompilationMapper {

/*    @Mapping(target = "events", source = "events", qualifiedByName = "mapEvents")
    Compilation toEntity(NewCompilationDto newCompilationDto);*/

    CompilationDto toDto(Compilation compilation);

/*    @Named("mapEvents")
    default List<Event> mapEvents(List<Long> eventIds) {
        if (eventIds == null) {
            return Collections.emptyList();
        }
        return eventIds.stream()
                .map(eventId -> {
                    Event event = new Event();
                    event.setId(eventId);
                    return event;
                })
                .collect(Collectors.toList());
    }*/
}
