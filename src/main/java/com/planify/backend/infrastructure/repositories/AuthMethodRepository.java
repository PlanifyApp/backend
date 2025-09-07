package com.planify.backend.infrastructure.repositories;

import com.planify.backend.domain.models.AuthMethodEntity;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthMethodRepository extends R2dbcRepository<AuthMethodEntity, Long> {
}
