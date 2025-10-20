package com.planify.backend.application.mappers;

import com.planify.backend.application.dtos.VariableExpensesDTO;
import com.planify.backend.domain.models.GoalType;
import org.springframework.stereotype.Component;

@Component
public class VariableExpensesMappersApp {

    public com.planify.backend.domain.models.VariableExpenses toDomain(VariableExpensesDTO dto) {
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

    public VariableExpensesDTO toResponse(com.planify.backend.domain.models.VariableExpenses domain) {
        VariableExpensesDTO response = new VariableExpensesDTO();
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
