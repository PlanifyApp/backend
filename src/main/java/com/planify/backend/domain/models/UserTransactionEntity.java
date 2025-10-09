package com.planify.backend.domain.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table("user_transactions")
public class UserTransactionEntity {
    @Id
    private Integer id;
    @Column("user_id")
    private Integer userId;
    @Column("descripcion")
    private String description;
    @Column("cuenta")
    private String account;
    @Column("categoria")
    private String category;
    @Column("fecha")
    private LocalDate date;
    @Column("tipo")
    private String type;
    @Column("presupuesto")
    private Integer budget;
    @Column("actual")
    private Integer currentValue;
}
