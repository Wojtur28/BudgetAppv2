package com.example.budgetappv2.transaction.mapper;

import com.example.budgetappv2.transaction.Transaction;
import com.example.budgetappv2.transaction.dto.TransactionDto;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

public class TransactionDtoMapper {

    public static ResponseEntity<Stream<TransactionDto>> mapTransactionToTransactionDto(ResponseEntity<List<Transaction>> transactions) {
        Stream<TransactionDto> transactionDtoStream = Objects.requireNonNull(transactions.getBody())
                .stream()
                .map(transaction -> new TransactionDto(transaction.getDate(), transaction.getTotal(), transaction.getTransactionType(), transaction.getNotes()));
        return ResponseEntity.ok(transactionDtoStream);
    }
}
