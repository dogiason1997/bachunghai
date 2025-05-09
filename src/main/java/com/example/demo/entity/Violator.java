package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Data
@Entity
@Table(name = "Violator")
public class Violator {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id_Violator")
    private Integer idViolator;

    @Column(name = "Name", length = 200, nullable = false)
    private String name;
    
    @Column(name = "Address", length = 500)
    private String address;
    
    @Column(name = "Phone", length = 20)
    private String phone;
    
    @Column(name = "Email", length = 100)
    private String email;
    
    @Column(name = "Identity_Number", length = 50)
    private String identityNumber;
    
    @Column(name = "Organization_Type", length = 50)
    private String organizationType;
    
    @Column(name = "Representative", length = 100)
    private String representative;
    
    // Relationship
    @OneToMany(mappedBy = "violator")
    @JsonIgnore
    private List<Violate> violates;
} 