package com.planify.backend.application.use_cases;

import com.planify.backend.application.dtos.UserTransactionDTO;
import com.planify.backend.domain.models.UserTransactionEntity;
import com.planify.backend.infrastructure.repositories.UsersTransactionRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.YearMonth;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserTransactionService {
    private final UsersTransactionRepository repository;

    //Method to obtain transactions filtered and paginated
    public Mono<TransactionPage> getTransactions(Integer userId, Integer month, Integer year, Integer page, Integer size) {
        // if the month or year is null, use current year
        YearMonth current = YearMonth.now();
        int appliedMonth = (month == null) ? current.getMonthValue() : month;
        int appliedYear = (year == null) ? current.getYear() : year;

        int offset = (page - 1) * size;

        //Obtain data and total parallely
        Flux<UserTransactionEntity> transactionsFlux = repository.findByUserIdAndMonthAndYear(userId, appliedMonth, appliedYear, size, offset);
        Mono<Long> totalCountMono = repository.countByUserIdAndMonthAndYear(userId, appliedMonth, appliedYear);

        return Mono.zip(transactionsFlux.collectList(), totalCountMono).map(tuple -> {
            var transactions = tuple.getT1();
            long totalElements = tuple.getT2();
            long totalPages = (totalElements + size - 1) / size;

            var dtos = transactions.stream().map(this::toDto).collect(Collectors.toList());
            return new TransactionPage(page, size, totalPages, totalElements, dtos);
        });
    }

    private UserTransactionDTO toDto(UserTransactionEntity entity) {
        return UserTransactionDTO.builder().id(entity.getId()).description(entity.getDescription()).account(entity.getAccount()).category(entity.getCategory()).date(entity.getDate()).type(entity.getType()).budget(entity.getBudget()).currentValue(entity.getCurrentValue()).build();
    }

    @AllArgsConstructor
    @Data
    public static class TransactionPage {
        private int page;
        private int size;
        private long totalPages;
        private long totalElements;
        private List<UserTransactionDTO> transactions;
    }
}
