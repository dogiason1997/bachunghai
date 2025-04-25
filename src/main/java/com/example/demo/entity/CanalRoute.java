package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Data
@Entity
@Table(name = "CanalRoute")
public class CanalRoute {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id_CanalRoute")
    private Integer idCanalRoute;

    @Column(name = "Name_CanalRoute", nullable = false, length = 100)
    private String nameCanalRoute;

    @Column(name = "CanalRoute_Code", nullable = false, length = 100)
    private String canalRouteCode;

    @Column(name = "Describe", columnDefinition = "TEXT")
    private String describe;

    // Relationships
    @OneToMany(mappedBy = "canalRoute")
    private List<Violate> violates;
} 