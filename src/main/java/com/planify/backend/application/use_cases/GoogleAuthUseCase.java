package com.planify.backend.application.use_cases;

import com.planify.backend.application.dtos.LoginResponseDTO;
import reactor.core.publisher.Mono;

public interface GoogleAuthUseCase {
    Mono<LoginResponseDTO> execute(String idToken);
}