package ru.practicum.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.exception.NotFoundException;
import ru.practicum.mapper.CompilationMapper;
import ru.practicum.mapper.EventMapper;
import ru.practicum.model.dto.compilation.CompilationDto;
import ru.practicum.model.dto.compilation.NewCompilationDto;
import ru.practicum.model.dto.compilation.UpdateCompilationRequest;
import ru.practicum.model.entity.Compilation;
import ru.practicum.model.entity.Event;
import ru.practicum.repository.CompilationRepository;
import ru.practicum.repository.event.EventRepository;
import ru.practicum.service.CompilationService;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CompilationServiceImpl implements CompilationService {
    private final CompilationRepository compilationRepository;
    private final EventRepository eventRepository;
    private final CompilationMapper compilationMapper;
    private final EventMapper eventMapper;
    @Override
    public List<CompilationDto> getCompilationsPublic(Integer from, Integer size, Boolean pinned) {
        //pinned == false by default
        Pageable pageable = PageRequest.of(from / size, size);
        //PageRequest(from, size, Sort.by(Sort.Direction.ASC, "id"));?
        List<Compilation> compilations = compilationRepository.findAllByPinned(pinned, pageable).getContent();
        return compilations.stream()
                .map(c -> compilationMapper.toDto(c, eventMapper))
                .collect(Collectors.toList());
    }

    @Override
    public CompilationDto getCompilationsByIdPublic(Long compId) {
        Compilation compilation = compilationRepository.findById(compId)
                .orElseThrow(()-> new NotFoundException("compilation is not found"));
        return compilationMapper.toDto(compilation, eventMapper);
    }

    @Override
    @Transactional
    public CompilationDto addCompilationByAdmin(NewCompilationDto newCompilationDto) {
        List<Long> eventsIds = newCompilationDto.getEvents();
        List<Event> events = eventRepository.findAllByIdIn(eventsIds);
        Compilation compilation = Compilation.builder()
                .title(newCompilationDto.getTitle())
                .pinned(newCompilationDto.getPinned())
                .events(new HashSet<>(events)).build();
        return compilationMapper.toDto(compilationRepository.save(compilation), eventMapper);
    }

    @Override
    @Transactional
    public void deleteCompilationByAdmin(Long compId) {
        Compilation compilation = compilationRepository.findById(compId)
                .orElseThrow(()-> new NotFoundException("compilation is not found"));
        compilationRepository.delete(compilation);
    }

    @Override
    @Transactional
    public CompilationDto updateCompilationByAdmin(Long compId, UpdateCompilationRequest request) {
        Compilation compilation = compilationRepository.findById(compId)
                .orElseThrow(()-> new NotFoundException("compilation is not found"));
        List<Event> events = eventRepository.findAllByIdIn(request.getEvents());
        if(request.getTitle() != null)
            compilation.setTitle(request.getTitle());
        if(request.getPinned() != null)
            compilation.setPinned(request.getPinned());
        if(request.getEvents() != null)
            compilation.setEvents(new HashSet<>(events));
        return compilationMapper.toDto(compilation, eventMapper);
    }
}
