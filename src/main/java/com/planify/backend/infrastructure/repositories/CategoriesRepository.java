package com.planify.backend.infrastructure.repositories;

import com.planify.backend.domain.models.CategoryEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

public interface CategoriesRepository extends ReactiveCrudRepository<CategoryEntity, Long> {

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
    ORDER BY c.id, t.date_time DESC
""")
    Flux<TransactionCategoryProjection> findTransactionsByCategory(
            Integer userId,
            String type,
            LocalDateTime startDate,
            LocalDateTime endDate
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