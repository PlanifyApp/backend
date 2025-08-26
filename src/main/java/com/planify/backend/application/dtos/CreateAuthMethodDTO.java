package com.planify.backend.application.dtos;

public class CreateAuthMethodDTO {

    private Integer userId;
    private String provider;
    private String password; // solo se usa si provider = "local"
    private String providerUserId;

    public CreateAuthMethodDTO() {}

    public CreateAuthMethodDTO(Integer userId, String provider, String password, String providerUserId) {
        this.userId = userId;
        this.provider = provider;
        this.password = password;
        this.providerUserId = providerUserId;
    }

    // Getters y setters
    public Integer getUserId() { return userId; }
    public void setUserId(Integer userId) { this.userId = userId; }

    public String getProvider() { return provider; }
    public void setProvider(String provider) { this.provider = provider; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getProviderUserId() { return providerUserId; }
    public void setProviderUserId(String providerUserId) { this.providerUserId = providerUserId; }
}
