package com.planify.backend.application.dtos;

import java.time.LocalDateTime;

public class AuthMethodDTO {

    private Long id;
    private Integer userId;
    private String provider;
    private String providerUserId;
    private LocalDateTime createdAt;

    // Constructor vacío
    public AuthMethodDTO() {}

    // Constructor completo
    public AuthMethodDTO(Long id, Integer userId, String provider, String providerUserId, LocalDateTime createdAt) {
        this.id = id;
        this.userId = userId;
        this.provider = provider;
        this.providerUserId = providerUserId;
        this.createdAt = createdAt;
    }

    // Getters y setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Integer getUserId() { return userId; }
    public void setUserId(Integer userId) { this.userId = userId; }

    public String getProvider() { return provider; }
    public void setProvider(String provider) { this.provider = provider; }

    public String getProviderUserId() { return providerUserId; }
    public void setProviderUserId(String providerUserId) { this.providerUserId = providerUserId; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
