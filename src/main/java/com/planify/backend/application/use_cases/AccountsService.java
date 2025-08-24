package com.planify.backend.application.use_cases;

import com.planify.backend.domain.models.AccountsEntity;
import com.planify.backend.infrastructure.repositories.AccountsRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class AccountsService {
    private final AccountsRepository accountsRepository;

    public AccountsService(AccountsRepository accountsRepository) {
        this.accountsRepository = accountsRepository;
    }

    public List<AccountsEntity> getAllAccounts() {
        return accountsRepository.findAll();
    }

    public AccountsEntity getAccountById(Long id) {
        return accountsRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cuenta no encontrada"));
    }
}
