package com.planify.backend.application.dtos;

import java.time.LocalDateTime;

public record TransactionSummaryDTO(
        LocalDateTime date,
        String description,
        Integer amount
) {}
