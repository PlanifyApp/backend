package com.planify.backend.infrastructure.repositories;

import com.planify.backend.domain.models.AuthMethodEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthMethodRepository extends JpaRepository<AuthMethodEntity, Long> {
}
