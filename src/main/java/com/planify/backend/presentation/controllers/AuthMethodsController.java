package com.planify.backend.presentation.controllers;

import com.planify.backend.application.dtos.AuthMethodDTO;
import com.planify.backend.application.dtos.CreateAuthMethodDTO;
import com.planify.backend.application.use_cases.AuthMethodsService;
import com.planify.backend.domain.models.AuthMethodsEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/auth-methods")
@RequiredArgsConstructor
public class AuthMethodsController {

    private final AuthMethodsService service;

    @PostMapping
    public Mono<ResponseEntity<AuthMethodDTO>> create(@RequestBody CreateAuthMethodDTO dto) {
        AuthMethodsEntity entity = service.toEntity(dto);
        return service.save(entity)
                .map(service::toDTO)
                .map(ResponseEntity::ok);
    }

    @GetMapping("/{userId}/{provider}")
    public Mono<ResponseEntity<AuthMethodDTO>> getByUserAndProvider(
            @PathVariable Integer userId,
            @PathVariable String provider
    ) {
        return service.findByUserAndProvider(userId, provider)
                .map(service::toDTO)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
}
