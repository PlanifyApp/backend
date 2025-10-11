package com.planify.backend.application.use_cases;

import com.planify.backend.application.dtos.GoogleLoginRequestDTO;
import com.planify.backend.domain.models.UsersEntity;

public interface GoogleAuthUseCase {
    UsersEntity authenticate(GoogleLoginRequestDTO request);
}