package com.example.budgetappv2.category.mapper;

import com.example.budgetappv2.category.Category;
import com.example.budgetappv2.category.dto.CategoryDto;

public class CategoryMapper {

    public static Category mapToCategory(long id, CategoryDto categoryDto) {
        return Category.builder()
                .id(id)
                .budget(categoryDto.budget())
                .name(categoryDto.name())
                .startDate(categoryDto.startDate())
                .endDate(categoryDto.endDate())
                .build();
    }
}
