package com.planify.backend.infrastructure.repositories;

import com.planify.backend.domain.models.Transaction;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.time.LocalDateTime;

public interface TransactionsRepository extends ReactiveCrudRepository<Transaction, Long> {

    @Query("""
        INSERT INTO transactions (user_id, account_id, category_id, type, description, amount, date_time, note)
        VALUES ($1, $2, $3, $4::transaction_type_enum, $5, $6, $7, $8)
        RETURNING *
        """)
    Mono<Transaction> saveWithEnumCast(
            Long userId,
            Long accountId,
            Long categoryId,
            String type,
            String description,
            Integer amount,
            LocalDateTime dateTime,
            String note
    );

    @Query("""
        SELECT 
            COALESCE(SUM(CASE WHEN type = 'income' THEN amount END), 0) AS total_ingresos,
            COALESCE(SUM(CASE WHEN type = 'expense' THEN amount END), 0) AS total_gastos
        FROM transactions
        WHERE user_id = $1
        """)
    Mono<UserBalanceProjection> getUserBalance(Integer userId);

    // ✅ Record interno para mapear correctamente con los alias SQL
    public record UserBalanceProjection(Long total_ingresos, Long total_gastos) {}


    @Query("""
    SELECT date_time, description, amount
    FROM transactions
    WHERE user_id = $1
      AND ($2::text IS NULL OR type = $2::transaction_type_enum)
      AND ($3::timestamp IS NULL OR date_time >= $3)
      AND ($4::timestamp IS NULL OR date_time <= $4)
    ORDER BY date_time DESC
""")
    Flux<TransactionSummaryProjection> findIncomeExpenseFiltered(
            Integer userId,
            String type,
            LocalDateTime startDate,
            LocalDateTime endDate
    );
    public record TransactionSummaryProjection(
            LocalDateTime date_time,
            String description,
            Integer amount
    ) {}
}
