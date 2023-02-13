package com.example.budgetappv2.category.dto;


import com.example.budgetappv2.transaction.dto.TransactionReadDto;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
@Builder
@Getter
public class CategoryReadDto {
    private long id;
    private String name;
    private double budget;
    private String startDate;
    private String endDate;
    private List<TransactionReadDto> transactions;
}
