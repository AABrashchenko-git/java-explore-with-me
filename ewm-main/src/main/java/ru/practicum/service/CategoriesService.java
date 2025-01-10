package ru.practicum.service;

import ru.practicum.model.dto.category.CategoryDto;
import ru.practicum.model.dto.category.NewCategoryDto;

import java.util.List;

public interface CategoriesService {
    CategoryDto addCategory(NewCategoryDto newCategoryDto);

    void deleteCategory(Integer catId);

    CategoryDto updateCategory(Integer catId, CategoryDto categoryDto);

    CategoryDto getCategory(Integer catId);

    List<CategoryDto> getCategories(Integer from, Integer size);
}
