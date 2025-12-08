package com.planify.backend.application.dtos;

import java.math.BigDecimal;
import java.util.List;

public record CategoryBudgetWithTotalResponse(
        List<CategoryBudgetItem> categories,
        BigDecimal totalBudget
) {
    public record CategoryBudgetItem(
            String category,
            BigDecimal budgeted
    ) {}
}