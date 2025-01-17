package ru.practicum.controller.adminApi;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.model.dto.compilation.CompilationDto;
import ru.practicum.model.dto.compilation.NewCompilationDto;
import ru.practicum.model.dto.compilation.UpdateCompilationRequest;
import ru.practicum.service.CompilationService;

@RestController
@Slf4j
@RequestMapping(path = "/admin/compilations")
@RequiredArgsConstructor
public class AdminCompilationController {
    private final CompilationService compilationService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CompilationDto addCompilationByAdmin(@Valid @RequestBody NewCompilationDto newCompilationDto) {
        log.info("POST /admin/compilations is accessed: {}", newCompilationDto);
        return compilationService.addCompilationByAdmin(newCompilationDto);
    }

    @DeleteMapping("/{compId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCompilationByAdmin(@PathVariable Long compId) {
        log.info("DELETE /admin/compilations/{} is accessed", compId);
        compilationService.deleteCompilationByAdmin(compId);
    }

    @PatchMapping("/{compId}")
    public CompilationDto updateCompilationByAdmin(@PathVariable Long compId,
                                                   @Valid @RequestBody UpdateCompilationRequest request) {
        log.info("PATCH /admin/compilations/{} is accessed: {}", compId, request);
        return compilationService.updateCompilationByAdmin(compId, request);
    }
}
