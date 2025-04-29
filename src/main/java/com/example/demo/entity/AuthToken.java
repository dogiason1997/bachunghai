package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Data
@Entity
@Table(name = "AuthToken")
public class AuthToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    @JsonIgnore
    @Column(name = "Id_Token")
    private Integer idToken;

    @Column(name = "Token", nullable = false, unique = true, length = 500)
    private String jwtToken;

    @Column(name = "Username", nullable = false, length = 50)
    private String username;

    @ElementCollection
    @CollectionTable(name = "AuthToken_Roles", joinColumns = @JoinColumn(name = "Id_Token"))
    @Column(name = "Role")
    private List<String> roles;

    @Column(name = "expiration_time", nullable = false)
    private LocalDateTime expirationTime;

    public AuthToken(String username, List<String> roles, String jwtToken) {
        this.username = username;
        this.roles = roles;
        this.jwtToken = jwtToken;
        this.expirationTime = LocalDateTime.now().plusHours(1); // Token expires in 1 hour
    }

    public AuthToken() {
    }
}
