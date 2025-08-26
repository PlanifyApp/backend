package com.planify.backend.presentation.controllers;

import com.planify.backend.application.dtos.AuthMethodDTO;
import com.planify.backend.application.dtos.CreateAuthMethodDTO;
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
    public ResponseEntity<AuthMethodDTO> create(@RequestBody CreateAuthMethodDTO dto) {
        AuthMethodsEntity entity = service.save(service.toEntity(dto));
        return ResponseEntity.ok(service.toDTO(entity));
    }

    @GetMapping("/{userId}/{provider}")
    public ResponseEntity<AuthMethodDTO> getByUserAndProvider(@PathVariable Integer userId, @PathVariable String provider) {
        return service.findByUserAndProvider(userId, provider)
                .map(service::toDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
