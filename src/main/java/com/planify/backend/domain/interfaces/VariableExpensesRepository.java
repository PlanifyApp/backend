package com.planify.backend.domain.interfaces;

import com.planify.backend.domain.models.VariableExpenses;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface VariableExpensesRepository {

    Flux<VariableExpenses> findAll();
    Mono<VariableExpenses> findById(Integer id);
    Mono<VariableExpenses> save(VariableExpenses variableExpenses);
    Mono<VariableExpenses> update(VariableExpenses variableExpenses);
    Mono<Void> deleteById(Integer id);

}
