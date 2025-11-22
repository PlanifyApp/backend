package com.planify.backend.application.use_cases;

import com.planify.backend.infrastructure.repositories.PasswordResetTokenRepository;
import com.planify.backend.infrastructure.repositories.UsersRepository;
import com.planify.backend.infrastructure.repositories.AuthMethodsRepository;
import com.planify.backend.domain.models.UsersEntity;
import com.planify.backend.domain.models.PasswordResetTokenEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PasswordService {

    private final UsersRepository usersRepository;
    private final AuthMethodsRepository authMethodsRepository;
    private final PasswordResetTokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    public Mono<Void> requestPasswordReset(String email, String baseUrl) {
        return usersRepository.findByEmail(email)
                .switchIfEmpty(Mono.error(new RuntimeException("Correo no registrado")))
                .flatMap(user -> {
                    String token = UUID.randomUUID().toString();
                    PasswordResetTokenEntity tokenEntity = PasswordResetTokenEntity.builder()
                            .userId(user.getId().intValue())
                            .token(token)
                            .expiresAt(LocalDateTime.now().plusHours(1))
                            .createdAt(LocalDateTime.now())
                            .used(false)
                            .build();
                    return tokenRepository.save(tokenEntity)
                            .flatMap(savedToken -> {
                                // Enviar email con link de reseteo
                                String resetLink = baseUrl + "/forgetPassword?token=" + token;
                                return emailService.sendPasswordResetEmail(user.getEmail(), resetLink);
                            });
                });
    }

    public Mono<UsersEntity> resetPassword(String token, String newPassword) {
        return tokenRepository.findByToken(token)
                .switchIfEmpty(Mono.error(new RuntimeException("Token inválido o expirado")))
                .flatMap(tokenEntity -> {

                    if (tokenEntity.getUsed()) {
                        return Mono.error(new RuntimeException("Este enlace ya fue usado"));
                    }

                    if (tokenEntity.getExpiresAt().isBefore(LocalDateTime.now())) {
                        return Mono.error(new RuntimeException("Token expirado"));
                    }

                    return authMethodsRepository.findByUserIdAndProvider(tokenEntity.getUserId(), "local")
                            .flatMap(auth -> {
                                auth.setPassword(passwordEncoder.encode(newPassword));

                                tokenEntity.setUsed(true);

                                return authMethodsRepository.save(auth)
                                        .then(tokenRepository.save(tokenEntity))
                                        .then(usersRepository.findById(Long.valueOf(tokenEntity.getUserId())));
                            });
                });
    }
}
