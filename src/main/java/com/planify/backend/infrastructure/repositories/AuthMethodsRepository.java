package com.planify.backend.infrastructure.repositories;

import com.planify.backend.domain.models.AuthMethodsEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface AuthMethodsRepository extends ReactiveCrudRepository<AuthMethodsEntity, Long> {
    Mono<AuthMethodsEntity> findByUserIdAndProvider(Integer userId, String provider);
}
