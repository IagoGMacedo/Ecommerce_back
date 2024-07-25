package com.macedo.Product.service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.Collections;



import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.macedo.Product.Exceptions.NotFoundException;
import com.macedo.Product.dtos.CategoryDTO;
import com.macedo.Product.entities.Category;
import com.macedo.Product.repository.CategoryRepository;
import com.macedo.Product.utils.Patcher;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final Patcher patcher;

    public List<CategoryDTO> getCategories(CategoryDTO filtro) {
        Category obj = extractCategory(filtro);
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(
                        ExampleMatcher.StringMatcher.CONTAINING);

        Example example = Example.of(obj, matcher);
        return toDTOList(categoryRepository.findAll(example));
    }

    public CategoryDTO getCategoryById(Integer id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("category"));
        return toDTO(category);
    }

    public CategoryDTO createCategory(CategoryDTO category) {
        Category newCategory = new Category();
        newCategory = extractCategory(category);
        return toDTO(categoryRepository.save(newCategory));
    }

    public CategoryDTO updateCategory(Integer id, CategoryDTO category) {
        Category existingCategory = categoryRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("category"));

        Category newCategory = extractCategory(category);
        newCategory.setId(existingCategory.getId());
        return toDTO(categoryRepository.save(newCategory));
    }

    public CategoryDTO patchCategory(Integer id, CategoryDTO categoryIncompletaDto) {
        Category existingCategory = categoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("category"));

        Category incompleteCategory = extractCategory(categoryIncompletaDto);

        patcher.patchPropertiesNotNull(incompleteCategory, existingCategory);
        return toDTO(categoryRepository.save(existingCategory));
    }

    public void deleteCategory(Integer id) {
        Category category = categoryRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("category"));
        categoryRepository.delete(category);
    }

    private Category extractCategory(CategoryDTO dto) {
        Category category = new Category();
        String name = dto.getName();
        if(name!=null)
            name = name.toUpperCase();
        category.setName(name);
        return category;
    }

    private CategoryDTO toDTO(Category category) {
        return CategoryDTO.builder()
                .id(category.getId())
                .name(category.getName().toUpperCase())
                .build();
    }

    private List<CategoryDTO> toDTOList(List<Category> categories) {
        if (CollectionUtils.isEmpty(categories)) {
            return Collections.emptyList();
        }
        return categories.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

}
