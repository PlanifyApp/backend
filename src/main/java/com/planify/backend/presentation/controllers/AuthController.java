package com.planify.backend.presentation.controllers;

import com.planify.backend.application.dtos.GoogleLoginRequestDTO;
import com.planify.backend.application.use_cases.GoogleAuthService;
import com.planify.backend.domain.models.UsersEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final GoogleAuthService googleAuthService;

    /* @GetMapping("/me")
    public Mono<ResponseEntity<FirebaseUser>> getAuthenticateUser(@RequestHeader("Authorization") String authHeader) throws FirebaseAuthException {
        String token = authHeader.replace("Bearer ", "");
        return validateToken.execute(token)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.status(401).build());
    } */

    /**
     * 🔐 Endpoint principal para autenticación con Google.
     * Recibe un idToken desde el frontend y crea o devuelve el usuario asociado.
     */
    @PostMapping("/google")
    public Mono<ResponseEntity<?>> loginWithGoogle(@RequestBody GoogleLoginRequestDTO request) {
        System.out.println("\n[AuthController] Petición de login con Google recibida...");

        return googleAuthService.authenticate(request)
            .<ResponseEntity<?>>map(user -> {
                System.out.println("Usuario autenticado: " + user.getEmail());
                return ResponseEntity.ok(user);
            })
            .onErrorResume(e -> {
                System.out.println("Error en loginWithGoogle: " + e.getMessage());
                return Mono.just(ResponseEntity
                        .status(401)
                        .body("Error de autenticación con Google: " + e.getMessage()));
            });
    }
}

