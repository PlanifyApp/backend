package com.planify.backend.application.dtos;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;
@Data
public class MovementsDTO {
    @NotBlank
    @Size(min = 3, max = 100)
    private String description;

    @NotBlank
    private String category;

    @NotNull
    @Min(1)
    private Integer amount;

    @NotNull
    @Pattern(regexp = "income|expense|saving|debt|transfer", message = "Tipo debe ser: income, expense, saving, debt o transfer")
    private String type;

    @NotNull
    @PastOrPresent
    private LocalDate date;

    private String note;

    @NotNull
    private Long accountId;
}
