package com.planify.backend.application.dtos;

import lombok.Data;

@Data
public class GoogleLoginRequest {
    private String idToken; // Se recibe el token enviado desde el frontend
}
