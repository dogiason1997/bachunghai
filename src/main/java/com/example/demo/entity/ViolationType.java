package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Data
@Entity
@Table(name = "ViolationType")
public class ViolationType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id_Type")
    private Integer idType;

    @Column(name = "Type_Name", length = 100, nullable = false)
    private String typeName;
    
    @Column(name = "Description", length = 255)
    private String description;
    
    // Relationship with join table
    @OneToMany(mappedBy = "violationType")
    private List<ViolateTypeMapping> violateTypeMappings;
} 