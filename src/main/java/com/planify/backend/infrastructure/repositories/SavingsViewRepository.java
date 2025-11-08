package com.planify.backend.infrastructure.repositories;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

import com.planify.backend.domain.models.UserSavingsSummary;

public interface SavingsViewRepository extends ReactiveCrudRepository<UserSavingsSummary, Long> {

    @Query("""
        SELECT 
            saving_id,
            saving_name,
            monthly_budget,
            total_budget
        FROM user_savings_summary
        WHERE user_id = :userId
    """)
    Flux<UserSavingsSummary> findSavingsSummaryByUserId(Long userId);
}
