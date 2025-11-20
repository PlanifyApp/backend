package com.planify.backend.application.use_cases;

import com.planify.backend.application.dtos.UserFinancialOverviewDTO;
import com.planify.backend.domain.enums.CategoryType;
import com.planify.backend.domain.models.CategoryEntity;
import com.planify.backend.domain.models.SavingEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@RequiredArgsConstructor
public class N8nService {

    private final CategoriesService categoriesService;
    private final SavingsService savingsService;

    public Mono<UserFinancialOverviewDTO> getUserFinancialData(Long userId) {

        Mono<List<CategoryEntity>> incomes = categoriesService
                .getCategoriesByUserIdAndType(userId.intValue(), "income")
                .map(category -> new CategoryEntity(
                        category.getId(),
                        category.getName(),
                        category.getBudgeted(),
                        0.0,
                        userId.intValue(),
                        CategoryType.income
                ))
                .collectList();

        Mono<List<CategoryEntity>> expenses = categoriesService
                .getCategoriesByUserIdAndType(userId.intValue(), "expense")
                .map(category -> new CategoryEntity(
                        category.getId(),
                        category.getName(),
                        category.getBudgeted(),
                        0.0,
                        userId.intValue(),
                        CategoryType.expense
                ))
                .collectList();

        Mono<List<SavingEntity>> savings = savingsService
                .getByUserId(userId)
                .collectList();

        return Mono.zip(incomes, expenses, savings)
                .map(tuple -> new UserFinancialOverviewDTO(
                        tuple.getT1(),
                        tuple.getT2(),
                        tuple.getT3()
                ));
    }

}
