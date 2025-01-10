package ru.practicum.model.dto.compilation;

import lombok.Data;
import ru.practicum.model.dto.event.EventShortDto;

import java.util.List;

@Data
public class CompilationDto {
    private Long id;
    private String title;
    private Boolean pinned;
    private List<EventShortDto> events;
}
