package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "authorities")
@Data
public class Authorities {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id_Authorities")
    private Long id;

   
    @Column(name = "authority", nullable = false, length = 50)
    private String authority;

    @ManyToOne
    @JoinColumn(name = "Id_Token", nullable = true)
    private AuthToken authToken;


    @ManyToOne
    @JoinColumn(name = "Id_User", nullable = true)
    private Users user;


}