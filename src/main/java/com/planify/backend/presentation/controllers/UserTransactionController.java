package com.planify.backend.presentation.controllers;

import com.planify.backend.application.use_cases.UserTransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/users/{userId}/transactions")
@RequiredArgsConstructor
public class UserTransactionController {
    private final UserTransactionService service;

    @GetMapping
    public Mono<ResponseEntity<UserTransactionService.TransactionPage>> getTransactions(@PathVariable Integer userId, @RequestParam(required = false) Integer month, @RequestParam(required = false) Integer year, @RequestParam Integer page, @RequestParam Integer size) {
        return service.getTransactions(userId, month, year, page, size).map(ResponseEntity::ok);
    }
}
