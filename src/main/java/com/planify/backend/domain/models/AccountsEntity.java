package com.planify.backend.domain.models;

import jakarta.persistence.Entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name ="accounts")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountsEntity {
    @Id
    private Long id;
    @Column(name = "wallet_id", nullable = false,  unique = true)
    private Long walletId;

    private String name;
    private Long quota;
    private Long budgeted;

    @Column(name = "current_value")
    private Long currentValue;

    @Column(name = "user_id")
    private Long userId;
}
