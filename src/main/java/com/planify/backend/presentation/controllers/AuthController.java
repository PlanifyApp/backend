package com.planify.backend.presentation.controllers;

import com.planify.backend.application.dtos.GoogleLoginRequestDTO;
import com.planify.backend.application.dtos.LoginResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import com.google.firebase.auth.FirebaseAuthException;
import com.planify.backend.application.use_cases.ValidateFirebaseTokenUseCase;
import com.planify.backend.domain.models.FirebaseUser;
import org.springframework.http.ResponseEntity;

import com.planify.backend.application.use_cases.GoogleAuthUseCase;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final ValidateFirebaseTokenUseCase validateToken;

    private final GoogleAuthUseCase googleAuthUseCase;

    public AuthController(ValidateFirebaseTokenUseCase validateToken, GoogleAuthUseCase googleAuthUseCase) {
        this.validateToken = validateToken;
        this.googleAuthUseCase = googleAuthUseCase;
    }

    @GetMapping("/me")
    public Mono<ResponseEntity<FirebaseUser>> getAuthenticateUser(@RequestHeader("Authorization") String authHeader) throws FirebaseAuthException {
        String token = authHeader.replace("Bearer ", "");
        return validateToken.execute(token)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.status(401).build());
    }

    @PostMapping("/google")
    public Mono<ResponseEntity<LoginResponseDTO>> loginWithGoogle(@RequestBody GoogleLoginRequestDTO request) {
        return googleAuthUseCase.execute(request.getIdToken())
                .map(ResponseEntity::ok)
                .onErrorResume(ex -> {
                    // mapear errores (token inválido, email no verificado, etc.)
                    return Mono.just(ResponseEntity.status(401).build());
                });
    }
}

