package com.planify.backend.infrastructure.repositories;

import com.planify.backend.domain.models.Saving;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SavingsRepository extends ReactiveCrudRepository<Saving, Long> {
}
