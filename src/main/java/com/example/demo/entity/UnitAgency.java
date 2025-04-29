package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Data
@Entity
@Table(name = "UnitAgency")
public class UnitAgency {
 
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id_Unit")
    private Integer idUnit;

    @Column(name = "Name_Unit", nullable = false, length = 100)
    private String nameUnit;

    @Column(name = "Unit_Code", nullable = false, length = 50,unique = true)
    private String unitCode;

    @Column(name = "Id_PhongBan", nullable = false)
    private Integer idPhongBan;

    // Relationships
    @ManyToOne
    @JoinColumn(name = "Id_PhongBan", insertable = false, updatable = false)
    private Department department;
} 