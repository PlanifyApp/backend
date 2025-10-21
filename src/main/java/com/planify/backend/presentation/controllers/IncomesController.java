package com.planify.backend.presentation.controllers;


import com.planify.backend.application.dtos.IncomeResponseDTO;
import com.planify.backend.application.use_cases.IncomesService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequestMapping("/incomes")
public class IncomesController {
    @Autowired
    private IncomesService incomesService;

    @GetMapping
    public Flux<IncomeResponseDTO> getAllIncomes() {
        return incomesService.getAllIncomes();
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<IncomeResponseDTO>> getIncomeById(@PathVariable Long id) {
        return incomesService.getIncomeById(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping("/user/{userId}")
    public Flux<IncomeResponseDTO> getIncomesByUserId(@PathVariable Integer userId) {
        return incomesService.getIncomeByUserId(userId);
    }

    @GetMapping("/user/{userId}/count")
    public Mono<ResponseEntity<Long>> countIncomesByUserId(@PathVariable Integer userId) {
        return incomesService.countByUserId(userId).map(ResponseEntity::ok);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<IncomeResponseDTO> createIncome(@RequestBody IncomeResponseDTO incomeDTO) {
        return incomesService.createIncome(incomeDTO);
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<IncomeResponseDTO>> updateIncome(
            @PathVariable Long id,
            @RequestBody IncomeResponseDTO incomeDTO) {
        return incomesService.updateIncome(id, incomeDTO)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> deleteIncome(@PathVariable Long id) {
        return incomesService.deleteIncome(id)
                .map(result -> ResponseEntity.ok().<Void>build())
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/user/{userId}")
    public Mono<ResponseEntity<Void>> deleteIncomesByUserId(@PathVariable Integer userId) {
        return incomesService.deleteIncomeByUserId(userId).map(result -> ResponseEntity.ok().<Void>build());
    }
}
