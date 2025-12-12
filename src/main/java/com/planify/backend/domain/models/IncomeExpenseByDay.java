package com.planify.backend.domain.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("income_expense_by_day")
public class IncomeExpenseByDay {
    @Id
    private LocalDate date;
    private String day;
    private Integer income;
    private Integer expense;
    private Long userId;
}
