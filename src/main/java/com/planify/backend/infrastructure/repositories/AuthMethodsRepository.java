package com.planify.backend.infrastructure.repositories;

import com.planify.backend.domain.models.AuthMethodsEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface AuthMethodsRepository extends ReactiveCrudRepository<AuthMethodsEntity, Long> {
    Flux<AuthMethodsEntity> findByUserId(Integer userId);
    Flux<AuthMethodsEntity> findByProvider(String provider);
    Mono<AuthMethodsEntity> findByUserIdAndProvider(Integer userId, String provider);
}
