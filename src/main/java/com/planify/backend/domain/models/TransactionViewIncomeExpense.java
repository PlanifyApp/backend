package com.planify.backend.domain.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table("transactions_income_expense")
public class TransactionViewIncomeExpense {

    @Id
    private Long id;
    private Long userId;
    private Long accountId;
    private String description;
    private LocalDateTime dateTime;
    private Double amount;
    private String type;
}
