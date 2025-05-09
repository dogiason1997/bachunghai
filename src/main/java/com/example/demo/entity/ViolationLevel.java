package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Data
@Entity
@Table(name = "ViolationLevel")
public class ViolationLevel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id_Level")
    private Integer idLevel;

    @Column(name = "Level_Name", length = 50, nullable = false)
    private String levelName;
    
    @Column(name = "Description", length = 255)
    private String description;
    
    @Column(name = "Penalty_Range", length = 100)
    private String penaltyRange;
    
    @Column(name = "Severity_Score")
    private Integer severityScore;
    
    // Relationship
    @OneToMany(mappedBy = "violationLevel")
    @JsonIgnore
    private List<Violate> violates;
} 