package com.planify.backend.domain.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("incomes")
public class IncomesEntity {
    @Id
    private Long id;

    @Column("user_id")
    private Integer userId;

    @Column("detail")
    private String detail;

    @Column("current_value")
    private Integer currentValue = 0;
}
