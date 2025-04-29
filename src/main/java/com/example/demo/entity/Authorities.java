package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "authorities")
@Data
public class Authorities {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "Id_User", nullable = true)
    private Users user;

    @Column(name = "authority", nullable = false, length = 50)
    private String authority;

    @Column(name = "username", nullable = false, length = 100)
    private String username;

}