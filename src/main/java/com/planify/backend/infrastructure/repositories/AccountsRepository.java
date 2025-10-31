package com.planify.backend.infrastructure.repositories;

import com.planify.backend.domain.models.AccountsEntity;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface AccountsRepository extends R2dbcRepository<AccountsEntity, Long> {

    Mono<Boolean> existsByWalletId(Long walletId);
    Mono<AccountsEntity> findByIdAndUserId(Long id, Long userId);
    Flux<AccountsEntity> findAllByUserId(Long userId);
}
