package com.example.budgetappv2.category;

import com.example.budgetappv2.group.Group;
import com.example.budgetappv2.transaction.Transaction;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Builder

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
    private LocalDate startDate;
    @Column(name = "end_date")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @NonNull
    private LocalDate endDate;
    @OneToMany(
            mappedBy = "category",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Transaction> transactions;

    @ManyToOne
    @JoinColumn(name = "group_id")
    private Group group;

    @JsonBackReference
    public Group getGroup() {
        return group;
    }

    @JsonManagedReference
    public List<Transaction> getTransactions() {
        return transactions;
    }

}
