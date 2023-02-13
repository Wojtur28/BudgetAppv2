package com.example.budgetappv2.group.mapper;

import com.example.budgetappv2.category.dto.CategoryReadDto;
import com.example.budgetappv2.group.Group;
import com.example.budgetappv2.group.dto.GroupReadDto;
import com.example.budgetappv2.transaction.dto.TransactionReadDto;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GroupReadDtoMapper {

    public static ResponseEntity<Stream<GroupReadDto>> mapGroupToGroupReadDto(ResponseEntity<List<Group>> groups) {
        Stream<GroupReadDto> groupReadDtoStream = Objects.requireNonNull(groups.getBody())
                .stream()
                .map(group -> GroupReadDto.builder()
                        .id(group.getId())
                        .name(group.getName())
                        .categories(group.getCategories().stream()
                                .map(category -> CategoryReadDto.builder()
                                        .id(category.getId())
                                        .budget(category.getBudget().doubleValue())
                                        .name(category.getName())
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
                                        .build())
                                .collect(Collectors.toList()))
                        .build());

        return ResponseEntity.ok(groupReadDtoStream);
    }
}
