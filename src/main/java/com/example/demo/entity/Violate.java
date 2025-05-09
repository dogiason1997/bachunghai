package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;


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
    @JsonIgnore
    private List<ViolateUserAssignment> userAssignments;
    
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "Id_Violator", insertable = false, updatable = false)
    private Violator violator;
    
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "Id_Level", insertable = false, updatable = false)
    private ViolationLevel violationLevel;
    
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "Id_Resource", insertable = false, updatable = false)
    private WaterResource waterResource;
    
    @OneToMany(mappedBy = "violate")
    @JsonIgnore
    private List<ViolateStepDetail> violateStepDetails;
    
    @OneToMany(mappedBy = "violate")
    @JsonIgnore
    private List<ViolateTypeMapping> violateTypeMappings;
    
    @OneToMany(mappedBy = "violate")
    @JsonIgnore
    private List<ViolationProcessings> violationProcessings;

    @OneToMany(mappedBy = "violate")
    @JsonIgnore
    private List<FilesSave> filesSaves;

    @ManyToOne
    @JoinColumn(name = "Id_User", insertable = false, updatable = false)
    @JsonIgnore
    private Users user;
} 