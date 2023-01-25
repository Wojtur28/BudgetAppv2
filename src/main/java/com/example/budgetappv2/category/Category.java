package com.example.budgetappv2.category;

import com.example.budgetappv2.transaction.Transaction;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor

@Entity
@Table(name = "categories")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NonNull
    private BigDecimal budget;
    @NonNull
    private String name;
    @Column(name = "start_date")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @NonNull
    private Date startDate;
    @Column(name = "end_date")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @NonNull
    private Date endDate;
    @OneToMany
    @JoinColumn(name = "transcation_id")
    private List<Transaction> transactions;

}
