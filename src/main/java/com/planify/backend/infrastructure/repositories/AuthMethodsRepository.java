package com.planify.backend.infrastructure.repositories;

import com.planify.backend.domain.models.AuthMethodsEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface AuthMethodsRepository extends ReactiveCrudRepository<AuthMethodsEntity, Long> {
    Flux<AuthMethodsEntity> findByUserId(Integer userId);
    
    Flux<AuthMethodsEntity> findByProvider(String provider);
    
    // Método para autenticación con la cuenta de Google, viene ajustada de Users
    Mono<AuthMethodsEntity> findByUserIdAndProvider(Integer userId, String provider);

    // Método para vinculación con la cuenta de Google, viene ajustada de Users
    Mono<AuthMethodsEntity> findByProviderUserIdAndProvider(String providerUserId, String provider);

    @Query("DELETE FROM auth_methods WHERE user_id = :userId")
    Mono<Void> deleteByUserId(Integer userId);

}
