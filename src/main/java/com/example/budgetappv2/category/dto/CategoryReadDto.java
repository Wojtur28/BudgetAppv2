package com.example.budgetappv2.category.dto;


import com.example.budgetappv2.transaction.dto.TransactionReadDto;
import lombok.Builder;

import java.util.List;
@Builder

public record CategoryReadDto(long id, String name, double budget, String startDate, String endDate, List<TransactionReadDto> transactions) {

}
