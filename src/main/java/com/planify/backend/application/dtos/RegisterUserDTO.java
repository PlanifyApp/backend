package com.planify.backend.application.dtos;

import com.planify.backend.domain.models.UsersEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RegisterUserDTO {
    private String email;
    private String password;
}
