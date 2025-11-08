package com.planify.backend.presentation.controllers;

import com.planify.backend.application.dtos.TransactionDTO;
import com.planify.backend.application.use_cases.TransactionViewIncomeExpenseService;
import com.planify.backend.application.use_cases.TransactionViewService;
import com.planify.backend.application.use_cases.TransactionsService;
import com.planify.backend.domain.models.TransactionView;
import com.planify.backend.domain.models.TransactionViewIncomeExpense;
import com.planify.backend.shared.PagedResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;

@RestController
@RequestMapping("/api/transactions")
public class TransactionsController {

    @Autowired
    private TransactionsService transactionsService;
    
    @Autowired
    private  TransactionViewService service;

    @Autowired
    private TransactionViewIncomeExpenseService transactionViewIncomeExpenseService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<?> create(@Valid @RequestBody TransactionDTO dto) {
        return transactionsService.createTransaction(dto, dto.getUserId())
                .map(saved -> Map.of(
                        "id", saved.getId(),
                        "description", saved.getDescription(),
                        "categoryId", saved.getCategoryId(),
                        "amount", saved.getAmount(),
                        "type", saved.getType(),
                        "date", saved.getDateTime().toLocalDate(),
                        "note", saved.getNote(),
                        "createdAt", saved.getDateTime()
                ));
    }


    @GetMapping("/balance/{userId}")
    public Mono<Map<String, Object>> getBalance(@PathVariable Integer userId) {
        return transactionsService.getBalance(userId);
    }

    @GetMapping("/user/{userId}")
    public Mono<PagedResponse<TransactionView>> getByUserWithFilters(
            @PathVariable Long userId,
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return service.findByUserIdWithFilters(userId, startDate, endDate, page, size);
    }


    @GetMapping("/user/{userId}/account/{accountId}")
    public Flux<TransactionViewIncomeExpense> getByUserWithFilters(
            @PathVariable Long userId,
            @PathVariable Long accountId,
            @RequestParam(required = false) String type,
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return transactionViewIncomeExpenseService.findByUserIdWithFilters(
                userId, accountId, type, startDate, endDate, page, size
        );
    }



}
