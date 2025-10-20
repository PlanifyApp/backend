package com.planify.backend.infrastructure.repositories;

import com.planify.backend.domain.models.MovementsEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

public interface MovementsRepository extends ReactiveCrudRepository<MovementsEntity, Long> {
    @Query("""
        INSERT INTO transactions (user_id, account_id, category_id, type, description, amount, date_time, note) 
        VALUES ($1, $2, $3, $4::transaction_type_enum, $5, $6, $7, $8)
        RETURNING *
        """)
    Mono<MovementsEntity> saveWithEnumCast(
            Long userId,        // $1
            Long accountId,     // $2 - Este parámetro existía pero faltaba en la query
            Long categoryId,    // $3 - Ahora es el tercer parámetro
            String type,        // $4 - Ahora es el cuarto parámetro con el cast
            String description, // $5
            Integer amount,     // $6
            LocalDateTime dateTime, // $7
            String note        // $8
    );
}
