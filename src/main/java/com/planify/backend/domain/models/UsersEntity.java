package com.planify.backend.domain.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.annotation.Transient;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("users") // Nombre de la tabla en la BD
public class UsersEntity {

    public enum GenderEnum {
        male, female, other
    }

    @Id
    private Long id;

    @Column("profile_picture")
    private String profilePicture;

    @Column("firstname")
    private String firstname;

    @Column("lastname")
    private String lastname;

    // En R2DBC no hay @Enumerated, se guarda como texto
    @Column("gender")
    private GenderEnum gender;

    @Column("username")
    private String username;

    @Column("role")
    private String role;

    @Column("address")
    private String address;

    @Column("email")
    private String email;

    @Column("created_at")
    private java.time.LocalDateTime createdAt;

    // ⚙️ Campo no persistido, útil para devolver tokens JWT u otros
    @Transient
    private String token; // no persistido
}
