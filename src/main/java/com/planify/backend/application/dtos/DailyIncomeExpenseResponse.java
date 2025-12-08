package com.planify.backend.application.dtos;

public record DailyIncomeExpenseResponse(
        Object income,
        Object expense
) {}