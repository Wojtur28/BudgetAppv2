package com.example.budgetappv2.transaction.dto;

import com.example.budgetappv2.transaction.TransactionType;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;
@Builder
@Getter

public class TransactionReadDto {
    private long id;
    private LocalDate date;
    private BigDecimal total;
    private TransactionType transactionType;
    private String notes;
}
