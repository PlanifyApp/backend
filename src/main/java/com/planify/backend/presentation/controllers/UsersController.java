package com.planify.backend.presentation.controllers;

import com.planify.backend.application.dtos.RegisterUserDTO;
import com.planify.backend.application.use_cases.UsersService;
import com.planify.backend.domain.models.UsersEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/users")
public class UsersController {
    private final UsersService usersService;

    public UsersController(UsersService usersService) {
        this.usersService = usersService;
    }

    @PostMapping("/register")
    public Mono<UsersEntity> register(@RequestBody RegisterUserDTO user) {
        return usersService.registerUser(user);
    }

    @GetMapping
    public Flux<UsersEntity> getAllUsers() {
        return usersService.getAllUsers();
    }
}


