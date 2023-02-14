package com.example.budgetappv2.category.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record CategoryDto(BigDecimal budget, String name, LocalDate startDate, LocalDate endDate) {
}
