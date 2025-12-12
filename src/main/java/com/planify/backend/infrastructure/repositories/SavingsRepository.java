package com.planify.backend.infrastructure.repositories;

import com.planify.backend.domain.models.SavingEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.data.r2dbc.repository.Query;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface SavingsRepository extends ReactiveCrudRepository<SavingEntity, Long> {

    // 16. Objetivos de ahorro
    @Query("""
            SELECT saving_name, goal, initial_balance, expected_deposit, target_date
            FROM savings
            WHERE user_id = :userId
            """)
    Flux<SavingGoalProjection> getSavingGoals(Long userId);


    public interface SavingGoalProjection {
        String getSavingName();
        BigDecimal getGoal();
        BigDecimal getInitialBalance();
        BigDecimal getExpectedDeposit();
        LocalDate getTargetDate();
    }

    Flux<SavingEntity> findByUserId(Long userId);

    @Query("SELECT SUM(goal) FROM savings WHERE user_id = :userId")
    Mono<Integer> sumGoalsByUserId(Long userId);

    @Query("""
    SELECT * FROM savings
    WHERE user_id = :userId
      AND (:search IS NULL OR saving_name ILIKE '%' || :search || '%')
      AND (:start IS NULL OR :end IS NULL OR target_date BETWEEN :start AND :end)
""")
    Flux<SavingEntity> findByUserIdAndSearchAndDateRange(Long userId,
                                                         String search,
                                                         LocalDate start,
                                                         LocalDate end);

}
