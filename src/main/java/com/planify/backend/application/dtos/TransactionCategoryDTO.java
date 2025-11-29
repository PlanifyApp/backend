package com.planify.backend.application.dtos;

import java.util.List;

public record TransactionCategoryDTO(
        Long categoryId,
        String categoryName,
        Integer budgeted, // null for income
        List<TransactionItem> transactions
) {
    public record TransactionItem(
            String description,
            Integer amount,
            String date
    ) {}
}
