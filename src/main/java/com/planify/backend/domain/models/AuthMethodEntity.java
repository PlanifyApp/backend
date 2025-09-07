package com.planify.backend.domain.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.data.relational.core.mapping.Column;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("auth_methods")
public class AuthMethodEntity {

    @Id
    private Long id;

    @Column("user_id")
    private Long userId;

    private String provider;
    private String password;

    @Column("provider_user_id")
    private String providerUserId;
}
