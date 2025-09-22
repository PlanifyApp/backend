package com.planify.backend.application.dtos;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class FixedExpenseDTO {
    private Long id;
    private Integer userId;
    private Integer accountId;
    private Integer categoryId;
    private String name;
    private LocalDateTime dateTime;
    private Integer budget;
    private Integer currentValue;
    private String type; // nuevo campo
    private LocalDateTime createdAt;
    
    // getters/setters, constructor vacío con lombok 
}
