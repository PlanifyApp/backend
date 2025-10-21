package com.planify.backend.application.dtos;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class UserTransactionDTO {
    private Integer id;
    private String description;
    private String account;
    private String category;
    private LocalDate date;
    private String type;
    private Integer budget;
    private Integer currentValue;
}
