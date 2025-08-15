package com.planify.backend.domain.models;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsersEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    public String profilePicture;
    public String firstname;
    public String lastname;
    public String gender;
    public String username;
    public String role;
    public String address;
    public String email;
}
