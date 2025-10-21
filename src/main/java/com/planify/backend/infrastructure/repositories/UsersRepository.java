package com.planify.backend.infrastructure.repositories;

import com.planify.backend.domain.models.UsersEntity;

import reactor.core.publisher.Mono;

import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepository extends R2dbcRepository<UsersEntity, Long> {

    Mono<UsersEntity> findByEmail(String email);

}
