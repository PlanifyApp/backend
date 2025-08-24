package com.planify.backend.domain.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;

@Entity
@Table(name = "auth_methods")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthMethodEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    public Long user_id;
    public String provider;
    public String password;
    public String provider_user_id;
}
