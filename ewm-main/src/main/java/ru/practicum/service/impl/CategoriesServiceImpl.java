package ru.practicum.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.model.dto.category.CategoryDto;
import ru.practicum.model.dto.category.NewCategoryDto;
import ru.practicum.repository.CategoryRepository;
import ru.practicum.service.CategoriesService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoriesServiceImpl implements CategoriesService {
    private final CategoryRepository categoryRepository;
    @Override
    public CategoryDto addCategory(NewCategoryDto newCategoryDto) {
        return null;
    }

    @Override
    public void deleteCategory(Integer catId) {

    }

    @Override
    public CategoryDto updateCategory(Integer catId, CategoryDto categoryDto) {
        return null;
    }

    @Override
    public CategoryDto getCategory(Integer catId) {
        return null;
    }

    @Override
    public List<CategoryDto> getCategories(Integer from, Integer size) {
        return null;
    }
}
