package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "ViolationProcessings")
public class ViolationProcessings {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id_Processing")
    private Integer idProcessing;

    @Column(name = "Processing_Date")
    private LocalDateTime processingDate;
    
    @Column(name = "Notes", length = 500)
    private String notes;
    
    // Relationships
    @ManyToOne
    @JoinColumn(name = "Id_Violate", nullable = false)
    private Violate violate;
    
    @ManyToOne
    @JoinColumn(name = "Id_Status", nullable = false)
    private ViolationStatus violationStatus;
} 