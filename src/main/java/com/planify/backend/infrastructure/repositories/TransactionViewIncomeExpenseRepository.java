package com.planify.backend.infrastructure.repositories;

import com.planify.backend.domain.models.TransactionViewIncomeExpense;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

import java.time.LocalDateTime;

public interface TransactionViewIncomeExpenseRepository extends ReactiveCrudRepository<TransactionViewIncomeExpense, Long> {

    @Query("SELECT * FROM transactions_income_expense " +
            "WHERE user_id = :userId " +
            "AND account_id = :accountId " +
            "AND (:type IS NULL OR type::text = :type) " +
            "AND (:startDate IS NULL OR date_time >= :startDate) " +
            "AND (:endDate IS NULL OR date_time <= :endDate) " +
            "ORDER BY date_time DESC " +
            "LIMIT :#{#pageable.pageSize} OFFSET :#{#pageable.offset}")
    Flux<TransactionViewIncomeExpense> findByFilters(
            Long userId,
            Long accountId,
            String type,
            LocalDateTime startDate,
            LocalDateTime endDate,
            Pageable pageable
    );

}
