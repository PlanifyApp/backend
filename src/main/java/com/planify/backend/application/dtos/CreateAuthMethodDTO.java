package com.planify.backend.application.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateAuthMethodDTO {

    private Integer userId;
    private String provider;
    private String password; // nota: solo se usa si provider = "local"
    private String providerUserId;
}
