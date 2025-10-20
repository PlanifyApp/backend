package com.planify.backend.domain.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table("transactions")
public class MovementsEntity {
    @Id
    private Integer id;
    @Column("user_id")
    private Integer userId;
    @Column("account_id")
    private Integer accountId;
    @Column("category_id")
    private Integer categoryId;
    private String type;
    private String description;
    private Integer amount;
    @Column("date_time")
    private LocalDateTime dateTime;
    private String note;
}
