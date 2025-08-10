package com.planify.backend.presentation.controllers;

import com.planify.backend.application.use_cases.UsersService;
import com.planify.backend.domain.models.UsersEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UsersController {
    private final UsersService usersService;

    public UsersController(UsersService usersService) {
        this.usersService = usersService;
    }

//    @PostMapping
//    public UsersEntity createUser(@RequestBody UsersEntity user){
//        System.out.println("Creating user: " + user);
//        return usersService.createUser(user);
//    }

    @GetMapping
    public Iterable<UsersEntity> getAllUsers() {
        System.out.println("Fetching all users");
        return usersService.getAllUsers();
    }

}
