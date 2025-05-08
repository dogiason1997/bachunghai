package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

import java.time.LocalTime;
import java.util.List;

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
    private LocalDate dateWork;

    @Column(name = "Hour_Start", nullable = false)
    private LocalTime hourStart;

    @Column(name = "Hour_End", nullable = false)
    private LocalTime hourEnd;

    @Column(name = "Content_Work", columnDefinition = "TEXT")
    private String contentWork;

    @Column(name = "Locations", length = 200)
    private String locations;

    // Relationships
    @ManyToOne
    @JoinColumn(name = "Id_User", insertable = false, updatable = false)
    private Users user;

    @OneToMany(mappedBy = "workSchedule", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Participant> participants;
} 