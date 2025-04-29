
package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;


@Data
@Entity
@Table(name = "Vehicle")
public class Vehicle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id_Vehicle")
    private Integer idVehicle;

    @Column(name = "Id_User", nullable = false)
    private Integer idUser;

    @Column(name = "Title", nullable = false, length = 200)
    private String title;

    @Column(name = "Size", nullable = false)
    private Long size;

    @Column(name = "Typess", nullable = false, length = 100)
    private String typess;

    @Column(name = "CreationDate", nullable = false)
    private LocalDate creationDate;

    @ManyToOne
    @JoinColumn(name = "Id_User", insertable = false, updatable = false)
    private Users user;
} 