package com.planify.backend.domain.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("debts_by_day")
public class DebtByDay {

    private LocalDate date;
    private String day;
    private BigDecimal totalPaid;   // mínimo pagado
    private BigDecimal totalDebt;   // deuda total
    private Long userId;
}
