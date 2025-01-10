package ru.practicum.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.dto.ViewStatsDto;
import ru.practicum.model.dto.compilation.CompilationDto;
import ru.practicum.model.dto.compilation.NewCompilationDto;
import ru.practicum.model.dto.compilation.UpdateCompilationRequest;
import ru.practicum.repository.CompilationRepository;
import ru.practicum.service.CompilationService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CompilationServiceImpl implements CompilationService {
    private final CompilationRepository compilationRepository;
    @Override
    public List<CompilationDto> getCompilations(Integer from, Integer size, Boolean pinned) {
        return null;
    }

    @Override
    public List<ViewStatsDto> getCompilationsById(Integer compId) {
        return null;
    }

    @Override
    public CompilationDto addCompilation(NewCompilationDto newCompilationDto) {
        return null;
    }

    @Override
    public void deleteCompilation(Integer compId) {

    }

    @Override
    public CompilationDto updateCompilation(Integer compId, UpdateCompilationRequest request) {
        return null;
    }
}
