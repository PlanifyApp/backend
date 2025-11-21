package com.planify.backend.presentation.controllers;
import com.planify.backend.application.dtos.CreateDebtDTO;
import com.planify.backend.application.dtos.DebtResponseDTO;
import com.planify.backend.application.dtos.UpdateDebtDTO;
import com.planify.backend.application.use_cases.DebtService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/debts")
@RequiredArgsConstructor
public class DebtController {

    private final DebtService debtService;

    @PostMapping
    public Mono<DebtResponseDTO> createDebt(@RequestBody CreateDebtDTO dto) {
        return debtService.createDebt(dto);
    }

    @GetMapping("/user/{userId}")
    public Flux<DebtResponseDTO> getDebtsByUserId(@PathVariable Long userId) {
        return debtService.getDebtsByUserId(userId);
    }

    // NUEVO endpoint
    @GetMapping("/user/{userId}/sum")
    public Mono<Integer> getTotalDebt(@PathVariable Long userId) {
        return debtService.getTotalDebtByUserId(userId);
    }

    @PutMapping("/{id}")
    public Mono<DebtResponseDTO> updateDebt(
            @PathVariable Long id,
            @RequestBody UpdateDebtDTO dto
    ) {
        return debtService.updateDebt(id, dto);
    }

    @DeleteMapping("/{id}")
    public Mono<Void> deleteDebt(@PathVariable Long id) {
        return debtService.deleteDebt(id);
    }
}

