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
@Table("accounts")
public class AccountsEntity {

    @Id
    private Long id;

    @Column("name")
    private String name;

    @Column("quota")
    private Long quota;

    @Column("budgeted")
    private Long budgeted;

    @Column("current_value")
    private Long currentValue;

    @Column("user_id")
    private Long userId;
}
