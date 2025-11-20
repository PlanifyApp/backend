package com.planify.backend.presentation.controllers;

import com.planify.backend.application.dtos.UserFinancialOverviewDTO;
import com.planify.backend.application.use_cases.N8nService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/n8n")
@RequiredArgsConstructor
public class N8nController {

    private final N8nService n8nService;

    @GetMapping("/user-financial-data")
    public Mono<UserFinancialOverviewDTO> getUserFinancialData(
            @RequestParam Long userId
    ) {
        return n8nService.getUserFinancialData(userId);
    }
}
