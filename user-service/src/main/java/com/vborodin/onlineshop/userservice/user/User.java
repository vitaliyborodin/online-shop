package com.vborodin.onlineshop.userservice.user;

import com.fasterxml.jackson.annotation.JsonProperty;
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
    @Column(name = "STATUS")
    @Enumerated(EnumType.STRING)
    private UserStatus status;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
}
