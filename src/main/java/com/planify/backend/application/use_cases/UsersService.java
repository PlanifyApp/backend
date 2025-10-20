package com.planify.backend.application.use_cases;

import com.planify.backend.application.dtos.RegisterUserDTO;
import com.planify.backend.application.dtos.UpdateUserDTO;
import com.planify.backend.domain.models.AuthMethodsEntity;
import com.planify.backend.domain.models.UsersEntity;
import com.planify.backend.infrastructure.repositories.AuthMethodsRepository;
import com.planify.backend.infrastructure.repositories.UsersRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
public class UsersService {

    private final UsersRepository usersRepository;
    private final AuthMethodsRepository authMethodsRepository;
    private final PasswordEncoder passwordEncoder;

    public UsersService(UsersRepository usersRepository, AuthMethodsRepository authMethodsRepository, PasswordEncoder passwordEncoder) {
        this.usersRepository = usersRepository;
        this.authMethodsRepository = authMethodsRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // ✅ Registrar usuario
    public Mono<UsersEntity> registerUser(RegisterUserDTO dto) {
        UsersEntity user = UsersEntity.builder()
                .email(dto.getEmail())
                .firstname(dto.getFirstname())
                .lastname(dto.getLastname())
                .gender(dto.getGender() != null ? dto.getGender() : UsersEntity.GenderEnum.other)
                .username(dto.getUsername())
                .role("USER")
                .address(dto.getAddress() != null ? dto.getAddress() : "Sin dirección") // valor por defecto
                .createdAt(LocalDateTime.now())
                .build();

        return usersRepository.save(user)
                .flatMap(savedUser -> {
                    AuthMethodsEntity auth = AuthMethodsEntity.builder()
                            .userId(savedUser.getId().intValue())
                            .provider("local")
                            .providerUserId(String.valueOf(savedUser.getId()))
                            .password(passwordEncoder.encode(dto.getPassword()))
                            .createdAt(LocalDateTime.now())
                            .build();
                    return authMethodsRepository.save(auth).thenReturn(savedUser);
                });
    }

    // ✅ Actualizar usuario
    public Mono<UsersEntity> updateUser(Integer id, UpdateUserDTO dto, String photoUrl) {
        return usersRepository.findById(id.longValue())
                .switchIfEmpty(Mono.error(new RuntimeException("Usuario no encontrado")))
                .flatMap(user -> {
                    user.setFirstname(dto.getFirstName());
                    user.setLastname(dto.getLastName());
                    user.setEmail(dto.getEmail());
                    user.setAddress(dto.getAddress());
                    user.setRole(dto.getRole());
                    user.setGender(dto.getGender() != null ? dto.getGender() : user.getGender());
                    if (photoUrl != null && !photoUrl.isBlank()) {
                        user.setProfilePicture(photoUrl);
                    }
                    return usersRepository.save(user);
                });
    }

    // ✅ Borrado lógico
    public Mono<Void> deleteUser(Integer id) {
        return usersRepository.findById(id.longValue())
                .switchIfEmpty(Mono.error(new RuntimeException("Usuario no encontrado")))
                .flatMap(user -> {
                    user.setRole("INACTIVE");
                    return usersRepository.save(user);
                })
                .then();
    }

    // ✅ Autenticación email + password
    public Mono<UsersEntity> authenticate(String email, String password) {
        return usersRepository.findByEmail(email)
                .switchIfEmpty(Mono.error(new RuntimeException("Usuario no encontrado")))
                .flatMap(user ->
                        authMethodsRepository.findByUserIdAndProvider(user.getId().intValue(), "local")
                                .switchIfEmpty(Mono.error(new RuntimeException("Método de autenticación no encontrado")))
                                .flatMap(authMethod -> {
                                    if (passwordEncoder.matches(password, authMethod.getPassword())) {
                                        return Mono.just(user);
                                    } else {
                                        return Mono.error(new RuntimeException("Credenciales inválidas"));
                                    }
                                })
                );
    }

    // ✅ Listar todos los usuarios
    public Flux<UsersEntity> getAllUsers() {
        return usersRepository.findAll();
    }
}
