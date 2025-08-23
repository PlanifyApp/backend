package com.planify.backend.application.use_cases;

import com.planify.backend.domain.models.AuthMethodsEntity;
import com.planify.backend.infrastructure.repositories.AuthMethodsRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthMethodsService {

    private final AuthMethodsRepository repository;

    public AuthMethodsService(AuthMethodsRepository repository) {
        this.repository = repository;
    }

    public AuthMethodsEntity save(AuthMethodsEntity entity) {
        return repository.save(entity);
    }

    public Optional<AuthMethodsEntity> findByUserAndProvider(Integer userId, String provider) {
        return repository.findByUserIdAndProvider(userId, provider);
    }
}
