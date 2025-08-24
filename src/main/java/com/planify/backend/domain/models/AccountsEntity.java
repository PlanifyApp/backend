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
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private Long wallet_id;
    private String name;
    private Long quota;
    private Long budgeted;
    private Long current_value;
    private Long user_id;
}
