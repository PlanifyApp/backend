package com.planify.backend.application.use_cases;
import com.planify.backend.application.dtos.RegisterUserDTO;
import com.planify.backend.application.dtos.UpdateUserDTO;
import com.planify.backend.domain.models.AuthMethodsEntity;
import com.planify.backend.domain.models.UsersEntity;
import com.planify.backend.infrastructure.repositories.AuthMethodsRepository;
import com.planify.backend.infrastructure.repositories.UsersRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
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


    public Mono<UsersEntity> registerUser(RegisterUserDTO dto) {
        UsersEntity user = UsersEntity.builder()
                .email(dto.getEmail())
                .firstname(dto.getFirstname() != null ? dto.getFirstname() : "Sin nombre")
                .lastname(dto.getLastname() != null ? dto.getLastname() : "Sin apellido")
                .gender(dto.getGender())
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


    public Mono<UsersEntity> updateUser(Integer id, UpdateUserDTO dto, String photoUrl) {
        return usersRepository.findById(id.longValue())
                .switchIfEmpty(Mono.error(new RuntimeException("Usuario no encontrado")))
                .flatMap(user -> {
                    // Solo actualizar si el campo fue enviado (no null ni vacío)
                    if (dto.getFirstname() != null && !dto.getFirstname().isBlank()) {
                        user.setFirstname(dto.getFirstname());
                    }
                    if (dto.getUsername() != null && !dto.getUsername().isBlank()) {
                        user.setUsername(dto.getUsername());
                    }
                    if (dto.getLastname() != null && !dto.getLastname().isBlank()) {
                        user.setLastname(dto.getLastname());
                    }
                    if (dto.getAddress() != null && !dto.getAddress().isBlank()) {
                        user.setAddress(dto.getAddress());
                    }
                    if (dto.getRole() != null && !dto.getRole().isBlank()) {
                        user.setRole(dto.getRole());
                    }
                    if (dto.getGender() != null) {
                        user.setGender(dto.getGender());
                    }

                    // Foto opcional
                    if (photoUrl != null && !photoUrl.isBlank()) {
                        user.setProfilePicture(photoUrl);
                    }

                    // Guardar cambios
                    return usersRepository.save(user)
                            .flatMap(savedUser -> {
                                // Si viene nueva contraseña → actualizar auth_methods
                                if (dto.getPassword() != null && !dto.getPassword().isBlank()) {
                                    return authMethodsRepository.findByUserIdAndProvider(savedUser.getId().intValue(), "local")
                                            .flatMap(auth -> {
                                                auth.setPassword(passwordEncoder.encode(dto.getPassword()));
                                                return authMethodsRepository.save(auth).thenReturn(savedUser);
                                            })
                                            .switchIfEmpty(Mono.error(new RuntimeException(
                                                    "No se puede actualizar la contraseña porque el proveedor no es local")));
                                }
                                return Mono.just(savedUser);
                            });
                });
    }


    public Mono<UsersEntity> getUserById(Integer id) {
        return usersRepository.findById(id.longValue())
                .switchIfEmpty(Mono.error(new RuntimeException("Usuario no encontrado")));
    }




    public Mono<Void> deleteUser(Integer id) {
        return usersRepository.findById(id.longValue())
                .switchIfEmpty(Mono.error(new RuntimeException("Usuario no encontrado")))
                .flatMap(user -> {
                    user.setRole("INACTIVE");
                    return usersRepository.save(user);
                })
                .then();
    }


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
}
