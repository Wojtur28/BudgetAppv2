package com.example.budgetappv2.transaction;

import com.example.budgetappv2.category.Category;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor

@Entity
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private Date date;
    private BigDecimal total;
    private Enum<Type> type;
    private String notes;
    @ManyToOne
    private Category category_id;
}
