package com.planify.backend.domain.models;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.data.relational.core.mapping.Column;

import java.time.LocalDateTime;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table("savings")
public class Saving {

    @Id
    private Long id;

    @Column("user_id")
    private Long userId;

    @Column("saving_name")
    private String savingName;

    @Column("goal")
    private Integer goal;

    @Column("initial_balance")
    private Integer initialBalance;

    @Column("monthly_budget")
    private Integer monthlyBudget;

    @Column("total_saved")
    private Integer totalSaved;

    @Column("remaining")
    private Integer remaining;

    @Column("target_date")
    private LocalDate targetDate;

    @Column("average")
    private Integer average;

    @Column("created_at")
    private LocalDateTime createdAt;
}
