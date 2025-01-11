package ru.practicum.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.dto.ViewStatsDto;
import ru.practicum.exception.NotFoundException;
import ru.practicum.mapper.CompilationMapper;
import ru.practicum.model.dto.compilation.CompilationDto;
import ru.practicum.model.dto.compilation.NewCompilationDto;
import ru.practicum.model.dto.compilation.UpdateCompilationRequest;
import ru.practicum.model.entity.Compilation;
import ru.practicum.model.entity.Event;
import ru.practicum.repository.CompilationRepository;
import ru.practicum.service.CompilationService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CompilationServiceImpl implements CompilationService {
    private final CompilationRepository compilationRepository;
    private final CompilationMapper compilationMapper;
    @Override
    public List<CompilationDto> getCompilationsPublic(Integer from, Integer size, Boolean pinned) {
        //pinned == false by default
        Pageable pageable = PageRequest.of(from / size, size);
        //PageRequest(from, size, Sort.by(Sort.Direction.ASC, "id"));?
        List<Compilation> compilations = compilationRepository.findAllByPinned(pinned, pageable).getContent();
        return compilations.stream()
                .map(compilationMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public CompilationDto getCompilationsByIdPublic(Long compId) {
        Compilation compilation = compilationRepository.findById(compId)
                .orElseThrow(()-> new NotFoundException("compilation is not found"));
        return compilationMapper.toDto(compilation);
    }

    @Override
    public CompilationDto addCompilationByAdmin(NewCompilationDto newCompilationDto) {
        return null;
    }

    @Override
    public void deleteCompilationByAdmin(Long compId) {

    }

    @Override
    public CompilationDto updateCompilationByAdmin(Long compId, UpdateCompilationRequest request) {
        return null;
    }
}
