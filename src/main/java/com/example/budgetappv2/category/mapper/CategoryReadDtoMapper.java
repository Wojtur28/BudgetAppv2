package com.example.budgetappv2.category.mapper;

import com.example.budgetappv2.category.Category;
import com.example.budgetappv2.category.dto.CategoryReadDto;
import com.example.budgetappv2.transaction.dto.TransactionReadDto;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CategoryReadDtoMapper {

    public static ResponseEntity<Stream<CategoryReadDto>> mapCategoryToCategoryReadDto(ResponseEntity<List<Category>> categories) {
        Stream<CategoryReadDto> categoryReadDtoStream = Objects.requireNonNull(categories.getBody())
                .stream()
                .map(category -> CategoryReadDto.builder()
                        .id(category.getId())
                        .name(category.getName())
                        .budget(category.getBudget().doubleValue())
                        .startDate(category.getStartDate().toString())
                        .endDate(category.getEndDate().toString())
                        .transactions(category.getTransactions().stream()
                                .map(transaction -> TransactionReadDto.builder()
                                        .id(transaction.getId())
                                        .date(transaction.getDate())
                                        .total(transaction.getTotal())
                                        .transactionType(transaction.getTransactionType())
                                        .notes(transaction.getNotes())
                                        .build()
                                ).collect(Collectors.toList()))
                        .build());

        return ResponseEntity.ok(categoryReadDtoStream);
    }

}

