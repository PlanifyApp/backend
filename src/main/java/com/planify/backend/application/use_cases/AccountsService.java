package com.planify.backend.application.use_cases;

import com.planify.backend.application.dtos.AccountsCreateDTO;
import com.planify.backend.application.dtos.AccountsResponseDTO;
import com.planify.backend.application.dtos.AccountsSimpleResponseDTO;
import com.planify.backend.application.dtos.AccountsUpdateDTO;
import com.planify.backend.domain.models.AccountsEntity;
import com.planify.backend.infrastructure.repositories.AccountsRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class AccountsService {
    private final AccountsRepository accountsRepository;

    public AccountsService(AccountsRepository accountsRepository) {
        this.accountsRepository = accountsRepository;
    }

    public Flux<AccountsResponseDTO> getAllAccounts() {
        return accountsRepository.findAll()
                .map(this::mapToResponse);
    }
    public Flux<AccountsSimpleResponseDTO> getAccountsNamesByUserId(Long userId) {
        return accountsRepository.findAllByUserId(userId)
                .map(entity -> new AccountsSimpleResponseDTO(entity.getId(), entity.getName()));
    }


    public Mono<AccountsResponseDTO> getAccountById(Long id) {
        return accountsRepository.findById(id)
                .map(this::mapToResponse)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "La cuenta no existe")));
    }

    // 🔹 Nuevo: obtener cuentas por usuario
    public Flux<AccountsResponseDTO> getAccountsByUserId(Long userId) {
        return accountsRepository.findAllByUserId(userId)
                .map(this::mapToResponse);
    }

    public Mono<AccountsResponseDTO> createAccount(AccountsCreateDTO dto) {
        AccountsEntity entity = new AccountsEntity();
        entity.setName(dto.getName());
        entity.setQuota(dto.getQuota());
        entity.setBudgeted(dto.getBudgeted());
        entity.setCurrentValue(dto.getCurrent_value());
        entity.setUserId(dto.getUser_id());

        return accountsRepository.save(entity)
                .map(this::mapToResponse);
    }

    public Mono<AccountsResponseDTO> updateAccount(Long id, AccountsUpdateDTO dto) {
        return accountsRepository.findById(id)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "La cuenta no existe")))
                .flatMap(entity -> {
                    if (dto.getName() != null) entity.setName(dto.getName());
                    if (dto.getQuota() != null) entity.setQuota(dto.getQuota());
                    if (dto.getBudgeted() != null) entity.setBudgeted(dto.getBudgeted());
                    if (dto.getCurrentValue() != null) entity.setCurrentValue(dto.getCurrentValue());

                    return accountsRepository.save(entity).map(this::mapToResponse);
                });
    }

    public Mono<Void> deleteAccount(Long id) {
        return accountsRepository.findById(id)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "La cuenta no existe")))
                .flatMap(accountsRepository::delete);
    }

    private AccountsResponseDTO mapToResponse(AccountsEntity entity) {
        return new AccountsResponseDTO(
                entity.getId(),
                entity.getName(),
                entity.getQuota(),
                entity.getBudgeted(),
                entity.getCurrentValue()
        );
    }
}
