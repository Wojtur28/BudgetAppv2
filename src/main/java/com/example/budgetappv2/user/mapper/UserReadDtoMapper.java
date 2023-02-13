package com.example.budgetappv2.user.mapper;

import com.example.budgetappv2.category.dto.CategoryReadDto;
import com.example.budgetappv2.group.dto.GroupReadDto;
import com.example.budgetappv2.transaction.dto.TransactionReadDto;
import com.example.budgetappv2.user.User;
import com.example.budgetappv2.user.dto.UserReadDto;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class UserReadDtoMapper {

    public static ResponseEntity<Stream<UserReadDto>> mapUserToUserReadDto(ResponseEntity<List<User>> users) {
        Stream<UserReadDto> userReadDtoStream = Objects.requireNonNull(users.getBody())
                .stream()
                .map(user -> UserReadDto.builder()
                        .id(user.getId())
                        .username(user.getUsername())
                        .password(user.getPassword())
                        .groups(user.getGroups().stream()
                                .map(group -> GroupReadDto.builder()
                                        .id(group.getId())
                                        .name(group.getName())
                                        .categories(group.getCategories().stream()
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
                                                        .build()
                                                ).collect(Collectors.toList()))
                                        .build()
                                ).collect(Collectors.toList())).build());

        return ResponseEntity.ok(userReadDtoStream);
    }
}


