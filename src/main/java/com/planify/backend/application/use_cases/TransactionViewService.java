package com.planify.backend.application.use_cases;

import com.planify.backend.domain.models.TransactionView;
import com.planify.backend.infrastructure.repositories.TransactionViewRepository;
import com.planify.backend.shared.PagedResponse;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class TransactionViewService {

    private final TransactionViewRepository repository;

    public TransactionViewService(TransactionViewRepository repository) {
        this.repository = repository;
    }

    public Mono<PagedResponse<TransactionView>> findByUserIdWithFilters(Long userId, String startDate, String endDate, int page, int size) {
        int offset = (page - 1) * size;

        Mono<List<TransactionView>> transactions = repository
                .findByUserIdWithFilters(userId, startDate, endDate, size, offset)
                .collectList();

        Mono<Long> totalCount = repository.countByUserIdWithFilters(userId, startDate, endDate);

        return Mono.zip(transactions, totalCount)
                .map(tuple -> {
                    List<TransactionView> content = tuple.getT1();
                    long totalElements = tuple.getT2();
                    int totalPages = (int) Math.ceil((double) totalElements / size);

                    return new PagedResponse<>(
                            content,
                            totalElements,
                            totalPages,
                            page,
                            size
                    );
                });
    }
}
