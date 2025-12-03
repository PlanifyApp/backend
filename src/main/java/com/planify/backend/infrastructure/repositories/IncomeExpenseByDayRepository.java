package com.planify.backend.infrastructure.repositories;

import com.planify.backend.domain.models.IncomeExpenseByDay;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import java.time.LocalDate;

public interface IncomeExpenseByDayRepository extends ReactiveCrudRepository<IncomeExpenseByDay, LocalDate> {

    @Query("""
        SELECT *
        FROM income_expense_by_day
        WHERE user_id = :userId
        ORDER BY date ASC
    """)
    Flux<IncomeExpenseByDay> findByUser(Long userId);

    @Query("""
        SELECT *
        FROM income_expense_by_day
        WHERE user_id = :userId
        AND (:start IS NULL OR date >= :start)
        AND (:end IS NULL OR date <= :end)
        ORDER BY date ASC
    """)
    Flux<IncomeExpenseByDay> findByUserAndDateRange(Long userId, LocalDate start, LocalDate end);
}
