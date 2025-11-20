package com.planify.backend.domain.models;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table("debts")
public class DebtEntity {

    @Id
    private Long id;

    private Long userId;
    private String name;
    private Integer currentDebt;
    private Integer principalAmount;
    private Integer minimumPayment;
    private LocalDate dueDate;
    private String icon;
}
