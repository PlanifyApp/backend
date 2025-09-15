package com.planify.backend.infrastructure.repositories;

import com.planify.backend.domain.models.IncomesEntity;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface IncomesRepository extends R2dbcRepository<IncomesEntity, Long> {
    // Search all incomes by specific user
    Flux<IncomesEntity> findByUserId(Integer userId);
    // Count incomes by user
    Mono<Long> countByUserId(Integer userId);
    //Delete all incomes of a user
    Mono<Void> deleteByUserId(Integer userId);
}
