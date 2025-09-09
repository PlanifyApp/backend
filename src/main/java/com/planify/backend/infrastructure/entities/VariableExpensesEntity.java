package com.planify.backend.infrastructure.entities;

import com.planify.backend.domain.models.GoalType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table("variable_expenses")
public class VariableExpensesEntity {

    private Integer id;
    private Integer userId;
    private String name;
    private LocalDateTime dateTime;
    private Integer budget;
    private Integer currentValue;
    private GoalType type;
    private Integer categoryId;
    private Integer accountId;

}
