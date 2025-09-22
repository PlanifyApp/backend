package com.planify.backend.application.use_cases;

import com.planify.backend.application.dtos.AuthMethodDTO;
import com.planify.backend.application.dtos.CreateAuthMethodDTO;
import com.planify.backend.domain.models.AuthMethodsEntity;
import com.planify.backend.infrastructure.repositories.AuthMethodsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthMethodsService {

    private final AuthMethodsRepository repository;

    public Flux<AuthMethodDTO> findAll() {
        return repository.findAll().map(this::toDTO);
    }

    public Mono<AuthMethodDTO> findById(Long id) {
        return repository.findById(id).map(this::toDTO);
    }

    public Flux<AuthMethodDTO> findByUser(Integer userId) {
        return repository.findByUserId(userId).map(this::toDTO);
    }

    public Flux<AuthMethodDTO> findByProvider(String provider) {
        return repository.findByProvider(provider).map(this::toDTO);
    }

    public Mono<AuthMethodsEntity> findByUserAndProvider(Integer userId, String provider) {
        return repository.findByUserIdAndProvider(userId, provider);
    }

    public Mono<AuthMethodDTO> create(CreateAuthMethodDTO dto) {
        AuthMethodsEntity entity = toEntity(dto);
        entity.setCreatedAt(LocalDateTime.now());
        return repository.save(entity).map(this::toDTO);
    }

    public Mono<Void> delete(Long id) {
        return repository.deleteById(id);
    }

    // mapper helpers
    private AuthMethodDTO toDTO(AuthMethodsEntity e) {
        AuthMethodDTO dto = new AuthMethodDTO();
        dto.setId(e.getId());
        dto.setUserId(e.getUserId());
        dto.setProvider(e.getProvider());
        dto.setProviderUserId(e.getProviderUserId());
        dto.setCreatedAt(e.getCreatedAt());
        return dto;
    }

    private AuthMethodsEntity toEntity(CreateAuthMethodDTO dto) {
        AuthMethodsEntity e = new AuthMethodsEntity();
        e.setUserId(dto.getUserId());
        e.setProvider(dto.getProvider());
        e.setProviderUserId(dto.getProviderUserId());
        e.setPassword(dto.getPassword()); // solo aplica si provider = "local"
        return e;
    }
}
