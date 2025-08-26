package com.planify.backend.application.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountsResponseDTO {
    private Long id;
    private String name;
    private Long quota;
    private Long budgeted;
    private Long current_value;
}
