package com.planify.backend.application.use_cases;

import com.planify.backend.application.dtos.AuthMethodDTO;
import com.planify.backend.application.dtos.CreateAuthMethodDTO;
import com.planify.backend.application.dtos.GoogleLoginRequestDTO;
import com.planify.backend.domain.models.AuthMethodsEntity;
import com.planify.backend.domain.models.UsersEntity;
import com.planify.backend.infrastructure.repositories.AuthMethodsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

/**
 * ✅ AuthMethodsService
 * Gestiona los métodos de autenticación asociados a los usuarios.
 * Compatible con GoogleAuthService y futuros proveedores.
 */
@Service
@RequiredArgsConstructor
public class AuthMethodsService {

    private final AuthMethodsRepository repository;
    private final GoogleAuthService googleAuthService; // Integración con/para Google agregada

    public Flux<AuthMethodDTO> findAll() {
        System.out.println("Listando todos los métodos de autenticación...");
        return repository.findAll().map(this::toDTO);
    }

    public Mono<AuthMethodDTO> findById(Long id) {
        System.out.println("Buscando AuthMethod por ID: " + id);
        return repository.findById(id).map(this::toDTO);
    }

    public Flux<AuthMethodDTO> findByUser(Integer userId) {
        System.out.println("Buscando AuthMethods por usuario ID: " + userId);
        return repository.findByUserId(userId).map(this::toDTO);
    }

    public Flux<AuthMethodDTO> findByProvider(String provider) {
        System.out.println("Buscando AuthMethods por proveedor: " + provider);
        return repository.findByProvider(provider).map(this::toDTO);
    }

    public Mono<AuthMethodsEntity> findByUserAndProvider(Integer userId, String provider) {
        System.out.println("Buscando AuthMethod por userId=" + userId + " y provider=" + provider);
        return repository.findByUserIdAndProvider(userId, provider);
    }

    public Mono<AuthMethodDTO> create(CreateAuthMethodDTO dto) {
        System.out.println("Creando nuevo AuthMethod para provider=" + dto.getProvider());
        AuthMethodsEntity entity = toEntity(dto);
        entity.setCreatedAt(LocalDateTime.now());
        return repository.save(entity).map(this::toDTO);
    }

    public Mono<Void> delete(Long id) {
        System.out.println("Eliminando AuthMethod ID=" + id);
        return repository.deleteById(id);
    }

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

    // Autenticación delegada a Google
    public Mono<UsersEntity> authenticateWithGoogle(GoogleLoginRequestDTO request) {
        System.out.println("Iniciando autenticación con Google para token: " + request.getIdToken());
        return googleAuthService.authenticate(request);
    }
}
