package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Data
@Entity
@Table(name = "ProcessingStep")
public class ProcessingStep {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id_Step")
    private Integer idStep;

    @Column(name = "Step_Name", length = 100, nullable = false)
    private String stepName;
    
    @Column(name = "Description", length = 255)
    private String description;
    
    @Column(name = "Order_Number")
    private Integer orderNumber;
    
    // Relationship
    @OneToMany(mappedBy = "processingStep")
    private List<ViolateStepDetail> violateStepDetails;
} 