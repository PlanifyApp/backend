package com.planify.backend.infrastructure.repositories;

import com.planify.backend.domain.models.SavingDebtByDay;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

import java.time.LocalDate;

public interface SavingDebtByDayRepository extends ReactiveCrudRepository<SavingDebtByDay, Void> {

    @Query("""
        SELECT * 
        FROM savings_debts_by_day
        WHERE user_id = :userId
        ORDER BY date ASC
    """)
    Flux<SavingDebtByDay> findByUser(Long userId);

    @Query("""
        SELECT *
        FROM savings_debts_by_day
        WHERE user_id = :userId
        AND date BETWEEN :start AND :end
        ORDER BY date ASC
    """)
    Flux<SavingDebtByDay> findByUserAndRange(Long userId, LocalDate start, LocalDate end);
}
