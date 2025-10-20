package com.planify.backend.domain.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table("categories")
@NoArgsConstructor
@AllArgsConstructor
public class CategoryEntity {
    @Id
    private Long id;

    private String name;
    private Integer budgeted;

    @Column("percent_spent")
    private Double percentSpent;

    @Column("user_id")
    private Integer userId;
}
