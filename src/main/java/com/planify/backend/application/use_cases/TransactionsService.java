package com.planify.backend.application.use_cases;

import com.planify.backend.application.dtos.MovementsDTO;
import com.planify.backend.domain.models.Transaction;
import com.planify.backend.infrastructure.repositories.AccountsRepository;
import com.planify.backend.infrastructure.repositories.CategoriesRepository;
import com.planify.backend.infrastructure.repositories.TransactionsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

import java.util.Map;

@Service
public class TransactionsService {

    @Autowired
    private TransactionsRepository transactionsRepository;

    @Autowired
    private CategoriesRepository categoriesRepository;

    @Autowired
    private AccountsRepository accountsRepository;

    public Mono<Transaction> createTransaction(MovementsDTO dto, Long userId) {
        return accountsRepository.findByIdAndUserId(dto.getAccountId(), userId)
                .switchIfEmpty(Mono.error(new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Account not found or doesn't belong to user: " + dto.getAccountId()
                )))
                .flatMap(account -> categoriesRepository.findByName(dto.getCategory())
                        .switchIfEmpty(Mono.error(new ResponseStatusException(
                                HttpStatus.NOT_FOUND, "Category not found: " + dto.getCategory()
                        )))
                        .flatMap(category -> transactionsRepository.saveWithEnumCast(
                                userId,
                                dto.getAccountId(),
                                category.getId(),
                                dto.getType(),
                                dto.getDescription(),
                                dto.getAmount(),
                                dto.getDate().atStartOfDay(),
                                dto.getNote()
                        )));
    }

    public Mono<Map<String, Object>> getBalance(Integer userId) {
        return transactionsRepository.getUserBalance(userId)
                .doOnNext(result -> System.out.println("DEBUG RESULT: " + result))
                .map(result -> {
                    long ingresos = result.total_ingresos() != null ? result.total_ingresos() : 0L;
                    long gastos = result.total_gastos() != null ? result.total_gastos() : 0L;
                    long balance = ingresos - gastos;

                    return Map.of(
                            "total_ingresos", ingresos,
                            "total_gastos", gastos,
                            "balance", balance
                    );
                });
    }



}
