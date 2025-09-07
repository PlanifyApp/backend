package com.planify.backend.domain.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import java.time.LocalDateTime;

@Data
@Table("fixed_expenses")
public class FixedExpenseEntity {

    @Id
    private Long id;
    private Integer userId;
    private Integer accountId;
    private Integer categoryId;
    private String name;
    private LocalDateTime dateTime;
    private Integer budget;
    private Integer currentValue;
    private LocalDateTime createdAt;

    // getters & setters con lombok
}
