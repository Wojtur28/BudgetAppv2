package com.example.budgetappv2.transaction.dto;

import com.example.budgetappv2.transaction.TransactionType;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDate;
@Builder

public record TransactionReadDto(long id, LocalDate date, BigDecimal total, TransactionType transactionType, String notes) {

}
