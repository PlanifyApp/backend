package com.planify.backend.presentation.controllers;

import com.planify.backend.application.use_cases.StatsService;
import com.planify.backend.domain.models.IncomeExpenseByDay;
import com.planify.backend.domain.models.SavingDebtByDay;
import com.planify.backend.infrastructure.repositories.TransactionsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/stats")
@RequiredArgsConstructor
public class StatsController {

    private final StatsService statsService;

    // ============================
    // ENDPOINTS PARA MÉTRICAS SIMPLES
    // ============================

    @GetMapping("/{userId}/total-income")
    public Mono<Object> getTotalIncome(@PathVariable Long userId) {
        return statsService.getTotalIncome(userId)
                .map(total -> new Response("Total ingresos", total));
    }

    @GetMapping("/{userId}/total-expenses")
    public Mono<Object> getTotalExpenses(@PathVariable Long userId) {
        return statsService.getTotalExpenses(userId)
                .map(total -> new Response("Total gastos", total));
    }

    @GetMapping("/{userId}/total-budget")
    public Mono<Object> getTotalBudget(@PathVariable Long userId) {
        return statsService.getTotalBudget(userId)
                .map(total -> new Response("Total presupuesto", total));
    }

    @GetMapping("/{userId}/total-balance")
    public Mono<Object> getTotal(@PathVariable Long userId) {
        return statsService.getTotal(userId)
                .map(total -> new Response("Balance total", total));
    }

    @GetMapping("/{userId}/total-general")
    public Mono<Object> getTotalGeneral(@PathVariable Long userId) {
        return statsService.getTotalGeneral(userId)
                .map(total -> new Response("Total general", total));
    }

    @GetMapping("/{userId}/total-year")
    public Mono<Object> getTotalYear(@PathVariable Long userId) {
        return statsService.getTotalYear(userId)
                .map(total -> new Response("Total anual", total));
    }

    @GetMapping("/{userId}/total-month")
    public Mono<Object> getTotalMonth(@PathVariable Long userId) {
        return statsService.getTotalMonth(userId)
                .map(total -> new Response("Total mensual", total));
    }

    @GetMapping("/{userId}/daily-average")
    public Mono<Object> getDailyAverage(@PathVariable Long userId) {
        return statsService.getDailyAverage(userId)
                .map(total -> new Response("Promedio diario", total));
    }

    @GetMapping("/{userId}/total-debt")
    public Mono<Object> getTotalDebt(@PathVariable Long userId) {
        return statsService.getTotalDebt(userId)
                .map(total -> new Response("Deuda total", total));
    }


    @GetMapping("/{userId}/total-debt-paid")
    public Flux<Object> getDebtByDay(
            @PathVariable Long userId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate
    ) {

        var flux = (startDate != null || endDate != null)
                ? statsService.getDebtByDay(userId, startDate, endDate)
                : statsService.getDebtByDay(userId);

        return flux.map(row -> new DebtByDayResponse(
                row.getDate(),
                row.getDay(),
                row.getTotalPaid(),
                row.getTotalDebt()
        ));
    }

    private record DebtByDayResponse(
            LocalDate date,
            String day,
            Object totalPaid,
            Object totalDebt
    ) {}


    // ============================
    // ENDPOINTS PARA GRÁFICOS/TABLAS
    // ============================

    @GetMapping("/{userId}/income-vs-expenses-by-day")
    public Flux<Object> getIncomeVsExpensesByDay(
            @PathVariable Long userId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate
    ) {

        Flux<IncomeExpenseByDay> flux =
                (startDate != null || endDate != null)
                        ? statsService.getIncomeVsExpensesByDay(userId, startDate, endDate)
                        : statsService.getIncomeVsExpensesByDay(userId);

        return flux.map(row -> new IncomeExpenseResponse(
                row.getDate(),
                row.getDay(),
                row.getIncome(),
                row.getExpense()
        ));
    }


    @GetMapping("/{userId}/saving-vs-debt-by-day")
    public Flux<Object> getSavingVsDebtByDay(
            @PathVariable Long userId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate
    ) {

        Flux<SavingDebtByDay> flux =
                (startDate != null || endDate != null)
                        ? statsService.getSavingVsDebtByDay(userId, startDate, endDate)
                        : statsService.getSavingVsDebtByDay(userId);

        return flux.map(row -> new SavingDebtResponse(
                row.getDate(),
                row.getDay(),
                row.getTotalSavings(),
                row.getTotalDebts()
        ));
    }


    @GetMapping("/{userId}/expenses-by-category")
    public Flux<Object> getExpensesByCategory(@PathVariable Long userId) {
        return statsService.getExpensesByCategory(userId)
                .map(projection -> new CategoryResponse(
                        projection.getCategory(),
                        projection.getTotal()
                ));
    }

    @GetMapping("/{userId}/budget-by-category")
    public Flux<Object> getBudgetByCategory(@PathVariable Long userId) {
        return statsService.getBudgetByCategory(userId)
                .map(projection -> new CategoryBudgetResponse(
                        projection.getCategory(),
                        projection.getBudgeted()
                ));
    }

    @GetMapping("/{userId}/expenses-only")
    public Flux<Object> getExpensesOnly(@PathVariable Long userId) {
        return statsService.getExpensesOnly(userId)
                .map(projection -> new CategoryResponse(
                        projection.getCategory(),
                        projection.getTotal()
                ));
    }

    @GetMapping("/{userId}/saving-goals")
    public Flux<Object> getSavingGoals(@PathVariable Long userId) {
        return statsService.getSavingGoals(userId)
                .map(projection -> new SavingGoalResponse(
                        projection.getSavingName(),
                        projection.getGoal(),
                        projection.getInitialBalance(),
                        projection.getExpectedDeposit(),
                        projection.getTargetDate()
                ));
    }

    // ============================
    // ENDPOINT PARA TODAS LAS MÉTRICAS
    // ============================

    @GetMapping("/{userId}/full")
    public Mono<Object> getFullStats(@PathVariable Long userId) {
        return statsService.getFullStats(userId)
                .map(statsSummary -> statsSummary);
    }

    // ============================
    // DTOs DE RESPUESTA
    // ============================

    // DTO para respuestas simples
    private record Response(String metric, Object value) {}

    // DTO para ingresos vs gastos
    private record IncomeExpenseResponse(
            LocalDate date,  // Nuevo campo
            String day,
            Object income,
            Object expense
    ) {}

    // DTO para ahorro vs deuda
    private record SavingDebtResponse(
            LocalDate date,
            String day,
            Object saving,
            Object debt
    ) {}

    // DTO para categorías
    private record CategoryResponse(
            String category,
            Object total
    ) {}

    // DTO para presupuesto por categoría
    private record CategoryBudgetResponse(
            String category,
            Object budgeted
    ) {}

    // DTO para objetivos de ahorro
    private record SavingGoalResponse(
            String savingName,
            Object goal,
            Object initialBalance,
            Object expectedDeposit,
            Object targetDate
    ) {}
}