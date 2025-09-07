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
    private GoalType type; // 🔴 ahora es un enum, no String, faltaba este campo también
    private Integer currentValue;
    private LocalDateTime createdAt;

    public enum GoalType {
        ahorro,
        deuda,
        inversion,
        otro
    }

    // getters & setters con lombok
}
