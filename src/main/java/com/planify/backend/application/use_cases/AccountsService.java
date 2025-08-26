package com.planify.backend.application.use_cases;


import com.planify.backend.application.dtos.AccountsCreateDTO;
import com.planify.backend.application.dtos.AccountsResponseDTO;
import com.planify.backend.application.dtos.AccountsUpdateDTO;
import com.planify.backend.domain.models.AccountsEntity;
import com.planify.backend.infrastructure.repositories.AccountsRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountsService {
    private final AccountsRepository accountsRepository;

    public AccountsService(AccountsRepository accountsRepository) {
        this.accountsRepository = accountsRepository;
    }

    public List<AccountsResponseDTO> getAllAccounts() {
        List<AccountsEntity> entities = accountsRepository.findAll();
        List<AccountsResponseDTO> dtos = entities.stream().map(entity -> new AccountsResponseDTO(entity.getId(), entity.getName(), entity.getQuota(), entity.getBudgeted(), entity.getCurrentValue())).collect(Collectors.toList());
        return dtos;

    }

    public AccountsResponseDTO getAccountById(Long id) {
        AccountsEntity entity = accountsRepository.findById(id).orElse(null);
        AccountsResponseDTO dto = new AccountsResponseDTO(entity.getId(), entity.getName(), entity.getQuota(), entity.getBudgeted(), entity.getCurrentValue());
        return dto;
    }

    public AccountsResponseDTO createAccount(AccountsCreateDTO dto) {
        boolean exists = (accountsRepository.existsById(dto.getId()) || accountsRepository.existsByWalletId(dto.getWallet_id()));
        if (exists) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "La cuenta ya existe");
        }

        AccountsEntity entity = new AccountsEntity();
        entity.setId(dto.getId());
        entity.setWalletId(dto.getWallet_id());
        entity.setName(dto.getName());
        entity.setQuota(dto.getQuota());
        entity.setBudgeted(dto.getBudgeted());
        entity.setCurrentValue(dto.getCurrent_value());
        entity.setUserId(dto.getUser_id());

        AccountsEntity saved = accountsRepository.save(entity);

        return new AccountsResponseDTO(
                saved.getId(), saved.getName(), saved.getQuota(), saved.getBudgeted(), saved.getCurrentValue()
        );
    }

    public AccountsResponseDTO updateAccount(Long id, AccountsUpdateDTO dto) {
        AccountsEntity entity = accountsRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "La cuenta no existe"));

        if (dto.getName() != null) {
            entity.setName(dto.getName());
        }
        if (dto.getQuota() != null) {
            entity.setQuota(dto.getQuota());
        }
        if (dto.getBudgeted() != null) {
            entity.setBudgeted(dto.getBudgeted());
        }
        if (dto.getCurrentValue() != null) {
            entity.setCurrentValue(dto.getCurrentValue());
        }

        AccountsEntity updated = accountsRepository.save(entity);

        return new AccountsResponseDTO(
                updated.getId(), updated.getName(), updated.getQuota(), updated.getBudgeted(), updated.getCurrentValue()
        );
    }

    public void deleteAccount(Long id) {
        AccountsEntity entity = accountsRepository.findById(id).orElseThrow(() ->  new ResponseStatusException(HttpStatus.NOT_FOUND, "La cuenta no existe"));

        accountsRepository.delete(entity);
    }
}
