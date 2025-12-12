package com.planify.backend.domain.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("savings_debts_by_day")
public class SavingDebtByDay {

    private LocalDate date;
    private String day;
    private BigDecimal totalSavings;
    private BigDecimal totalDebts;
    private Long userId;
}
