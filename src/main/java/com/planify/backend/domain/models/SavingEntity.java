package com.planify.backend.domain.models;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.data.relational.core.mapping.Column;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table("savings")
public class SavingEntity {

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

    @Column("expected_deposit")
    private Integer expectedDeposit;

    @Column("target_date")
    private LocalDate targetDate;

    @Column("icon")
    private String icon;
}
