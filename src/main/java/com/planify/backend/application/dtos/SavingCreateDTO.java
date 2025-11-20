package com.planify.backend.application.dtos;

import lombok.Data;

import java.time.LocalDate;

@Data
public class SavingCreateDTO {
    private Long userId;
    private String savingName;
    private Integer goal;
    private Integer initialBalance;
    private Integer expectedDeposit;
    private LocalDate targetDate;
    private String icon;
}
