package ru.practicum.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.practicum.model.dto.request.ParticipationRequestDto;
import ru.practicum.model.entity.ParticipationRequest;

@Mapper(componentModel = "spring", uses = {UserMapper.class, EventMapper.class})
public interface RequestMapper {

    @Mapping(target = "created", expression = "java(java.time.LocalDateTime.now())")
    ParticipationRequest toEntity(ParticipationRequestDto participationRequestDto);

    @Mapping(source = "event.id", target = "event")
    @Mapping(source = "requester.id", target = "requester")
    ParticipationRequestDto toDto(ParticipationRequest request);
}