package com.planify.backend.application.use_cases;

import com.planify.backend.application.dtos.SavingCreateDTO;
import com.planify.backend.application.dtos.SavingUpdateDTO;
import com.planify.backend.domain.models.SavingEntity;
import com.planify.backend.infrastructure.repositories.SavingsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class SavingsService {

    private final SavingsRepository savingsRepository;

    public Flux<SavingEntity> getByUserId(Long userId) {
        return savingsRepository.findByUserId(userId);
    }

    public Mono<SavingEntity> getById(Long id) {
        return savingsRepository.findById(id);
    }

    public Mono<Integer> getGoalSumByUser(Long userId) {
        return savingsRepository.sumGoalsByUserId(userId)
                .defaultIfEmpty(0);
    }

    public Mono<SavingEntity> create(SavingCreateDTO dto) {
        SavingEntity entity = SavingEntity.builder()
                .userId(dto.getUserId())
                .savingName(dto.getSavingName())
                .goal(dto.getGoal())
                .initialBalance(dto.getInitialBalance() == null ? 0 : dto.getInitialBalance())
                .expectedDeposit(dto.getExpectedDeposit() == null ? 0 : dto.getExpectedDeposit())
                .targetDate(dto.getTargetDate())
                .icon(dto.getIcon())
                .build();

        return savingsRepository.save(entity);
    }

    public Mono<SavingEntity> update(Long id, SavingUpdateDTO dto) {
        return savingsRepository.findById(id)
                .flatMap(existing -> {

                    // ❗ Sumar saldo nuevo al saldo que ya existía
                    if (dto.getInitialBalance() != null) {
                        existing.setInitialBalance(existing.getInitialBalance() + dto.getInitialBalance());
                    }

                    if (dto.getSavingName() != null) existing.setSavingName(dto.getSavingName());
                    if (dto.getGoal() != null) existing.setGoal(dto.getGoal());
                    if (dto.getExpectedDeposit() != null) existing.setExpectedDeposit(dto.getExpectedDeposit());
                    if (dto.getTargetDate() != null) existing.setTargetDate(dto.getTargetDate());
                    if (dto.getIcon() != null) existing.setIcon(dto.getIcon());

                    return savingsRepository.save(existing);
                });
    }

    public Mono<Void> delete(Long id) {
        return savingsRepository.deleteById(id);
    }
}
