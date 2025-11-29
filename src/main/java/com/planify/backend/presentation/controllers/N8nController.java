package com.planify.backend.presentation.controllers;
import com.planify.backend.application.dtos.ApiResponse;
import com.planify.backend.application.dtos.ErrorResponseDTO;
import com.planify.backend.application.dtos.UserFinancialOverviewDTO;
import com.planify.backend.application.use_cases.N8nService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/n8n")
@RequiredArgsConstructor
public class N8nController {

    private final N8nService n8nService;

    @GetMapping("/user-financial-data")
    public Mono<UserFinancialOverviewDTO> getUserFinancialData(
            @RequestParam Long userId
    ) {
        return n8nService.getUserFinancialData(userId);
    }

    @GetMapping("/get-user-by-email")
    public Mono<ResponseEntity<ApiResponse>> getUserByEmail(@RequestParam String email) {
        return n8nService.getUserIdByEmail(email)
                .map(user -> ResponseEntity.ok(new ApiResponse(true, user)))
                .onErrorResume(e ->
                        Mono.just(ResponseEntity
                                .status(HttpStatus.NOT_FOUND)
                                .body(new ApiResponse(false, new ErrorResponseDTO(e.getMessage())))
                        )
                );
    }

}
