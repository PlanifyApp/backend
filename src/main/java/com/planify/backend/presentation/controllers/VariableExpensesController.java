package com.planify.backend.presentation.controllers;

import com.planify.backend.application.dtos.VariableExpenses;
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
    public Flux<ResponseEntity<VariableExpenses>> findAll() {
        return variableExpensesUseCases.findAll().map(ResponseEntity::ok).defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping("/")
    public Mono<ResponseEntity<VariableExpenses>> save(VariableExpenses variableExpenses) {
        return variableExpensesUseCases.save(variableExpenses).map(ResponseEntity::ok).defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping("/{id}")
    public Mono<ResponseEntity<VariableExpenses>> findById(@PathVariable Integer id) {
        return variableExpensesUseCases.findById(id).map(ResponseEntity::ok).defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PutMapping("/")
    public Mono<ResponseEntity<VariableExpenses>> update(VariableExpenses variableExpenses) {
        return variableExpensesUseCases.update(variableExpenses).map(ResponseEntity::ok).defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> delete(@PathVariable Integer id) {
        return variableExpensesUseCases.deleteById(id).map(ResponseEntity::ok).defaultIfEmpty(ResponseEntity.notFound().build());
    }

}
