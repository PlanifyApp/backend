package com.planify.backend.application.use_cases;

import com.planify.backend.application.dtos.FinancialSummaryDTO;
import com.planify.backend.infrastructure.repositories.FinancialSummaryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class FinancialSummaryService {

    private final FinancialSummaryRepository repository;

    public Mono<FinancialSummaryDTO> getSummary(Long userId) {
        return repository.findByUserId(userId);
    }
}
