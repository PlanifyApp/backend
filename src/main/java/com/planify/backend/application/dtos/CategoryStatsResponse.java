package com.planify.backend.application.dtos;

import java.math.BigDecimal;

public record CategoryStatsResponse(
        Long id,
        String categoria,
        String tipo,
        BigDecimal total
) {}