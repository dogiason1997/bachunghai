package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "ViolateTypeMapping")
public class ViolateTypeMapping {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id_Mapping")
    private Integer idMapping;
    
    @Column(name = "Created_Date")
    private LocalDateTime createdDate;
    
    @Column(name = "Notes", length = 255)
    private String notes;
    
    // Relationships
    @ManyToOne
    @JoinColumn(name = "Id_Violate", nullable = false)
    private Violate violate;
    
    @ManyToOne
    @JoinColumn(name = "Id_Type", nullable = false)
    private ViolationType violationType;
} 