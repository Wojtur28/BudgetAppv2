package com.example.budgetappv2.group.dto;

import com.example.budgetappv2.category.dto.CategoryReadDto;
import lombok.Builder;

import java.util.List;
@Builder

public record GroupReadDto(long id, String name, List<CategoryReadDto> categories) {

}
