package com.example.budgetappv2.transaction.dto;

import com.example.budgetappv2.transaction.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDate;

public record TransactionDto(LocalDate date, BigDecimal total, TransactionType transactionType, String notes) {
}
