package com.planify.backend.infrastructure.repositories;

import com.planify.backend.domain.models.CategoryEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface CategoriesRepository extends ReactiveCrudRepository<CategoryEntity, Integer> {
    Mono<CategoryEntity> findByName(String name);

}
