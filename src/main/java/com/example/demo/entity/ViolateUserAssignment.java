package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "ViolateUserAssignment")
public class ViolateUserAssignment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id_Assignment")
    private Integer idAssignment;
    
    @Column(name = "Assignment_Date")
    private LocalDateTime assignmentDate;
    
    @Column(name = "Role_Type", length = 50, nullable = false)
    private String roleType;
    
    @Column(name = "Status", length = 50)
    private String status;
    
    @Column(name = "Notes", length = 500)
    private String notes;
    
    // Relationships
    @ManyToOne
    @JoinColumn(name = "Id_Violate", nullable = false)
    private Violate violate;
    
    @ManyToOne
    @JoinColumn(name = "Id_User", nullable = false)
    private Users user;
    
    @ManyToOne
    @JoinColumn(name = "Id_Assigner")
    private Users assigner;
} 