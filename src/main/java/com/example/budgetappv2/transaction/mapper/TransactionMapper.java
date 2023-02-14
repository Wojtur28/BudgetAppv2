package com.example.budgetappv2.transaction.mapper;

import com.example.budgetappv2.transaction.Transaction;
import com.example.budgetappv2.transaction.dto.TransactionDto;

public class TransactionMapper {

    public static Transaction mapToTransaction(long id, TransactionDto transactionDto) {
        return Transaction.builder()
                .id(id)
                .date(transactionDto.date())
                .total(transactionDto.total())
                .transactionType(transactionDto.transactionType())
                .notes(transactionDto.notes())
                .build();
    }
}
