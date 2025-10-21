package com.planify.backend.application.use_cases;

import com.planify.backend.application.dtos.AccountsCreateDTO;
import com.planify.backend.application.dtos.AccountsResponseDTO;
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
                .map(entity -> new AccountsResponseDTO(
                        entity.getId(),
                        entity.getName(),
                        entity.getQuota(),
                        entity.getBudgeted(),
                        entity.getCurrentValue()
                ));
    }

    public Mono<AccountsResponseDTO> getAccountById(Long id) {
        return accountsRepository.findById(id)
                .map(entity -> new AccountsResponseDTO(
                        entity.getId(),
                        entity.getName(),
                        entity.getQuota(),
                        entity.getBudgeted(),
                        entity.getCurrentValue()
                ))
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "La cuenta no existe")));
    }

    public Mono<AccountsResponseDTO> createAccount(AccountsCreateDTO dto) {
        return accountsRepository.existsById(dto.getId())
                .flatMap(exists -> {
                    if (exists) {
                        return Mono.error(new ResponseStatusException(HttpStatus.CONFLICT, "La cuenta ya existe"));
                    }
                    return accountsRepository.existsByWalletId(dto.getWallet_id())
                            .flatMap(walletExists -> {
                                if (walletExists) {
                                    return Mono.error(new ResponseStatusException(HttpStatus.CONFLICT, "La cuenta ya existe"));
                                }

                                AccountsEntity entity = new AccountsEntity();
                                entity.setId(dto.getId());
                                entity.setWalletId(dto.getWallet_id());
                                entity.setName(dto.getName());
                                entity.setQuota(dto.getQuota());
                                entity.setBudgeted(dto.getBudgeted());
                                entity.setCurrentValue(dto.getCurrent_value());
                                entity.setUserId(dto.getUser_id());

                                return accountsRepository.save(entity)
                                        .map(saved -> new AccountsResponseDTO(
                                                saved.getId(),
                                                saved.getName(),
                                                saved.getQuota(),
                                                saved.getBudgeted(),
                                                saved.getCurrentValue()
                                        ));
                            });
                });
    }

    public Mono<AccountsResponseDTO> updateAccount(Long id, AccountsUpdateDTO dto) {
        return accountsRepository.findById(id)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "La cuenta no existe")))
                .flatMap(entity -> {
                    if (dto.getName() != null) entity.setName(dto.getName());
                    if (dto.getQuota() != null) entity.setQuota(dto.getQuota());
                    if (dto.getBudgeted() != null) entity.setBudgeted(dto.getBudgeted());
                    if (dto.getCurrentValue() != null) entity.setCurrentValue(dto.getCurrentValue());

                    return accountsRepository.save(entity)
                            .map(updated -> new AccountsResponseDTO(
                                    updated.getId(),
                                    updated.getName(),
                                    updated.getQuota(),
                                    updated.getBudgeted(),
                                    updated.getCurrentValue()
                            ));
                });
    }

    public Mono<Void> deleteAccount(Long id) {
        return accountsRepository.findById(id)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "La cuenta no existe")))
                .flatMap(entity -> accountsRepository.delete(entity));
    }
}
