package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "ViolateStepDetail")
public class ViolateStepDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id_Detail")
    private Integer idDetail;
    
    @Column(name = "Start_Date")
    private LocalDateTime startDate;
    
    @Column(name = "End_Date")
    private LocalDateTime endDate;
    
    @Column(name = "Status", length = 50)
    private String status;
    
    @Column(name = "Notes", length = 500)
    private String notes;
    
    // Relationships
    @ManyToOne
    @JoinColumn(name = "Id_Violate", nullable = false)
    private Violate violate;
    
    @ManyToOne
    @JoinColumn(name = "Id_Step", nullable = false)
    private ProcessingStep processingStep;
} 