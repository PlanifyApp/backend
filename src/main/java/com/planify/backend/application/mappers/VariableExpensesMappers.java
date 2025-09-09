package com.planify.backend.application.mappers;

import com.planify.backend.application.dtos.VariableExpenses;
import com.planify.backend.domain.models.GoalType;
import org.springframework.stereotype.Component;

@Component
public class VariableExpensesMappers {

    public com.planify.backend.domain.models.VariableExpenses toDomain(VariableExpenses dto) {
        return new com.planify.backend.domain.models.VariableExpenses(
                null,
                dto.getUserId(),
                dto.getName(),
                dto.getDateTime(),
                dto.getBudget(),
                dto.getCurrentValue(),
                GoalType.fromValue(dto.getType()),  // convertir string → enum
                dto.getCategoryId(),
                dto.getAccountId()
        );
    }

    public VariableExpenses toResponse(com.planify.backend.domain.models.VariableExpenses domain) {
        VariableExpenses response = new VariableExpenses();
        response.setId(domain.getId());
        response.setUserId(domain.getUserId());
        response.setName(domain.getName());
        response.setDateTime(domain.getDateTime());
        response.setBudget(domain.getBudget());
        response.setCurrentValue(domain.getCurrentValue());
        response.setType(domain.getType().getValue()); // enum → string
        response.setCategoryId(domain.getCategoryId());
        response.setAccountId(domain.getAccountId());
        return response;
    }

}
