package com.planify.backend.application.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class DebtResponseDTO {
    private Long id;
    private Long userId;
    private String name;
    private Integer currentDebt;
    private Integer principalAmount;
    private Integer minimumPayment;
    private LocalDate dueDate;
    private String icon;
    private Integer remainingDebt;
}
