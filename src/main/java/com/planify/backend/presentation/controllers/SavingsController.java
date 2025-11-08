package com.planify.backend.presentation.controllers;
import com.planify.backend.application.use_cases.SavingsService;
import com.planify.backend.domain.models.Saving;
import com.planify.backend.domain.models.UserSavingsSummary;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/savings")
@RequiredArgsConstructor
public class SavingsController {

    private final SavingsService savingsService;

    @GetMapping("/summary/{userId}")
    public Flux<UserSavingsSummary> getUserSavingsSummary(@PathVariable Long userId) {
        return savingsService.getUserSavingsSummary(userId);
    }

    @PostMapping
    public Mono<Saving> createSaving(@RequestBody Saving saving) {
        return savingsService.createSaving(saving);
    }
}