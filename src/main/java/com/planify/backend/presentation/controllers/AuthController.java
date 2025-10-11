package com.planify.backend.presentation.controllers;

import com.planify.backend.application.dtos.GoogleLoginRequestDTO;
import com.planify.backend.application.dtos.LoginResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import com.google.firebase.auth.FirebaseAuthException;
/* import com.planify.backend.application.use_cases.ValidateFirebaseTokenUseCase;
import com.planify.backend.domain.models.FirebaseUser; */
import org.springframework.http.ResponseEntity;

import com.planify.backend.application.use_cases.GoogleAuthUseCase;
import reactor.core.publisher.Mono;
import com.planify.backend.domain.models.UsersEntity;

@RestController
@RequestMapping("/auth")
public class AuthController {
    //private final ValidateFirebaseTokenUseCase validateToken;

    private final GoogleAuthUseCase googleAuthUseCase;

    public AuthController(/* ValidateFirebaseTokenUseCase validateToken, */ GoogleAuthUseCase googleAuthUseCase) {
        //this.validateToken = validateToken;
        this.googleAuthUseCase = googleAuthUseCase;
    }

    /* @GetMapping("/me")
    public Mono<ResponseEntity<FirebaseUser>> getAuthenticateUser(@RequestHeader("Authorization") String authHeader) throws FirebaseAuthException {
        String token = authHeader.replace("Bearer ", "");
        return validateToken.execute(token)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.status(401).build());
    } */

    @PostMapping("/google")
    public ResponseEntity<?> loginWithGoogle(@RequestBody GoogleLoginRequestDTO request) {
        try {
            UsersEntity user = googleAuthUseCase.authenticate(request);
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            return ResponseEntity.status(401).body("Token inválido o expirado: " + e.getMessage());
        }
    }
}

