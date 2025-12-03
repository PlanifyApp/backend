package com.planify.backend.infrastructure.repositories;

import com.planify.backend.domain.models.DebtEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface DebtRepository extends ReactiveCrudRepository<DebtEntity, Long> {

    // 11. Deuda total
    @Query("""
            SELECT SUM(current_debt)
            FROM debts
            WHERE user_id = :userId
            """)
    Mono<BigDecimal> getTotalDebt(Long userId);

    Flux<DebtEntity> findAllByUserId(Long userId);


    // Búsqueda por descripción (name en la base)
    @Query("SELECT * FROM debts WHERE user_id = :userId AND name ILIKE '%' || :description || '%'")
    Flux<DebtEntity> findByUserIdAndDescription(Long userId, String description);

    // Rango de fechas
    @Query("SELECT * FROM debts WHERE user_id = :userId AND due_date BETWEEN :start AND :end")
    Flux<DebtEntity> findByUserIdAndDueDateBetween(Long userId, LocalDate start, LocalDate end);

    @Query("""
    SELECT * FROM debts
    WHERE user_id = :userId
      AND (:description IS NULL OR name ILIKE '%' || :description || '%')
      AND (:start IS NULL OR :end IS NULL OR due_date BETWEEN :start AND :end)
""")
    Flux<DebtEntity> findByUserIdAndDescriptionAndDateRange(Long userId, String description,
                                                            LocalDate start, LocalDate end);

}
