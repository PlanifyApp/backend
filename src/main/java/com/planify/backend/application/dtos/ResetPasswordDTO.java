package com.planify.backend.application.dtos;

import lombok.Data;

@Data
public class ResetPasswordDTO {
    private String newPassword;
    private String token; // Token que llega desde el link
}
