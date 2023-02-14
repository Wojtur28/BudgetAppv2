package com.example.budgetappv2.category.mapper;

import com.example.budgetappv2.category.Category;
import com.example.budgetappv2.category.dto.CategoryDto;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

public class CategoryDtoMapper {

    public static ResponseEntity<Stream<CategoryDto>> mapCategoryToCategoryDto(ResponseEntity<List<Category>> categories) {
        Stream<CategoryDto> categoryDtoStream = Objects.requireNonNull(categories.getBody())
                .stream()
                .map(category -> new CategoryDto(category.getBudget(), category.getName(), category.getStartDate(), category.getEndDate()));
        return ResponseEntity.ok(categoryDtoStream);
    }
}
