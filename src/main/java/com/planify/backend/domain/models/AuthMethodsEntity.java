package com.planify.backend.domain.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.data.relational.core.mapping.Column;

import java.time.LocalDateTime;

@Data
@Table("auth_methods")
public class AuthMethodsEntity {

    @Id
    private Long id;

    @Column("user_id")
    private Integer userId;

    @Column("provider")
    private String provider;

    @Column("password")
    private String password;

    @Column("provider_user_id")
    private String providerUserId;

    @Column("created_at")
    private LocalDateTime createdAt = LocalDateTime.now();
}
