package com.planify.backend.application.use_cases;

import com.planify.backend.application.dtos.GoogleLoginRequestDTO;
import com.planify.backend.domain.models.UsersEntity;

import reactor.core.publisher.Mono;

public interface GoogleAuthUseCase {
    Mono<UsersEntity> authenticate(GoogleLoginRequestDTO request);
}