package com.planify.backend.presentation.controllers;

import com.planify.backend.application.use_cases.AccountsService;
import com.planify.backend.domain.models.AccountsEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/accounts")
@RequiredArgsConstructor
public class AccountsController {
    private final AccountsService accountsService;

    @GetMapping
    public ResponseEntity<List<AccountsEntity>> getAllAccounts() {
        List<AccountsEntity> accounts = accountsService.getAllAccounts();
        log.info("Fetching all accounts");
        return ResponseEntity.ok(accounts);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountsEntity> getAccountById(@PathVariable Long id) {
        AccountsEntity account = accountsService.getAccountById(id);
        log.info("Fetching account by id: " + id);
        return ResponseEntity.ok(account);
    }

}
