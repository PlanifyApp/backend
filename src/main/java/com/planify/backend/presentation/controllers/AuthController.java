package com.planify.backend.presentation.controllers;

import com.google.firebase.auth.FirebaseAuthException;
import com.planify.backend.application.use_cases.ValidateFirebaseTokenUseCase;
import com.planify.backend.domain.models.FirebaseUser;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final ValidateFirebaseTokenUseCase validateToken;

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
}

