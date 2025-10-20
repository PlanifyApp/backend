package com.planify.backend.infrastructure.repositories;

import com.planify.backend.domain.models.CategoryEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CategoriesRepository extends ReactiveCrudRepository<CategoryEntity, Long> {
    Mono<CategoryEntity> findByName(String name);
    Flux<CategoryEntity> findByUserId(Integer userId);

}
