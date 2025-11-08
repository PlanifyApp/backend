package com.planify.backend.infrastructure.repositories;

import com.planify.backend.domain.models.TransactionView;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.data.repository.query.Param;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface TransactionViewRepository extends ReactiveCrudRepository<TransactionView, Long> {

    @Query("""
        SELECT * FROM v_user_transactions
        WHERE user_id = :userId
        AND (:startDate IS NULL OR date >= :startDate)
        AND (:endDate IS NULL OR date <= :endDate)
        ORDER BY date DESC
        LIMIT :limit OFFSET :offset
    """)
    Flux<TransactionView> findByUserIdWithFilters(
            @Param("userId") Long userId,
            @Param("startDate") String startDate,
            @Param("endDate") String endDate,
            @Param("limit") int limit,
            @Param("offset") int offset
    );

    @Query("""
        SELECT COUNT(*) FROM v_user_transactions
        WHERE user_id = :userId
        AND (:startDate IS NULL OR date >= :startDate)
        AND (:endDate IS NULL OR date <= :endDate)
    """)
    Mono<Long> countByUserIdWithFilters(
            @Param("userId") Long userId,
            @Param("startDate") String startDate,
            @Param("endDate") String endDate
    );
}
