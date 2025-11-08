package com.planify.backend.application.use_cases;
import com.planify.backend.domain.models.Saving;
import com.planify.backend.infrastructure.repositories.CategoriesRepository;
import com.planify.backend.infrastructure.repositories.SavingsRepository;
import com.planify.backend.infrastructure.repositories.SavingsViewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import com.planify.backend.domain.models.UserSavingsSummary;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;


@Service
@RequiredArgsConstructor
public class SavingsService {

    private final SavingsViewRepository savingsViewRepository;
    private final SavingsRepository savingsRepository;

    public Flux<UserSavingsSummary> getUserSavingsSummary(Long userId) {
        return savingsViewRepository.findSavingsSummaryByUserId(userId);
    }

    public Mono<Saving> createSaving(Saving saving) {
        if (saving.getInitialBalance() == null) saving.setInitialBalance(0);
        if (saving.getMonthlyBudget() == null) saving.setMonthlyBudget(0);
        if (saving.getTotalSaved() == null) saving.setTotalSaved(0);
        if (saving.getAverage() == null) saving.setAverage(0);
        saving.setCreatedAt(LocalDateTime.now());
        return savingsRepository.save(saving);
    }
}
