package com.planify.backend.application.dtos;

import lombok.Data;

import java.time.LocalDate;

@Data
public class UpdateDebtDTO {
    private String name;
    private Integer currentDebt;
    private Integer principalAmount;
    private Integer minimumPayment;
    private LocalDate dueDate;
    private String icon;
}
