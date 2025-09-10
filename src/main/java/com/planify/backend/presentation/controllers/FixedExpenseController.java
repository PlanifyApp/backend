package com.planify.backend.presentation.controllers;

import com.planify.backend.application.dtos.CreateFixedExpenseDTO;
import com.planify.backend.application.dtos.FixedExpenseDTO;
import com.planify.backend.application.use_cases.FixedExpenseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/fixed-expenses")
public class FixedExpenseController {

    private final FixedExpenseService service;

    public FixedExpenseController(FixedExpenseService service) {
        this.service = service;
    }

    @PostMapping
    public Mono<ResponseEntity<FixedExpenseDTO>> create(@Valid @RequestBody Mono<CreateFixedExpenseDTO> dtoMono){
        return dtoMono
                .flatMap(service::create)
                .map(saved -> ResponseEntity.ok(saved));
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<FixedExpenseDTO>> getById(@PathVariable Long id){
        return service.findById(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping("/user/{userId}")
    public Flux<FixedExpenseDTO> getByUser(@PathVariable Integer userId){
        return service.findByUser(userId);
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<FixedExpenseDTO>> update(@PathVariable Long id,
                                             @Valid @RequestBody Mono<CreateFixedExpenseDTO> dtoMono){
        return dtoMono
                .flatMap(dto -> service.update(id, dto))
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> delete(@PathVariable Long id){
        return service.delete(id)
                .then(Mono.just(ResponseEntity.noContent().<Void>build()));
    }
}
