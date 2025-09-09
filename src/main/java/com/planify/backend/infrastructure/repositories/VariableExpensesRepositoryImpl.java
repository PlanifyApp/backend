package com.planify.backend.infrastructure.repositories;

import com.planify.backend.domain.interfaces.VariableExpensesRepository;
import com.planify.backend.domain.models.VariableExpenses;
import com.planify.backend.infrastructure.mappers.VariableExpensesMappers;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public class VariableExpensesRepositoryImpl implements VariableExpensesRepository {

    private final SpringDataVariableExpensesRepository springDataVariableExpensesRepository;
    private final VariableExpensesMappers variableExpensesMappers;

    public VariableExpensesRepositoryImpl(SpringDataVariableExpensesRepository springDataVariableExpensesRepository, VariableExpensesMappers variableExpensesMappers) {
        this.springDataVariableExpensesRepository = springDataVariableExpensesRepository;
        this.variableExpensesMappers = variableExpensesMappers;
    }

    @Override
    public Flux<VariableExpenses> findAll() {
        return springDataVariableExpensesRepository.findAll().map(variableExpensesMappers::toDomain);
    }

    @Override
    public Mono<VariableExpenses> findById(Integer id) {
        return springDataVariableExpensesRepository.findById(id).map(variableExpensesMappers::toDomain);
    }

    @Override
    public Mono<VariableExpenses> save(VariableExpenses variableExpenses) {
        return springDataVariableExpensesRepository.save(variableExpensesMappers.toEntity(variableExpenses)).map(variableExpensesMappers::toDomain);
    }

    @Override
    public Mono<VariableExpenses> update(VariableExpenses variableExpenses) {
        return springDataVariableExpensesRepository.save(variableExpensesMappers.toEntity(variableExpenses)).map(variableExpensesMappers::toDomain);
    }

    @Override
    public Mono<Void> deleteById(Integer id) {
        return springDataVariableExpensesRepository.deleteById(id);
    }
}
