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
@Table("users") // Nombre de la tabla en la BD
public class UsersEntity {

    public enum GenderEnum {
        male, female, other
    }

    @Id
    private Integer id; // R2DBC asigna automáticamente si la columna es SERIAL/IDENTITY

    @Column("profile_picture")
    private String profilePicture;

    @Column("firstname")
    private String firstname;

    @Column("lastname")
    private String lastname;

    @Column("username")
    private String username;

    // En R2DBC no hay @Enumerated, se guarda como texto
    @Column("gender")
    private GenderEnum gender;

    @Column("role")
    private String role;

    @Column("address")
    private String address;

    @Column("email")
    private String email;

    @Column("googleid")
    private String googleId;
}
