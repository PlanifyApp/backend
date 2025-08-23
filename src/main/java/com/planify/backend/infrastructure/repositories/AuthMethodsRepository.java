package com.planify.backend.infrastructure.repositories;

import com.planify.backend.domain.models.AuthMethodsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthMethodsRepository extends JpaRepository<AuthMethodsEntity, Long> {
    Optional<AuthMethodsEntity> findByUserIdAndProvider(Integer userId, String provider);
}
