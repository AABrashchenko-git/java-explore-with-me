package ru.practicum.model.dto.compilation;

import lombok.Data;
import jakarta.validation.constraints.Size;
import ru.practicum.utils.NotBlankIfPresent;

import java.util.List;

@Data
public class UpdateCompilationRequest {
    private List<Long> events;
    private Boolean pinned;
    @Size(min = 1, max = 50)
    @NotBlankIfPresent
    private String title;
}
