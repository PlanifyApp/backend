package com.planify.backend.infrastructure.mappers;

import com.planify.backend.domain.models.VariableExpenses;
import com.planify.backend.infrastructure.entities.VariableExpensesEntity;
import org.springframework.stereotype.Component;

@Component
public class VariableExpensesMappers {

    public VariableExpenses toDomain(VariableExpensesEntity variableExpensesEntity) {
        VariableExpenses variableExpenses = new VariableExpenses();
        variableExpenses.setId(variableExpensesEntity.getId());
        variableExpenses.setUserId(variableExpensesEntity.getUserId());
        variableExpenses.setName(variableExpensesEntity.getName());
        variableExpenses.setDateTime(variableExpensesEntity.getDateTime());
        variableExpenses.setBudget(variableExpensesEntity.getBudget());
        variableExpenses.setCurrentValue(variableExpensesEntity.getCurrentValue());
        variableExpenses.setCategoryId(variableExpensesEntity.getCategoryId());
        variableExpenses.setAccountId(variableExpensesEntity.getAccountId());
        return variableExpenses;
    }

    public VariableExpensesEntity toEntity(VariableExpenses variableExpenses) {
        VariableExpensesEntity variableExpensesEntity = new VariableExpensesEntity();
        variableExpensesEntity.setId(variableExpenses.getId());
        variableExpensesEntity.setUserId(variableExpenses.getUserId());
        variableExpensesEntity.setName(variableExpenses.getName());
        variableExpensesEntity.setDateTime(variableExpenses.getDateTime());
        variableExpensesEntity.setBudget(variableExpenses.getBudget());
        variableExpensesEntity.setCurrentValue(variableExpenses.getCurrentValue());
        variableExpensesEntity.setCategoryId(variableExpenses.getCategoryId());
        variableExpensesEntity.setAccountId(variableExpenses.getAccountId());
        return variableExpensesEntity;
    }

}
