package com.planify.backend.application.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FinancialSummaryDTO {
    private Long userId;
    private Long incomes;
    private Long expenses;
    private Long balance;
}
