package com.planify.backend.application.dtos;

import java.time.LocalDateTime;

public record TransactionCategoryAllDTO(
        Long categoryId,
        String categoryName,
        String type,
        Integer budgeted,
        LocalDateTime date,
        String description,
        Integer amount
) {}
