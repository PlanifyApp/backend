package com.planify.backend.presentation.controllers;

import com.planify.backend.application.dtos.AuthMethodDTO;
import com.planify.backend.application.dtos.CreateAuthMethodDTO;
import com.planify.backend.application.use_cases.AuthMethodsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/auth-methods")
@RequiredArgsConstructor
public class AuthMethodsController {

    private final AuthMethodsService service;

    @GetMapping
    public Flux<AuthMethodDTO> getAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public Mono<AuthMethodDTO> getById(@PathVariable Long id) {
        return service.findById(id);
    }

    @GetMapping("/user/{userId}")
    public Flux<AuthMethodDTO> getByUser(@PathVariable Integer userId) {
        return service.findByUser(userId);
    }

    @PostMapping
    public Mono<AuthMethodDTO> create(@RequestBody CreateAuthMethodDTO dto) {
        return service.create(dto);
    }

    @DeleteMapping("/{id}")
    public Mono<Void> delete(@PathVariable Long id) {
        return service.delete(id);
    }
}
