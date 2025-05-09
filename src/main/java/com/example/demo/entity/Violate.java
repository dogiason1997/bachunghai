package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;


@Data
@Entity
@Table(name = "Violate")
public class Violate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id_Violate")
    private Integer idViolate;

    @Column(name = "Id_User", nullable = false)
    private Integer idUser;
    
    @Column(name = "Id_Violator")
    private Integer idViolator;
    
    @Column(name = "Id_Level")
    private Integer idLevel;
    
    @Column(name = "Id_Resource")
    private Integer idResource;

    @Column(name = "Locations", length = 200)
    private String locations;


    @Column(name = "Date_Discovery")
    private LocalDate dateDiscovery;


    @Column(name = "Latitude", precision = 9, scale = 6)
    private BigDecimal latitude;

    @Column(name = "Longitude", precision = 9, scale = 6)
    private BigDecimal longitude;

    @Column(name = "Organize", length = 200)
    private String organize;

    @Column(name = "Describe_Violation",length = 100)
    private String describeViolation;    

    // Relationships
    
    @OneToMany(mappedBy = "violate")
    private List<ViolateUserAssignment> userAssignments;
    
    @ManyToOne
    @JoinColumn(name = "Id_Violator", insertable = false, updatable = false)
    private Violator violator;
    
    @ManyToOne
    @JoinColumn(name = "Id_Level", insertable = false, updatable = false)
    private ViolationLevel violationLevel;
    
    @ManyToOne
    @JoinColumn(name = "Id_Resource", insertable = false, updatable = false)
    private WaterResource waterResource;
    
    @OneToMany(mappedBy = "violate")
    private List<ViolateStepDetail> violateStepDetails;
    
    @OneToMany(mappedBy = "violate")
    private List<ViolateTypeMapping> violateTypeMappings;
    
    @OneToMany(mappedBy = "violate")
    private List<ViolationProcessings> violationProcessings;

    @OneToMany(mappedBy = "violate")
    private List<FilesSave> filesSaves;

    @ManyToOne
    @JoinColumn(name = "Id_User", insertable = false, updatable = false)
    private Users user;
} 