package com.planify.backend.presentation.controllers;

import com.planify.backend.application.dtos.MovementsDTO;
import com.planify.backend.application.use_cases.MovementsService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.Map;


@RestController
@RequestMapping("/movements")
public class MovementsController {
    @Autowired
    private MovementsService movementsService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<?> create(@Valid @RequestBody MovementsDTO dto) {
        Long userId = 1L;

        return movementsService.createMovement(dto, userId).map(saved -> {
            return Map.of("id", saved.getId(), "description", saved.getDescription(), "category", dto.getCategory(), "amount", saved.getAmount(), "type", saved.getType(), "date", saved.getDateTime().toLocalDate(), "note", saved.getNote(), "createdAt", saved.getDateTime());
        });
    }
}
