package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@Entity
@Table(name = "WorkSchedule")
public class WorkSchedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id_WorkSchedule")
    private Integer idWorkSchedule;

    @Column(name = "Id_User", nullable = false)
    private Integer idUser;

    @Column(name = "Date_Work", nullable = false)
    private LocalDateTime dateWork;

    @Column(name = "Hour_Start", nullable = false)
    private LocalTime hourStart;

    @Column(name = "Hour_End", nullable = false)
    private LocalTime hourEnd;

    @Column(name = "Content_Work", columnDefinition = "NVARCHAR(MAX)")
    private String contentWork;

    @Column(name = "Participants", columnDefinition = "NVARCHAR(MAX)")
    private String participants;

    @Column(name = "Locations", length = 200,columnDefinition = "NVARCHAR(100)")
    private String locations;

    // Relationships
    @ManyToOne
    @JoinColumn(name = "Id_User", insertable = false, updatable = false)
    private Users user;
} 