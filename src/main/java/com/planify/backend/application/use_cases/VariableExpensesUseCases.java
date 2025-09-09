package com.planify.backend.application.use_cases;

import com.planify.backend.application.dtos.VariableExpenses;
import com.planify.backend.application.mappers.VariableExpensesMappers;
import com.planify.backend.domain.interfaces.VariableExpensesRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class VariableExpensesUseCases {

    private final VariableExpensesRepository variableExpensesRepository;
    private final VariableExpensesMappers variableExpensesMappers;

    public VariableExpensesUseCases(VariableExpensesRepository variableExpensesRepository, VariableExpensesMappers variableExpensesMappers) {
        this.variableExpensesRepository = variableExpensesRepository;
        this.variableExpensesMappers = variableExpensesMappers;
    }

    public Flux<VariableExpenses> findAll() {
        return variableExpensesRepository.findAll().map(variableExpensesMappers::toResponse);
    }

    public Mono<VariableExpenses> findById(Integer id) {
        return variableExpensesRepository.findById(id).map(variableExpensesMappers::toResponse);
    }

    public Mono<VariableExpenses> save(VariableExpenses variableExpenses) {
        return variableExpensesRepository.save(variableExpensesMappers.toDomain(variableExpenses)).map(variableExpensesMappers::toResponse);
    }

    public Mono<Void> deleteById(Integer id) {
        return variableExpensesRepository.deleteById(id);
    }

    public Mono<VariableExpenses> update(VariableExpenses variableExpenses) {
        return variableExpensesRepository.update(variableExpensesMappers.toDomain(variableExpenses)).map(variableExpensesMappers::toResponse);
    }

}
