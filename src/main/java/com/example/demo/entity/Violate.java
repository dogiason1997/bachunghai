package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

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

    @Column(name = "Locations", length = 200,columnDefinition = "NVARCHAR(100)")
    private String locations;

    @Column(name = "Type_Violation", length = 100,columnDefinition = "NVARCHAR(100)")
    private String typeViolation;

    @Column(name = "Date_Discovery")
    private LocalDateTime dateDiscovery;

    @Column(name = "Levels", nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    private ViolationLevel levels;

    @Column(name = "Id_Unit", nullable = false)
    private Integer idUnit;

    @Column(name = "Id_CanalRoute", nullable = false)
    private Integer idCanalRoute;

    @Column(name = "Edges", nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    private EdgeType edges;

    @Column(name = "Latitude", precision = 9, scale = 6)
    private BigDecimal latitude;

    @Column(name = "Longitude", precision = 9, scale = 6)
    private BigDecimal longitude;

    @Column(name = "Name_Violation", length = 200,columnDefinition = "NVARCHAR(100)")
    private String nameViolation;

    @Column(name = "Cmnd_Violation", length = 50, unique = true,columnDefinition = "NVARCHAR(100)")
    private String cmndViolation;

    @Column(name = "Tell_Violation", length = 20,columnDefinition = "NVARCHAR(100)")
    private String tellViolation;

    @Column(name = "Organize", length = 200,columnDefinition = "NVARCHAR(100)")
    private String organize;

    @Column(name = "Addresss", length = 300,columnDefinition = "NVARCHAR(100)")
    private String addresss;

    @Column(name = "Describe_Violation", columnDefinition = "NVARCHAR(MAX)")
    private String describeViolation;

    @Column(name = "File_Violation")
    @Lob
    private byte[] fileViolation;

    // Relationships
    @ManyToOne
    @JoinColumn(name = "Id_User", insertable = false, updatable = false)
    private Users user;

    @ManyToOne
    @JoinColumn(name = "Id_CanalRoute", insertable = false, updatable = false)
    private CanalRoute canalRoute;

    public enum ViolationLevel {
        Thấp, Trung_bình, Cao
    }

    public enum EdgeType {
        Bờ_tả, Bờ_hữu, không_xác_định
    }
} 