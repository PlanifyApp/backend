package com.planify.backend.presentation.controllers;

import com.planify.backend.application.dtos.SavingCreateDTO;
import com.planify.backend.application.dtos.SavingResponseDTO;
import com.planify.backend.application.dtos.SavingUpdateDTO;
import com.planify.backend.application.use_cases.SavingsService;
import com.planify.backend.domain.models.SavingEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/savings")
@RequiredArgsConstructor
public class SavingsController {

    private final SavingsService savingsService;

    @GetMapping("/user/{userId}")
    public Flux<SavingEntity> getSavingsByUser(@PathVariable Long userId) {
        return savingsService.getByUserId(userId);
    }

    @GetMapping("/user/{userId}/filter")
    public Flux<SavingResponseDTO> filterSavings(
            @PathVariable Long userId,
            @RequestParam(required = false) String description,
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate
    ) {
        LocalDate start = startDate != null ? LocalDate.parse(startDate) : null;
        LocalDate end = endDate != null ? LocalDate.parse(endDate) : null;
        return savingsService.searchSavingsDTO(userId, description, start, end);
    }


    @GetMapping("/{id}")
    public Mono<SavingEntity> getSavingById(@PathVariable Long id) {
        return savingsService.getById(id);
    }

    // ⭐ NUEVO: sumatoria de goal por usuario
    @GetMapping("/user/{userId}/goal/sum")
    public Mono<Integer> getGoalSum(@PathVariable Long userId) {
        return savingsService.getGoalSumByUser(userId);
    }

    @PostMapping
    public Mono<SavingEntity> createSaving(@RequestBody SavingCreateDTO dto) {
        return savingsService.create(dto);
    }

    @PutMapping("/{id}")
    public Mono<SavingEntity> updateSaving(@PathVariable Long id, @RequestBody SavingUpdateDTO dto) {
        return savingsService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public Mono<Void> deleteSaving(@PathVariable Long id) {
        return savingsService.delete(id);
    }
}
