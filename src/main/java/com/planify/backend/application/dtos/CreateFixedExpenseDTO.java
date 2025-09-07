package com.planify.backend.application.dtos;

import lombok.Data;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
public class CreateFixedExpenseDTO {

    @NotNull
    private Integer userId;
    @NotNull
    private Integer accountId;
    @NotNull
    private Integer categoryId;
    @NotNull
    private String name;
    @NotNull
    private LocalDateTime dateTime;
    private Integer budget = 0;
    private Integer currentValue = 0;
    // getters/setters
}
