package com.planify.backend.application.dtos;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginResponseDTO {
    private String email;
    private String token;
}
