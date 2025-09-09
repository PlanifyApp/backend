package com.planify.backend.infrastructure.repositories;

import com.planify.backend.infrastructure.entities.VariableExpensesEntity;
import org.springframework.data.r2dbc.repository.R2dbcRepository;

public interface SpringDataVariableExpensesRepository extends R2dbcRepository<VariableExpensesEntity, Integer> {
}
