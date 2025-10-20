package com.planify.backend.application.use_cases;

import com.planify.backend.application.dtos.VariableExpensesDTO;
import com.planify.backend.application.mappers.VariableExpensesMappersApp;
import com.planify.backend.domain.interfaces.VariableExpensesRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class VariableExpensesUseCases {

    private final VariableExpensesRepository variableExpensesRepository;
    private final VariableExpensesMappersApp variableExpensesMappers;

    public VariableExpensesUseCases(VariableExpensesRepository variableExpensesRepository, VariableExpensesMappersApp variableExpensesMappers) {
        this.variableExpensesRepository = variableExpensesRepository;
        this.variableExpensesMappers = variableExpensesMappers;
    }

    public Flux<VariableExpensesDTO> findAll() {
        return variableExpensesRepository.findAll().map(variableExpensesMappers::toResponse);
    }

    public Mono<VariableExpensesDTO> findById(Integer id) {
        return variableExpensesRepository.findById(id).map(variableExpensesMappers::toResponse);
    }

    public Mono<VariableExpensesDTO> save(VariableExpensesDTO variableExpensesDTO) {
        return variableExpensesRepository.save(variableExpensesMappers.toDomain(variableExpensesDTO)).map(variableExpensesMappers::toResponse);
    }

    public Mono<Void> deleteById(Integer id) {
        return variableExpensesRepository.deleteById(id);
    }

    public Mono<VariableExpensesDTO> update(VariableExpensesDTO variableExpensesDTO) {
        return variableExpensesRepository.update(variableExpensesMappers.toDomain(variableExpensesDTO)).map(variableExpensesMappers::toResponse);
    }

}
