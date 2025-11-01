package com.planify.backend.presentation.controllers;

import com.planify.backend.application.dtos.MovementsDTO;
import com.planify.backend.application.use_cases.TransactionsService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.Map;

@RestController
@RequestMapping("/transactions")
public class TransactionsController {

    @Autowired
    private TransactionsService transactionsService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<?> create(@Valid @RequestBody MovementsDTO dto) {
        Long userId = 1L; // temporal, hasta tener autenticación
        return transactionsService.createTransaction(dto, userId)
                .map(saved -> Map.of(
                        "id", saved.getId(),
                        "description", saved.getDescription(),
                        "categoryId", saved.getCategoryId(),
                        "amount", saved.getAmount(),
                        "type", saved.getType(),
                        "date", saved.getDateTime().toLocalDate(),
                        "note", saved.getNote(),
                        "createdAt", saved.getDateTime()
                ));
    }

    @GetMapping("/balance/{userId}")
    public Mono<Map<String, Object>> getBalance(@PathVariable Integer userId) {
        return transactionsService.getBalance(userId);
    }

}
