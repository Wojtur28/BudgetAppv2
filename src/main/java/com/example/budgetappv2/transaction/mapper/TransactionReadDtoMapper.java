package com.example.budgetappv2.transaction.mapper;

import com.example.budgetappv2.transaction.Transaction;
import com.example.budgetappv2.transaction.dto.TransactionReadDto;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

public class TransactionReadDtoMapper {

    public static ResponseEntity<Stream<TransactionReadDto>> mapTransactionToTransactionReadDto(ResponseEntity<List<Transaction>> transactions) {
        Stream<TransactionReadDto> transactionReadDtoStream = Objects.requireNonNull(transactions.getBody())
                .stream()
                .map(transaction -> TransactionReadDto.builder()
                        .id(transaction.getId())
                        .date(transaction.getDate())
                        .total(transaction.getTotal())
                        .transactionType(transaction.getTransactionType())
                        .notes(transaction.getNotes())
                        .build());
        return ResponseEntity.ok(transactionReadDtoStream);
    }
}
