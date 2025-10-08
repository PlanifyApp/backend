package com.planify.backend.infrastructure.repositories;

import com.planify.backend.domain.models.UserTransactionEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UsersTransactionRepository extends ReactiveCrudRepository<UserTransactionEntity, Integer> {
    //Paginated Query
    @Query("SELECT * FROM user_transactions " + "WHERE user_id = :userId AND EXTRACT(MONTH FROM fecha) = :month AND EXTRACT(YEAR FROM fecha) = :year " + "ORDER BY  fecha DESC " + "LIMIT :size OFFSET :offset")
    Flux<UserTransactionEntity> findByUserIdAndMonthAndYear(Integer userId, Integer month, Integer year, Integer size, Integer offset);

    //Total monthly transactions monthly for the pagination
    @Query("SELECT COUNT(*) FROM user_transactions WHERE user_id = :userId AND EXTRACT(MONTH FROM fecha) = :month AND EXTRACT(YEAR FROM fecha) = :year")
    Mono<Long> countByUserIdAndMonthAndYear(Integer userId, Integer month, Integer year);
}
