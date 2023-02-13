package com.example.budgetappv2.group.dto;

import com.example.budgetappv2.category.dto.CategoryReadDto;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
@Builder
@Getter
public class GroupReadDto {
    private long id;
    private String name;
    private List<CategoryReadDto> categories;

}
