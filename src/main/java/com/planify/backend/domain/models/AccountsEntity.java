package com.planify.backend.domain.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.data.relational.core.mapping.Column;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table("accounts") // equivale a @Entity + @Table en JPA
public class AccountsEntity {

    @Id
    private Long id;

    @Column("wallet_id")
    private Long walletId;

    private String name;
    private Long quota;
    private Long budgeted;

    @Column("current_value")
    private Long currentValue;

    @Column("user_id")
    private Long userId;
}
