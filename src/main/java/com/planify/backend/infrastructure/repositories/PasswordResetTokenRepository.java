package com.planify.backend.infrastructure.repositories;

import com.planify.backend.domain.models.PasswordResetTokenEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface PasswordResetTokenRepository extends ReactiveCrudRepository<PasswordResetTokenEntity, Long> {
    Mono<PasswordResetTokenEntity> findByToken(String token);
}
