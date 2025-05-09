package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Data
@Entity
@Table(name = "ViolationStatus")
public class ViolationStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id_Status")
    private Integer idStatus;

    @Column(name = "Status_Name", length = 50, nullable = false)
    private String statusName;
    
    @Column(name = "Description", length = 200)
    private String description;
    
    @OneToMany(mappedBy = "violationStatus")
    private List<ViolationProcessings> violationProcessings;
} 