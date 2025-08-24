package com.planify.backend.application.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountsUpdateDTO {
    private String name;
    private Long quota;
    private Long budgeted;
    private Long currentValue;
}
