package com.planify.backend.presentation.controllers;

import com.planify.backend.application.dtos.RegisterUserDTO;
import com.planify.backend.application.dtos.UpdateUserDTO;
import com.planify.backend.application.use_cases.UsersService;
import com.planify.backend.domain.models.UsersEntity;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/users")
public class UsersController {

    private final UsersService usersService;

    public UsersController(UsersService usersService) {
        this.usersService = usersService;
    }

    @PostMapping("/register")
    public Mono<UsersEntity> register(@Valid @RequestBody RegisterUserDTO user) {
        return usersService.registerUser(user);
    }

    @GetMapping("/{id}")
    public Mono<UsersEntity> getUserById(@PathVariable Integer id) {
        return usersService.getUserById(id);
    }

    @PutMapping("/{id}")
    public Mono<UsersEntity> updateUser(
            @PathVariable Integer id,
            @RequestBody UpdateUserDTO dto,
            @RequestParam(required = false) String photoUrl
    ) {
        return usersService.updateUser(id, dto, photoUrl);
    }


    @DeleteMapping("/{id}")
    public Mono<Void> deleteUser(@PathVariable Integer id) {
        return usersService.deleteUser(id);
    }
}
