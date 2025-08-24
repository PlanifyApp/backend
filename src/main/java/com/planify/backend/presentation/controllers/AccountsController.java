package com.planify.backend.presentation.controllers;

import com.planify.backend.application.dtos.AccountsCreateDTO;
import com.planify.backend.application.dtos.AccountsResponseDTO;
import com.planify.backend.application.dtos.AccountsUpdateDTO;
import com.planify.backend.application.use_cases.AccountsService;
import com.planify.backend.domain.models.AccountsEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/accounts")
public class AccountsController {
    private final AccountsService accountsService;

    public AccountsController(AccountsService accountsService) {
        this.accountsService = accountsService;
    }

    @GetMapping
    public ResponseEntity<List<AccountsResponseDTO>> getAllAccounts() {
        List<AccountsResponseDTO> accounts = accountsService.getAllAccounts();
        log.info("Fetching all accounts");
        return ResponseEntity.ok(accounts);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountsResponseDTO> getAccountById(@PathVariable Long id) {
        AccountsResponseDTO account = accountsService.getAccountById(id);
        log.info("Fetching account by id: {}", id);
        return ResponseEntity.ok(account);
    }

    @PostMapping
    public ResponseEntity<AccountsResponseDTO> createAccount(@RequestBody AccountsCreateDTO dto) {
        AccountsResponseDTO created = accountsService.createAccount(dto);
        return ResponseEntity.status(201).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AccountsResponseDTO> updateAccount(@PathVariable Long id, @RequestBody AccountsUpdateDTO dto) {
        AccountsResponseDTO updated = accountsService.updateAccount(id, dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAccount(@PathVariable Long id) {
        accountsService.deleteAccount(id);
        return ResponseEntity.noContent().build();
    }
}
