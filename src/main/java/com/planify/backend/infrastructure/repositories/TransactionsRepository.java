package com.planify.backend.infrastructure.repositories;

import com.planify.backend.domain.models.Transaction;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public interface TransactionsRepository extends ReactiveCrudRepository<Transaction, Long> {

    // 1. Total Ingresos
    @Query("""
            SELECT COALESCE(SUM(amount), 0)
            FROM transactions
            WHERE user_id = :userId
              AND type = 'income'
            """)
    Mono<BigDecimal> getTotalIncome(Long userId);

    // 2. Total Gastos
    @Query("""
            SELECT COALESCE(SUM(amount), 0)
            FROM transactions
            WHERE user_id = :userId
              AND type = 'expense'
            """)
    Mono<BigDecimal> getTotalExpenses(Long userId);

    // 3. Total (ingresos - gastos)
    @Query("""
            SELECT
                (SELECT COALESCE(SUM(amount),0) FROM transactions WHERE user_id=:userId AND type='income')
              - (SELECT COALESCE(SUM(amount),0) FROM transactions WHERE user_id=:userId AND type='expense')
            """)
    Mono<BigDecimal> getTotal(Long userId);

    // ============================
    // INTERFACES DE PROYECCIÓN (SOLO UNA VEZ)
    // ============================

    // 4. Gráfico ingresos vs gastos por día
    public interface IncomeExpenseByDayProjection {
        LocalDate getDate();
        String getDay();
        Integer getIncome();
        Integer getExpense();
    }

    // 5. Gráfico ahorro vs deuda
    public interface SavingDebtByDayProjection {
        String getDay();
        Integer getSaving();  // Usa Integer
        Integer getDebt();
    }

    // ============================
    // CONSULTAS CON PROYECCIONES
    // ============================


    // 8. Total general
    @Query("""
            SELECT SUM(amount)
            FROM transactions
            WHERE user_id = :userId
            """)
    Mono<BigDecimal> getTotalGeneral(Long userId);

    // 9. Total anual
    @Query("""
            SELECT SUM(amount)
            FROM transactions
            WHERE user_id = :userId
              AND EXTRACT(YEAR FROM date_time) = EXTRACT(YEAR FROM CURRENT_DATE)
            """)
    Mono<BigDecimal> getTotalYear(Long userId);

    // 10. Total mensual
    @Query("""
            SELECT SUM(amount)
            FROM transactions
            WHERE user_id = :userId
              AND date_time >= date_trunc('month', CURRENT_DATE)
              AND date_time < date_trunc('month', CURRENT_DATE) + INTERVAL '1 month'
            """)
    Mono<BigDecimal> getTotalMonth(Long userId);

    // 11. Promedio diario del mes actual
    @Query("""
            SELECT AVG(daily_sum)
            FROM (
                  SELECT date(date_time) AS date, SUM(amount) AS daily_sum
                  FROM transactions
                  WHERE user_id = :userId
                    AND date_time >= date_trunc('month', CURRENT_DATE)
                  GROUP BY 1
            ) sub
            """)
    Mono<BigDecimal> getDailyAverage(Long userId);

    // ============================
    // OTRAS CONSULTAS (mantenerlas)
    // ============================

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