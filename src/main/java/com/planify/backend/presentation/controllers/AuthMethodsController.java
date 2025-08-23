package com.planify.backend.presentation.controllers;

import com.planify.backend.application.use_cases.AuthMethodsService;
import com.planify.backend.domain.models.AuthMethodsEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/auth-methods")
public class AuthMethodsController {

    private final AuthMethodsService service;

    public AuthMethodsController(AuthMethodsService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<AuthMethodsEntity> create(@RequestBody AuthMethodsEntity entity) {
        return ResponseEntity.ok(service.save(entity));
    }

    @GetMapping("/{userId}/{provider}")
    public ResponseEntity<AuthMethodsEntity> getByUserAndProvider(@PathVariable Integer userId, @PathVariable String provider) {
        Optional<AuthMethodsEntity> authMethod = service.findByUserAndProvider(userId, provider);
        return authMethod.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
}
