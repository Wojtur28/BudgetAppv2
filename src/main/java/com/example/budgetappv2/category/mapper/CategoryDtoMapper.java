package com.example.budgetappv2.category.mapper;

import com.example.budgetappv2.category.Category;
import com.example.budgetappv2.category.dto.CategoryNameDto;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

public class CategoryDtoMapper {

    public static ResponseEntity<Stream<CategoryNameDto>> mapCategoryToCategoryNameDto(ResponseEntity<List<Category>> groups) {
        Stream<CategoryNameDto> categoryNameDtoStream = Objects.requireNonNull(groups.getBody()).stream()
                .map(group -> new CategoryNameDto(group.getName()));
        return ResponseEntity.ok(categoryNameDtoStream);
    }
}
