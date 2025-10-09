package com.planify.backend.application.dtos;

import lombok.Data;

@Data
public class GoogleLoginRequestDTO {
    private String idToken; // Se recibe el token enviado desde el frontend
}
