package com.planify.backend.presentation.controllers;
import com.planify.backend.JwtUtil;
import com.planify.backend.application.dtos.GoogleLoginRequestDTO;
import com.planify.backend.application.dtos.LoginRequestDTO;
import com.planify.backend.application.dtos.LoginResponseDTO;
import com.planify.backend.application.use_cases.GoogleAuthService;
import com.planify.backend.application.use_cases.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final GoogleAuthService googleAuthService;
    private final JwtUtil jwtUtil;
    private final UsersService authService;

    // 🔹 LOGIN NORMAL (email + password)
    @PostMapping("/login")
    public Mono<ResponseEntity<Object>> login(@RequestBody LoginRequestDTO loginReq) {
        return authService.authenticate(loginReq.getEmail(), loginReq.getPassword())
                .flatMap(user ->
                        jwtUtil.createToken(user)
                                .map(token -> ResponseEntity.ok((Object)
                                        new LoginResponseDTO(user.getEmail(), token)))
                )
                .onErrorResume(UsernameNotFoundException.class, e ->
                        Mono.just(ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                                .body(Map.of("error", e.getMessage()))))
                .onErrorResume(RuntimeException.class, e ->
                        Mono.just(ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                                .body(Map.of("error", e.getMessage()))));
    }

    // 🔹 LOGIN CON GOOGLE (no se toca)
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
                            .status(HttpStatus.UNAUTHORIZED)
                            .body(Map.of("error", "Error de autenticación con Google: " + e.getMessage())));
                });
    }
}
