package com.planify.backend.application.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VariableExpensesDTO {

    private Integer id;
    private Integer userId;
    private String name;
    private LocalDateTime dateTime;
    private Integer budget;
    private Integer currentValue;
    private String type;
    private Integer categoryId;
    private Integer accountId;

}
