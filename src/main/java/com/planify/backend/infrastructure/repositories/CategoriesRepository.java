package com.planify.backend.infrastructure.repositories;

import com.planify.backend.domain.models.CategoryEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface CategoriesRepository extends ReactiveCrudRepository<CategoryEntity, Long> {

    // 3. Total Presupuesto
    @Query("""
            SELECT COALESCE(SUM(budgeted), 0)
            FROM categories
            WHERE user_id = :userId
            """)
    Mono<BigDecimal> getTotalBudget(Long userId);

    // 13. Gastos por categoría
    @Query("""
            SELECT
                c.name AS category,
                SUM(t.amount) AS total
            FROM categories c
            LEFT JOIN transactions t ON t.category_id = c.id AND t.type = 'expense'
            WHERE c.user_id = :userId
            GROUP BY c.id, c.name
            """)
    Flux<CategoryExpenseProjection> getExpensesByCategory(Long userId);

    // 14. Presupuesto por categoría
    @Query("""
            SELECT name AS category, budgeted
            FROM categories
            WHERE user_id = :userId
            """)
    Flux<CategoryBudgetProjection> getBudgetByCategory(Long userId);

    // 15. Gastos (solo expense)
    @Query("""
            SELECT
                c.name AS category,
                SUM(t.amount) AS amount
            FROM transactions t
            LEFT JOIN categories c ON c.id = t.category_id
            WHERE t.user_id = :userId
              AND t.type = 'expense'
            GROUP BY c.name
            """)
    Flux<CategoryExpenseProjection> getExpensesOnly(Long userId);


    public interface CategoryExpenseProjection {
        String getCategory();
        BigDecimal getTotal();
    }

    public interface CategoryBudgetProjection {
        String getCategory();
        BigDecimal getBudgeted();
    }




    @Query("""
    UPDATE categories
    SET name = $2,
        budgeted = $3,
        user_id = $4,
        type = CAST($5 AS category_type_enum)
    WHERE id = $1
    RETURNING *
    """)
    Mono<CategoryEntity> updateWithEnumCast(Long id, String name, Integer budgeted, Integer userId, String type);

    Flux<CategoryEntity> findByUserId(Integer userId);

    Mono<CategoryEntity> findByIdAndUserId(Long id, Long userId);

    @Query("SELECT * FROM categories WHERE user_id = :userId AND type = CAST(:type AS category_type_enum)")
    Flux<CategoryEntity> findByUserIdAndType(Integer userId, String type);

    @Query("""
        INSERT INTO categories (name, budgeted, user_id, type)
        VALUES ($1, $2, $3, CAST($4 AS category_type_enum))
        RETURNING *
    """)
    Mono<CategoryEntity> insertCategory(String name, Integer budgeted, Integer userId, String type);


    @Query("""
    SELECT 
        c.id AS category_id,
        c.name AS category_name,
        c.type AS category_type,
        c.budgeted AS category_budgeted,
        t.date_time AS date,
        t.description AS description,
        t.amount AS amount
    FROM transactions t
    JOIN categories c ON c.id = t.category_id
    WHERE t.user_id = $1
      AND ($2::text IS NULL OR c.type = $2::category_type_enum)
      AND ($3::timestamp IS NULL OR t.date_time >= $3)
      AND ($4::timestamp IS NULL OR t.date_time <= $4)
      AND ($5::text IS NULL OR LOWER(t.description) LIKE LOWER($5))
    ORDER BY c.id, t.date_time DESC
""")
    Flux<TransactionCategoryProjection> findTransactionsByCategory(
            Integer userId,
            String type,
            LocalDateTime startDate,
            LocalDateTime endDate,
            String description   // <-- NUEVO
    );

    public record TransactionCategoryProjection(
            Long category_id,
            String category_name,
            String category_type,
            Integer category_budgeted,
            LocalDateTime date,
            String description,
            Integer amount
    ) {}


}