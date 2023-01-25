package com.example.budgetappv2.group;

import com.example.budgetappv2.category.Category;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor

@Entity
@Table(name = "groups")
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @NonNull
    private String name;

    @OneToMany
    @JoinColumn(name = "categoty_id")
    private List<Category> categories;

}
