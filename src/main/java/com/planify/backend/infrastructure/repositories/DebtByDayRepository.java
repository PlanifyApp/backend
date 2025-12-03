package com.planify.backend.infrastructure.repositories;

import com.planify.backend.domain.models.DebtByDay;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

import java.time.LocalDate;

public interface DebtByDayRepository extends ReactiveCrudRepository<DebtByDay, Void> {

    @Query("""
            SELECT *
            FROM debts_by_day
            WHERE user_id = :userId
            ORDER BY date
            """)
    Flux<DebtByDay> findByUser(Long userId);


    @Query("""
            SELECT *
            FROM debts_by_day
            WHERE user_id = :userId
              AND date BETWEEN :start AND :end
            ORDER BY date
            """)
    Flux<DebtByDay> findByUserAndRange(Long userId, LocalDate start, LocalDate end);
}
