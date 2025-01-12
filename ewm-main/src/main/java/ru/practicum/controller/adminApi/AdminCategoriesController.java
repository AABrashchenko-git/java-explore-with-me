package ru.practicum.controller.adminApi;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.model.dto.category.CategoryDto;
import ru.practicum.model.dto.category.NewCategoryDto;
import ru.practicum.service.CategoriesService;

@RestController
@Slf4j
@RequestMapping(path = "/admin/categories")
@RequiredArgsConstructor
public class AdminCategoriesController {
    private final CategoriesService categoriesService;

    @PostMapping
    public CategoryDto addCategory(@RequestBody @Valid NewCategoryDto newCategoryDto) {
        log.info("POST /admin/categories {} is accessed", newCategoryDto);
        return categoriesService.addCategory(newCategoryDto);
    }

    @DeleteMapping("/{catId}")
    public void deleteCategory(@PathVariable Long catId) {
        log.info("DELETE /admin/categories/{} is accessed", catId);
        categoriesService.deleteCategory(catId);
    }

    @PatchMapping("/{catId}")
    public CategoryDto updateCategory(@PathVariable Long catId, @RequestBody CategoryDto categoryDto) {
        log.info("PATCH /admin/categories/{}  is accessed: {}", catId, categoryDto);
        return categoriesService.updateCategory(catId, categoryDto);
    }
}
