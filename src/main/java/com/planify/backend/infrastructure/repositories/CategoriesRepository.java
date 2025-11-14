package com.planify.backend.infrastructure.repositories;

import com.planify.backend.application.dtos.CategoryStatsResponse;
import com.planify.backend.domain.models.CategoryEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CategoriesRepository extends ReactiveCrudRepository<CategoryEntity, Long> {

    @Query("""
    UPDATE categories
    SET name = $2,
        budgeted = $3,
        percent_spent = $4,
        user_id = $5,
        type = CAST($6 AS category_type_enum)
    WHERE id = $1
    RETURNING *
""")
    Mono<CategoryEntity> updateWithEnumCast(Long id, String name, Integer budgeted, Double percentSpent, Integer userId, String type);
    Mono<CategoryEntity> findByName(String name);
    Flux<CategoryEntity> findByUserId(Integer userId);
    Mono<CategoryEntity> findByIdAndUserId(Long id, Long userId);
    @Query("SELECT * FROM categories WHERE user_id = :userId AND type = CAST(:type AS category_type_enum)")
    Flux<CategoryEntity> findByUserIdAndType(Integer userId, String type);


    @Query("""
        INSERT INTO categories (name, budgeted, percent_spent, user_id, type)
        VALUES ($1, $2, $3, $4, CAST($5 AS category_type_enum))
        RETURNING *
    """)
    Mono<CategoryEntity> insertCategory(String name, Integer budgeted, Double percentSpent, Integer userId, String type);

}
