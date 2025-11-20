package com.planify.backend.application.dtos;

import com.planify.backend.domain.models.SavingEntity;
import com.planify.backend.domain.models.CategoryEntity;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class UserFinancialOverviewDTO {

    private List<CategoryEntity> incomes;
    private List<CategoryEntity> expenses;
    private List<SavingEntity> savings;
}
