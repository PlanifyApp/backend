package com.planify.backend.application.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data // para generar getters, setters, toString, equals y hashCode
@NoArgsConstructor // para generar constructor vacío
@AllArgsConstructor // para generar constructor con todos los atributos
public class AuthMethodDTO {

    private Long id;
    private Integer userId;
    private String provider;
    private String providerUserId;
    private LocalDateTime createdAt;
}
