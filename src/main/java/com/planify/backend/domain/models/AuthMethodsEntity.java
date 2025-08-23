package com.planify.backend.domain.models;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "auth_methods", uniqueConstraints = {
    @UniqueConstraint(name = "unique_user_provider", columnNames = {"user_id", "provider"})
})
public class AuthMethodsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //Id del usuario - FK
    @Column(name = "user_id", nullable = false)
    private Integer userId;

    // Ej: 'local', 'google', 'firebase', etc
    @Column(nullable = false)
    private String provider;

    private String password;

    // Ej: 'google_id', 'firebase_id', etc
    @Column(name = "provider_user_id")
    private String providerUserId;

    // Fecha automática
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    // Conjunto de Getters y setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Integer getUserId() { return userId; }
    public void setUserId(Integer userId) { this.userId = userId; }

    public String getProvider() { return provider; }
    public void setProvider(String provider) { this.provider = provider; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getProviderUserId() { return providerUserId; }
    public void setProviderUserId(String providerUserId) { this.providerUserId = providerUserId; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
