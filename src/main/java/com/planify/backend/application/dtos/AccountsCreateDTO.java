package com.planify.backend.application.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountsCreateDTO {
    private String name;
    private Long quota;
    private Long budgeted;
    private Long current_value;
    private Long user_id;
}
