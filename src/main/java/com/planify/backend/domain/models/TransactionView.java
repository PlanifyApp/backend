package com.planify.backend.domain.models;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table("v_user_transactions")
public class TransactionView {
    @Id
    private Long transactionId;
    private Long userId;
    private String description;
    private String account;
    private String category;
    private String date;
    private String type;
    private String budgeted;
    private String actual;
}
