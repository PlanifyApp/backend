package com.planify.backend.application.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class SavingResponseDTO {
    private Long id;
    private Long userId;
    private String description;  // <-- antes era savingName
    private Integer goal;
    private Integer initialBalance;
    private Integer expectedDeposit;
    private LocalDate targetDate;
    private String icon;
}
