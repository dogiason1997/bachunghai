package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "MonitoringData")
public class MonitoringData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id_MonitoringData")
    private Integer idMonitoringData;

    @Column(name = "Id_User", nullable = false)
    private Integer idUser;

    @Column(name = "Station", nullable = false, length = 200,columnDefinition = "NVARCHAR(100)")
    private String station;

    @Column(name = "Day_Data", nullable = false)
    private Integer dayData;

    @Column(name = "Month_Data", nullable = false)
    private Integer monthData;

    @Column(name = "Year_Data", nullable = false)
    private Integer yearData;

    @Column(name = "Hour_Data", nullable = false)
    private Integer hourData;

    @Column(name = "TL", precision = 10, scale = 3)
    private BigDecimal tl;

    @Column(name = "HL", precision = 10, scale = 3)
    private BigDecimal hl;

    @Column(name = "Salinity", precision = 10, scale = 3)
    private BigDecimal salinity;

    @Column(name = "Afternoon_Peak", precision = 10, scale = 3)
    private BigDecimal afternoonPeak;

    @Column(name = "Afternoon_Feet", precision = 10, scale = 3)
    private BigDecimal afternoonFeet;

    @Column(name = "Rainfall", precision = 10, scale = 3)
    private BigDecimal rainfall;

    // Relationships
    @ManyToOne
    @JoinColumn(name = "Id_User", insertable = false, updatable = false)
    private Users user;
} 