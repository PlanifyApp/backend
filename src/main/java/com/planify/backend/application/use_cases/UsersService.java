package com.planify.backend.application.use_cases;

import com.planify.backend.application.dtos.RegisterUserDTO;
import com.planify.backend.domain.models.AuthMethodEntity;
import com.planify.backend.domain.models.UsersEntity;
import com.planify.backend.infrastructure.repositories.AuthMethodRepository;
import com.planify.backend.infrastructure.repositories.UsersRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class UsersService {

    private final UsersRepository usersRepository;
    private final AuthMethodRepository authMethodRepository;

    public UsersService(UsersRepository usersRepository, AuthMethodRepository authMethodRepository) {
        this.usersRepository = usersRepository;
        this.authMethodRepository = authMethodRepository;
    }

    public Mono<UsersEntity> createUser(RegisterUserDTO dto) {
        UsersEntity user = UsersEntity.builder()
                .email(dto.getEmail())
                .gender(UsersEntity.GenderEnum.other)
                .build();

        return usersRepository.save(user)
                .flatMap(savedUser -> {
                    AuthMethodEntity authMethod = AuthMethodEntity.builder()
                            .userId(savedUser.getId())
                            .provider("local")
                            .providerUserId(String.valueOf(savedUser.getId()))
                            .password(dto.getPassword())
                            .build();

                    return authMethodRepository.save(authMethod)
                            .thenReturn(savedUser); // devolvemos el usuario después de guardar el método de auth
                });
    }

    public Flux<UsersEntity> getAllUsers() {
        return usersRepository.findAll();
    }
}
