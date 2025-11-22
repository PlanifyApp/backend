package com.planify.backend.presentation.controllers;

import com.planify.backend.application.dtos.ForgotPasswordRequestDTO;
import com.planify.backend.application.dtos.ResetPasswordDTO;
import com.planify.backend.application.use_cases.PasswordService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class PasswordController {

    private final PasswordService passwordService;

    @PostMapping("/forgot-password")
    public Mono<ResponseEntity<String>> forgotPassword(@RequestBody ForgotPasswordRequestDTO request) {
        String baseUrl = "http://localhost:8080"; // URL del frontend

        return passwordService.requestPasswordReset(request.getEmail(), baseUrl)
                .thenReturn(ResponseEntity.ok("Si el correo existe, te enviamos un enlace para restablecer la contraseña"))
                .onErrorResume(e -> Mono.just(ResponseEntity.badRequest().body(e.getMessage())));
    }

    @PostMapping("/reset-password")
    public Mono<ResponseEntity<String>> resetPassword(@RequestBody ResetPasswordDTO request) {

        return passwordService.resetPassword(request.getToken(), request.getNewPassword())
                .map(user -> ResponseEntity.ok("Contraseña actualizada correctamente"))
                .onErrorResume(e ->
                        Mono.just(ResponseEntity.badRequest().body(e.getMessage()))
                );
    }
}
