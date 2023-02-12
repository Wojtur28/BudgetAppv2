package com.example.budgetappv2.category.dto;

import java.time.LocalDate;

public record CategoryDto(long id, String name, LocalDate startDate, LocalDate endDate) {
}
