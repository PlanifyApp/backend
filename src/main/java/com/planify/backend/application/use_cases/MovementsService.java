package com.planify.backend.application.use_cases;

import com.planify.backend.application.dtos.MovementsDTO;
import com.planify.backend.domain.models.MovementsEntity;
import com.planify.backend.infrastructure.repositories.AccountsRepository;
import com.planify.backend.infrastructure.repositories.CategoriesRepository;
import com.planify.backend.infrastructure.repositories.MovementsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

@Service
public class MovementsService {

    @Autowired
    private MovementsRepository movementsRepository;

    @Autowired
    private CategoriesRepository categoriesRepository;

    @Autowired
    private AccountsRepository accountsRepository;


    public Mono<MovementsEntity> createMovement(MovementsDTO dto, Long userId) {
        return accountsRepository.findByIdAndUserId(dto.getAccountId(), userId)
                .switchIfEmpty(Mono.error(new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Account not found or doesn't belong to user: " + dto.getAccountId()
                )))
                .flatMap(account -> {
                    return categoriesRepository.findByName(dto.getCategory())
                            .switchIfEmpty(Mono.error(new ResponseStatusException(
                                    HttpStatus.NOT_FOUND,
                                    "Category not found: " + dto.getCategory()
                            )))
                            .flatMap(category -> {
                                return movementsRepository.saveWithEnumCast(
                                        userId,
                                        dto.getAccountId(),
                                        category.getId(),
                                        dto.getType(),
                                        dto.getDescription(),
                                        dto.getAmount(),
                                        dto.getDate().atStartOfDay(),
                                        dto.getNote()
                                );
                            });
                });
    }
}
