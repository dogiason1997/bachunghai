package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Data
@Entity
@Table(name = "Department")
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id_PhongBan")
    private Integer idPhongBan;

    @Column(name = "Name_Department", nullable = false, length = 100,columnDefinition = "NVARCHAR(100)")
    private String nameDepartment;

    @Column(name = "Department_Code", nullable = false, length = 50,columnDefinition = "NVARCHAR(50)",unique = true)
    private String departmentCode;

    @Column(name = "Describe", columnDefinition = "NVARCHAR(MAX)")
    private String describe;

    // Relationships
    @OneToMany(mappedBy = "department")
    private List<UnitAgency> unitAgencies;

    @OneToMany(mappedBy = "department")
    private List<Users> users;

} 