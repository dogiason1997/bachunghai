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

    
    @Column(name = "expiration_time", nullable = false)
    private LocalDateTime expirationTime;

    
    @ManyToOne
    @JoinColumn(name = "Id_User", nullable = true)
    private Users user;

    @OneToMany(mappedBy = "authToken", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Authorities> authorities;

}
