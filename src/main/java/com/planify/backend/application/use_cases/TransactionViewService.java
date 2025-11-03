package com.planify.backend.application.use_cases;

import com.planify.backend.domain.models.TransactionView;
import com.planify.backend.infrastructure.repositories.TransactionViewRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class TransactionViewService {

    private final TransactionViewRepository repository;

    public TransactionViewService(TransactionViewRepository repository) {
        this.repository = repository;
    }

    public Flux<TransactionView> findByUserIdWithFilters(Long userId, String startDate, String endDate, int page, int size) {
        int offset = (page - 1) * size;
        return repository.findByUserIdWithFilters(userId, startDate, endDate, size, offset);
    }
}
