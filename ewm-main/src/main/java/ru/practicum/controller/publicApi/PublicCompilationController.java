package ru.practicum.controller.publicApi;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.ViewStatsDto;
import ru.practicum.model.dto.compilation.CompilationDto;
import ru.practicum.service.CompilationService;

import java.util.List;

@RestController
@Slf4j
@RequestMapping(path = "/compilations")
@RequiredArgsConstructor
public class PublicCompilationController {
    private final CompilationService compilationService;

    @GetMapping
    public List<CompilationDto> getCompilations(@RequestParam(defaultValue = "0") Integer from,
                                                @RequestParam(defaultValue = "10") Integer size,
                                                @RequestParam(required = false) Boolean pinned) {
        log.info("GET /compilations for pinned = {} from {}  to {} is accessed", pinned, from, size);
        return compilationService.getCompilations(from, size, pinned);
    }

    @GetMapping("/{compId}")
    public List<ViewStatsDto> getCompilationById(@PathVariable Integer compId) {
        log.info("GET /compilations/{} is accessed", compId);
        return compilationService.getCompilationsById(compId);
    }
}
