package com.planify.backend.domain.models;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;

@Entity
@Table(name = "users")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsersEntity {

    public enum GenderEnum {
        male, female, other
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String profilePicture;
    private String firstname;
    private String lastname;
    private String username;

    @Enumerated(EnumType.STRING)
    private GenderEnum gender;

    private String role;
    private String address;
    private String email;
}
