package com.planify.backend.presentation.controllers;

import com.planify.backend.application.dtos.FinancialSummaryDTO;
import com.planify.backend.application.use_cases.FinancialSummaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/financial-summary")
@RequiredArgsConstructor
public class FinancialSummaryController {

    private final FinancialSummaryService service;

    @GetMapping("/{userId}")
    public Mono<FinancialSummaryDTO> getSummary(@PathVariable Long userId) {
        return service.getSummary(userId);
    }
}
