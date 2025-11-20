package com.planify.backend.infrastructure.repositories;

import com.planify.backend.domain.models.SavingEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.data.r2dbc.repository.Query;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface SavingsRepository extends ReactiveCrudRepository<SavingEntity, Long> {

    Flux<SavingEntity> findByUserId(Long userId);

    @Query("SELECT SUM(goal) FROM savings WHERE user_id = :userId")
    Mono<Integer> sumGoalsByUserId(Long userId);
}
