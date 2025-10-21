package com.planify.backend.presentation.controllers;

import com.planify.backend.application.dtos.AccountsCreateDTO;
import com.planify.backend.application.dtos.AccountsResponseDTO;
import com.planify.backend.application.dtos.AccountsUpdateDTO;
import com.planify.backend.application.use_cases.AccountsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequestMapping("/accounts")
public class AccountsController {
    private final AccountsService accountsService;

    public AccountsController(AccountsService accountsService) {
        this.accountsService = accountsService;
    }

    @GetMapping
    public Flux<AccountsResponseDTO> getAllAccounts() {
        log.info("Fetching all accounts");
        return accountsService.getAllAccounts();
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<AccountsResponseDTO>> getAccountById(@PathVariable Long id) {
        log.info("Fetching account by id: {}", id);
        return accountsService.getAccountById(id)
                .map(ResponseEntity::ok);
    }

    // 🔹 Nuevo: obtener cuentas por usuario
    @GetMapping("/user/{userId}")
    public Flux<AccountsResponseDTO> getAccountsByUserId(@PathVariable Long userId) {
        log.info("Fetching accounts for user id: {}", userId);
        return accountsService.getAccountsByUserId(userId);
    }

    @PostMapping
    public Mono<ResponseEntity<AccountsResponseDTO>> createAccount(@RequestBody AccountsCreateDTO dto) {
        log.info("Creating account: {}", dto);
        return accountsService.createAccount(dto)
                .map(created -> ResponseEntity.status(201).body(created));
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<AccountsResponseDTO>> updateAccount(@PathVariable Long id,
                                                                   @RequestBody AccountsUpdateDTO dto) {
        log.info("Updating account id: {}", id);
        return accountsService.updateAccount(id, dto)
                .map(ResponseEntity::ok);
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> deleteAccount(@PathVariable Long id) {
        log.info("Deleting account id: {}", id);
        return accountsService.deleteAccount(id)
                .then(Mono.fromSupplier(() ->
                        ResponseEntity.noContent()
                                .header(HttpHeaders.CONTENT_LENGTH, "0")
                                .build()
                ));
    }
}
