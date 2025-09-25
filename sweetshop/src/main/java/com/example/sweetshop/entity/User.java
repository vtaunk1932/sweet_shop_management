package com.example.sweetshop.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(unique = true)
    private String email;

    private String password;

    private Boolean isAdmin = false;

    // You can keep custom methods if they provide additional logic
    public boolean isAdmin() {
        return this.isAdmin;
    }

    public String getRole() {
        return isAdmin ? "ROLE_ADMIN" : "ROLE_USER";
    }
}