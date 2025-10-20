package com.planify.backend.presentation.controllers;

import com.planify.backend.application.dtos.VariableExpensesDTO;
import com.planify.backend.application.use_cases.VariableExpensesUseCases;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/variableExpenses")
public class VariableExpensesController {

    private final VariableExpensesUseCases variableExpensesUseCases;

    public VariableExpensesController(VariableExpensesUseCases variableExpensesUseCases) {
        this.variableExpensesUseCases = variableExpensesUseCases;
    }

    @GetMapping("/")
    public Flux<ResponseEntity<VariableExpensesDTO>> findAll() {
        return variableExpensesUseCases.findAll().map(ResponseEntity::ok).defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping("/")
    public Mono<ResponseEntity<VariableExpensesDTO>> save(VariableExpensesDTO variableExpensesDTO) {
        return variableExpensesUseCases.save(variableExpensesDTO).map(ResponseEntity::ok).defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping("/{id}")
    public Mono<ResponseEntity<VariableExpensesDTO>> findById(@PathVariable Integer id) {
        return variableExpensesUseCases.findById(id).map(ResponseEntity::ok).defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PutMapping("/")
    public Mono<ResponseEntity<VariableExpensesDTO>> update(VariableExpensesDTO variableExpensesDTO) {
        return variableExpensesUseCases.update(variableExpensesDTO).map(ResponseEntity::ok).defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> delete(@PathVariable Integer id) {
        return variableExpensesUseCases.deleteById(id).map(ResponseEntity::ok).defaultIfEmpty(ResponseEntity.notFound().build());
    }

}
