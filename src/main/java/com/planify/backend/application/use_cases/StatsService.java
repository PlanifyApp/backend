package com.planify.backend.application.use_cases;
import com.planify.backend.domain.models.DebtByDay;
import com.planify.backend.domain.models.IncomeExpenseByDay;
import com.planify.backend.domain.models.SavingDebtByDay;
import com.planify.backend.infrastructure.repositories.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Flux;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StatsService {

    private final TransactionsRepository transactionsRepository;
    private final CategoriesRepository categoriesRepository;
    private final SavingsRepository savingsRepository;
    private final DebtRepository debtRepository;
    private final SavingDebtByDayRepository savingDebtByDayRepository;
    private final IncomeExpenseByDayRepository incomeExpenseRepo;
    private final DebtByDayRepository debtByDayRepository;

    // ============================
    // MÉTRICAS SIMPLES
    // ============================
    public Mono<BigDecimal> getTotalIncome(Long userId) {
        return transactionsRepository.getTotalIncome(userId);
    }

    public Mono<BigDecimal> getTotalExpenses(Long userId) {
        return transactionsRepository.getTotalExpenses(userId);
    }

    public Mono<BigDecimal> getTotalBudget(Long userId) {
        return categoriesRepository.getTotalBudget(userId);
    }

    public Mono<BigDecimal> getTotal(Long userId) {
        return transactionsRepository.getTotal(userId);
    }

    public Mono<BigDecimal> getTotalGeneral(Long userId) {
        return transactionsRepository.getTotalGeneral(userId);
    }

    public Mono<BigDecimal> getTotalYear(Long userId) {
        return transactionsRepository.getTotalYear(userId);
    }

    public Mono<BigDecimal> getTotalMonth(Long userId) {
        return transactionsRepository.getTotalMonth(userId);
    }

    public Mono<BigDecimal> getDailyAverage(Long userId) {
        return transactionsRepository.getDailyAverage(userId);
    }

    public Mono<BigDecimal> getTotalDebt(Long userId) {
        return debtRepository.getTotalDebt(userId);
    }

    public Flux<DebtByDay> getDebtByDay(Long userId) {
        return debtByDayRepository.findByUser(userId);
    }

    public Flux<DebtByDay> getDebtByDay(Long userId, LocalDate start, LocalDate end) {
        return debtByDayRepository.findByUserAndRange(userId, start, end);
    }


    // ============================
    // FLUJOS (GRÁFICOS / TABLAS)
    // ============================
    public Flux<IncomeExpenseByDay> getIncomeVsExpensesByDay(Long userId) {
        return incomeExpenseRepo.findByUser(userId);
    }

    public Flux<IncomeExpenseByDay> getIncomeVsExpensesByDay(Long userId, LocalDate start, LocalDate end) {
        return incomeExpenseRepo.findByUserAndDateRange(userId, start, end);
    }


    public Flux<SavingDebtByDay> getSavingVsDebtByDay(Long userId) {
        return savingDebtByDayRepository.findByUser(userId);
    }

    public Flux<SavingDebtByDay> getSavingVsDebtByDay(Long userId, LocalDate start, LocalDate end) {
        return savingDebtByDayRepository.findByUserAndRange(userId, start, end);
    }


    public Flux<CategoriesRepository.CategoryExpenseProjection> getExpensesByCategory(Long userId) {
        return categoriesRepository.getExpensesByCategory(userId);
    }

    public Flux<CategoriesRepository.CategoryBudgetProjection> getBudgetByCategory(Long userId) {
        return categoriesRepository.getBudgetByCategory(userId);
    }

    public Flux<CategoriesRepository.CategoryExpenseProjection> getExpensesOnly(Long userId) {
        return categoriesRepository.getExpensesOnly(userId);
    }

    public Flux<SavingsRepository.SavingGoalProjection> getSavingGoals(Long userId) {
        return savingsRepository.getSavingGoals(userId);
    }

    // ============================
    // AGRUPAR TODAS LAS MÉTRICAS
    // ============================
    public Mono<StatsSummary> getFullStats(Long userId) {

        List<Mono<BigDecimal>> monos = Arrays.asList(
                getTotalIncome(userId),
                getTotalExpenses(userId),
                getTotalBudget(userId),
                getTotal(userId),
                getTotalGeneral(userId),
                getTotalYear(userId),
                getTotalMonth(userId),
                getDailyAverage(userId),
                getTotalDebt(userId)
        );

        return Mono.zip(monos, tuple -> new StatsSummary(
                (BigDecimal) tuple[0],
                (BigDecimal) tuple[1],
                (BigDecimal) tuple[2],
                (BigDecimal) tuple[3],
                (BigDecimal) tuple[4],
                (BigDecimal) tuple[5],
                (BigDecimal) tuple[6],
                (BigDecimal) tuple[7],
                (BigDecimal) tuple[8],
                (BigDecimal) tuple[9]
        ));
    }

    // ===================================================
    // DTO DE SALIDA (para evitar modelar 20 clases)
    // ===================================================
    public record StatsSummary(
            BigDecimal totalIncome,
            BigDecimal totalExpenses,
            BigDecimal totalBudget,
            BigDecimal totalBalance,
            BigDecimal totalGeneral,
            BigDecimal totalYear,
            BigDecimal totalMonth,
            BigDecimal dailyAverage,
            BigDecimal totalDebt,
            BigDecimal totalDebtPaid
    ) {}
}
