package com.planify.backend.infrastructure.repositories;

import com.planify.backend.domain.models.UsersEntity;

import reactor.core.publisher.Mono;

import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepository extends R2dbcRepository<UsersEntity, Integer> {   
    // Buscar por Google ID, ya no se usa, movido a AuthMethods
    //Mono<UsersEntity> findByGoogleId(String googleId);
 
    // Buscar por email (útil en caso de verificaciones adicionales)
    Mono<UsersEntity> findByEmail(String email);
}
