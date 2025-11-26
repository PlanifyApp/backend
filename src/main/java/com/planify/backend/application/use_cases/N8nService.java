package com.planify.backend.application.use_cases;

import com.planify.backend.application.dtos.UserFinancialOverviewDTO;
import com.planify.backend.application.dtos.DebtResponseDTO;
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
    private final DebtService debtService;

    public Mono<UserFinancialOverviewDTO> getUserFinancialData(Long userId) {

        // INGRESOS
        Mono<List<CategoryEntity>> incomes = categoriesService
                .getCategoriesByUserIdAndType(userId.intValue(), "income")
                .map(category -> new CategoryEntity(
                        category.getId(),
                        category.getName(),
                        category.getBudgeted(),
                        userId.intValue(),
                        CategoryType.income
                ))
                .collectList();

        // GASTOS
        Mono<List<CategoryEntity>> expenses = categoriesService
                .getCategoriesByUserIdAndType(userId.intValue(), "expense")
                .map(category -> new CategoryEntity(
                        category.getId(),
                        category.getName(),
                        category.getBudgeted(),
                        userId.intValue(),
                        CategoryType.expense
                ))
                .collectList();

        // AHORROS
        Mono<List<SavingEntity>> savings = savingsService
                .getByUserId(userId)
                .collectList();

        // DEUDAS 👇
        Mono<List<DebtResponseDTO>> debts = debtService
                .getDebtsByUserId(userId)
                .collectList();

        return Mono.zip(incomes, expenses, savings, debts)
                .map(tuple -> new UserFinancialOverviewDTO(
                        tuple.getT1(), // incomes
                        tuple.getT2(), // expenses
                        tuple.getT3(), // savings
                        tuple.getT4()  // debts 👈
                ));
    }
}
