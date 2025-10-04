package com.planify.backend.application.dtos;

import com.planify.backend.domain.models.UsersEntity;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResponseDTO {
    private String token;
    private UsersEntity user;
}
