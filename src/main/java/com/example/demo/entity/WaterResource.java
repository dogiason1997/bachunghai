package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Data
@Entity
@Table(name = "WaterResource")
public class WaterResource {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id_Resource")
    private Integer idResource;

    @Column(name = "Resource_Name", length = 200, nullable = false)
    private String resourceName;
    
    @Column(name = "Resource_Type", length = 100)
    private String resourceType;
    
    @Column(name = "Location", length = 500)
    private String location;
    
    @Column(name = "Latitude")
    private BigDecimal latitude;

    @Column(name = "Longitude")
    private BigDecimal longitude;
    
    @ManyToOne
    @JoinColumn(name = "Id_Department")
    @JsonIgnore
    private Department department;
    // Relationship
    @OneToMany(mappedBy = "waterResource")
    @JsonIgnore
    private List<Violate> violates;
} 