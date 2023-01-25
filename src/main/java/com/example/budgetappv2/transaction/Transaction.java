package com.example.budgetappv2.transaction;

import com.example.budgetappv2.category.Category;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor

@Entity
@Table(name = "transactions")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @NonNull
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date date;
    @NonNull
    private BigDecimal total;
    @NonNull
    private TransactionType transactionType;
    @NonNull
    private String notes;
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
}
