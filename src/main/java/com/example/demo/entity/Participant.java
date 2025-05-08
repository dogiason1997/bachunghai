package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "Participant")
public class Participant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id_Participant")
    private Integer idParticipant;
    
    @Column(name = "Name", nullable = false, length = 100)
    private String name;
    
    @Column(name = "Email", length = 100)
    private String email;
    
    @Column(name = "Phone", length = 20)
    private String phone;

    @ManyToOne
    @JoinColumn(name = "Id_WorkSchedule", nullable = false)
    private WorkSchedule workSchedule;
} 