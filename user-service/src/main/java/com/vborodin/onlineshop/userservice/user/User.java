package com.vborodin.onlineshop.userservice.user;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "USERS")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String login;
    private String email;
    private String firstName;
    private String lastName;
    private String address;
    @Column(name = "ROLE")
    @Enumerated(EnumType.STRING)
    private UserRole role;
    private boolean active;
    @Transient
    private String password;
}
