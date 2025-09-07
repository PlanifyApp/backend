package com.planify.backend.application.use_cases;

import com.planify.backend.application.dtos.AuthMethodDTO;
import com.planify.backend.application.dtos.CreateAuthMethodDTO;
import com.planify.backend.domain.models.AuthMethodsEntity;
import com.planify.backend.infrastructure.repositories.AuthMethodsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class AuthMethodsService {

    private final AuthMethodsRepository repository;

    public Mono<AuthMethodsEntity> save(AuthMethodsEntity entity) {
        return repository.save(entity);
    }

    public Mono<AuthMethodsEntity> findByUserAndProvider(Integer userId, String provider) {
        return repository.findByUserIdAndProvider(userId, provider);
    }

    public AuthMethodDTO toDTO(AuthMethodsEntity entity) {
        return new AuthMethodDTO(
            entity.getId(),
            entity.getUserId(),
            entity.getProvider(),
            entity.getProviderUserId(),
            entity.getCreatedAt()
        );
    }

    public AuthMethodsEntity toEntity(CreateAuthMethodDTO dto) {
        AuthMethodsEntity entity = new AuthMethodsEntity();
        entity.setUserId(dto.getUserId());
        entity.setProvider(dto.getProvider());
        entity.setPassword(dto.getPassword());
        entity.setProviderUserId(dto.getProviderUserId());
        return entity;
    }
}
