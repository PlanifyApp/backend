package com.planify.backend.application.use_cases;

import com.planify.backend.application.dtos.IncomeResponseDTO;
import com.planify.backend.infrastructure.repositories.IncomesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class IncomesService {

    @Autowired
    private IncomesRepository incomesRepository;

    public Flux<IncomeResponseDTO> getAllIncomes() {
        return incomesRepository.findAll().map(IncomeResponseDTO::new);
    }

    public Mono<IncomeResponseDTO> getIncomeById(Long id) {
        return incomesRepository.findById(id).map(IncomeResponseDTO::new);
    }

    public Flux<IncomeResponseDTO> getIncomeByUserId(Integer userId) {
        return incomesRepository.findByUserId(userId).map(IncomeResponseDTO::new);
    }

    public Mono<IncomeResponseDTO> createIncome(IncomeResponseDTO incomeDTO) {
        return Mono.just(incomeDTO).map(IncomeResponseDTO::toEntity).flatMap(incomesRepository::save).map(IncomeResponseDTO::new);
    }

    public Mono<IncomeResponseDTO> updateIncome(Long id, IncomeResponseDTO incomeDTO) {
        return incomesRepository.findById(id).flatMap(existingIncome -> {
            existingIncome.setUserId(incomeDTO.getUserId());
            existingIncome.setDetail(incomeDTO.getDetail());
            existingIncome.setCurrentValue(incomeDTO.getCurrentValue());
            return incomesRepository.save(existingIncome);
        }).map(IncomeResponseDTO::new);
    }

    public Mono<Void> deleteIncome(Long id) {
        return incomesRepository.deleteById(id);
    }

    public Mono<Void> deleteIncomeByUserId(Integer userId) {
        return incomesRepository.deleteByUserId(userId);
    }

    public Mono<Long> countByUserId(Integer userId) {
        return incomesRepository.countByUserId(userId);
    }
}
