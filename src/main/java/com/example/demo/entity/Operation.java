package com.example.demo.entity;

import com.example.demo.entity.Users;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "Operation")
public class Operation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id_Operation")
    private Integer idOperation;

    @Column(name = "Id_Department", nullable = false)
    private Integer idDepartment;

    @Column(name = "Day_Operation", nullable = false)
    private Integer dayOperation;

    @Column(name = "Month_Operation", nullable = false)
    private Integer monthOperation;

    @Column(name = "Year_Operation", nullable = false)
    private Integer yearOperation;

    @Column(name = "Operation", nullable = false, length = 100)
    private String operation;

    @Column(name = "Activity", nullable = false, length = 100)
    private String activity;

    @Column(name = "Station_Operation", length = 200)
    private String stationOperation;

    // Relationships
    @ManyToOne
    @JoinColumn(name = "Id_User", insertable = false, updatable = false)
    private Users user;
} 