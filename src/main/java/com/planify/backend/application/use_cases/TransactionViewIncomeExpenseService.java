package com.planify.backend.application.use_cases;

import com.planify.backend.domain.models.TransactionViewIncomeExpense;
import com.planify.backend.infrastructure.repositories.TransactionViewIncomeExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.time.LocalDateTime;

@Service
public class TransactionViewIncomeExpenseService {

    @Autowired
    private TransactionViewIncomeExpenseRepository repository;

    public Flux<TransactionViewIncomeExpense> findByUserIdWithFilters(
            Long userId,
            Long accountId,
            String type,
            String startDate,
            String endDate,
            int page,
            int size
    ) {
        PageRequest pageable = PageRequest.of(page - 1, size);

        LocalDateTime start = startDate != null ? LocalDateTime.parse(startDate + "T00:00:00") : null;
        LocalDateTime end = endDate != null ? LocalDateTime.parse(endDate + "T23:59:59") : null;

        return repository.findByFilters(userId, accountId, type, start, end, pageable);
    }
}
