package com.planify.backend.infrastructure.repositories;

import com.planify.backend.application.dtos.CategoryStatsResponse;
import com.planify.backend.domain.models.CategoryEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CategoriesRepository extends ReactiveCrudRepository<CategoryEntity, Long> {
    Mono<CategoryEntity> findByName(String name);
    Flux<CategoryEntity> findByUserId(Integer userId);
    Mono<CategoryEntity> findByIdAndUserId(Long id, Long userId);

    @Query("""
       SELECT c.name AS categoria,
              t.type AS tipo,
              SUM(t.amount) AS total
       FROM transactions t
       LEFT JOIN categories c ON t.category_id = c.id
       WHERE t.user_id = :userId
         AND (:type IS NULL OR t.type = CAST(:type AS transaction_type_enum))
       GROUP BY c.name, t.type
       ORDER BY t.type, c.name
       """)
    Flux<CategoryStatsResponse> findCategoryStatsByUserIdAndType(Integer userId, String type);



}
