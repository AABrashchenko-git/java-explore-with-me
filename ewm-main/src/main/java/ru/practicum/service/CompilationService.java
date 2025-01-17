package ru.practicum.service;

import ru.practicum.model.dto.compilation.CompilationDto;
import ru.practicum.model.dto.compilation.NewCompilationDto;
import ru.practicum.model.dto.compilation.UpdateCompilationRequest;

import java.util.List;

public interface CompilationService {
    List<CompilationDto> getCompilationsPublic(Integer from, Integer size, Boolean pinned);

    CompilationDto getCompilationsByIdPublic(Long compId);

    CompilationDto addCompilationByAdmin(NewCompilationDto newCompilationDto);

    void deleteCompilationByAdmin(Long compId);

    CompilationDto updateCompilationByAdmin(Long compId, UpdateCompilationRequest request);
}
