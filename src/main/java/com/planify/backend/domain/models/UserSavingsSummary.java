package com.planify.backend.domain.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table("user_savings_summary") // nombre exacto de la vista en tu BD
public class UserSavingsSummary {

    @Id
    @Column("saving_id")
    private Long savingId;

    @Column("saving_name")
    private String savingName;

    @Column("monthly_budget")
    private Integer monthlyBudget;

    @Column("total_budget")
    private Integer totalBudget;
}