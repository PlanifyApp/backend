package com.planify.backend.presentation.controllers;

import com.planify.backend.application.dtos.GoogleLoginRequestDTO;
import com.planify.backend.application.dtos.LoginResponseDTO;
import com.planify.backend.application.use_cases.GoogleAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import com.google.firebase.auth.FirebaseAuthException;
import com.planify.backend.application.use_cases.ValidateFirebaseTokenUseCase;
import com.planify.backend.domain.models.FirebaseUser;
import org.springframework.http.ResponseEntity;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final ValidateFirebaseTokenUseCase validateToken;

    private final GoogleAuthService googleAuthService;

    public AuthController(ValidateFirebaseTokenUseCase validateToken) {
        this.validateToken = validateToken;
    }

    @GetMapping("/me")
    public Mono<ResponseEntity<FirebaseUser>> getAuthenticateUser(@RequestHeader("Authorization") String authHeader) throws FirebaseAuthException {
        String token = authHeader.replace("Bearer ", "");
        return validateToken.execute(token)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.status(401).build());
    }

    @PostMapping("/google")
    public Mono<LoginResponseDTO> loginWithGoogle(@RequestBody GoogleLoginRequestDTO request) {
        return googleAuthService.authenticateWithGoogle(request.getIdToken());
    }
}

