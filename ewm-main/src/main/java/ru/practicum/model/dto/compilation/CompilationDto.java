package ru.practicum.model.dto.compilation;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import ru.practicum.model.dto.event.EventShortDto;

import java.util.List;

@Data
public class CompilationDto {
    private Long id;
    @NotBlank
    @Size(min = 1, max = 50)
    private String title;
    @NotNull
    private Boolean pinned;
    private List<EventShortDto> events;
}
