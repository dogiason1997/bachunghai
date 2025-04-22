package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "Plans")
public class Plans {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id_Plan")
    private Integer idPlan;

    @Column(name = "Title", nullable = false, length = 200,columnDefinition = "NVARCHAR(100)")
    private String title;

    @Column(name = "Id_User", nullable = false)
    private Integer idUser;

    @Column(name = "Statuss", nullable = false, length = 20,columnDefinition = "NVARCHAR(100)")
    @Enumerated(EnumType.STRING)
    private PlanStatus statuss;

    @Column(name = "StartDate", nullable = false)
    private LocalDateTime startDate;

    @Column(name = "EndDate", nullable = false)
    private LocalDateTime endDate;

    @Column(name = "Content", columnDefinition = "NVARCHAR(100)")
    private String content;

    @Column(name = "Files")
    @Lob
    private byte[] files;

    @Column(name = "Prioritize", nullable = false, length = 20,columnDefinition = "NVARCHAR(100)")
    @Enumerated(EnumType.STRING)
    private PlanPriority prioritize;

    // Relationships
    @ManyToOne
    @JoinColumn(name = "Id_User", insertable = false, updatable = false)
    private Users user;

    public enum PlanStatus {
        Nháp, Xuất_Bản
    }

    public enum PlanPriority {
        Cao, Thấp, Trung_bình
    }
} 