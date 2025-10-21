package com.planify.backend.application.dtos;

import com.planify.backend.domain.models.UsersEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserDTO {

    private String firstName;
    private String lastName;
    private String email;
    private String address;
    private String role;
    private UsersEntity.GenderEnum gender;
    private String password; // opcional, solo se actualiza si viene
}