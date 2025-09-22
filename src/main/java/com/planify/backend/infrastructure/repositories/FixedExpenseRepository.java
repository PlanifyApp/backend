package com.planify.backend.infrastructure.repositories;

import com.planify.backend.domain.models.FixedExpenseEntity;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Flux;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface FixedExpenseRepository extends R2dbcRepository<FixedExpenseEntity, Long> {
    Flux<FixedExpenseEntity> findByUserId(Integer userId);
    Flux<FixedExpenseEntity> findByAccountId(Integer accountId);
    Flux<FixedExpenseEntity> findByCategoryId(Integer categoryId);
}
