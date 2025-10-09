package com.planify.backend.infrastructure.repositories;

import com.planify.backend.application.dtos.FinancialSummaryDTO;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface FinancialSummaryRepository extends ReactiveCrudRepository<FinancialSummaryDTO, Long> {

    @Query("SELECT user_id, incomes, expenses, balance FROM financial_summary WHERE user_id = :userId")
    Mono<FinancialSummaryDTO> findByUserId(Long userId);
}
