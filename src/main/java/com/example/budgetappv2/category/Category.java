package com.example.budgetappv2.category;

import com.example.budgetappv2.group.Group;
import com.example.budgetappv2.transaction.Transaction;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor

@Entity
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private BigDecimal budget;
    private String name;
    private Date startDate;
    private Date endDate;
    @OneToMany
    @JoinColumn(name = "transcation_id")
    private List<Transaction> transactions;

}
