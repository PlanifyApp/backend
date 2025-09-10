package com.planify.backend.domain.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.data.relational.core.mapping.Column;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("auth_methods")  // Nombre de la tabla en tu BD
public class AuthMethodsEntity {

    @Id
    private Long id;

    @Column("user_id")
    private Integer userId;

    private String provider;

    @Column("provider_user_id")
    private String providerUserId;

    @Column("created_at")
    private LocalDateTime createdAt;

    private String password; // si tu tabla lo maneja, útil para "local"
}
