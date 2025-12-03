package com.planify.backend.application.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.time.LocalDate;

@Data
@AllArgsConstructor
public class DebtResponseWithDescriptionDTO {
    private Long id;
    private Long userId;
    private String description; // Antes era name
    private Integer currentDebt;
    private Integer principalAmount;
    private Integer minimumPayment;
    private LocalDate dueDate;
    private String icon;
    private Integer remainingDebt;
}
