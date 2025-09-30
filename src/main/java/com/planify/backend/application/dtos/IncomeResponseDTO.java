package com.planify.backend.application.dtos;

import com.planify.backend.domain.models.IncomesEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class IncomeResponseDTO {
    private Long id;
    private Integer userId;
    private String detail;
    private Integer currentValue;

    public IncomeResponseDTO(IncomesEntity income) {
        this.id = income.getId();
        this.userId = income.getUserId();
        this.detail = income.getDetail();
        this.currentValue = income.getCurrentValue();
    }

    public IncomesEntity toEntity() {
        IncomesEntity income = new IncomesEntity();
        income.setId(this.id);
        income.setUserId(this.userId);
        income.setDetail(this.detail);
        income.setCurrentValue(this.currentValue != null ? this.currentValue : 0);
        return income;
    }
}
