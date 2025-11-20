package com.planify.backend.infrastructure.repositories;

import com.planify.backend.domain.models.DebtEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface DebtRepository extends ReactiveCrudRepository<DebtEntity, Long> {

    Flux<DebtEntity> findAllByUserId(Long userId);
}
