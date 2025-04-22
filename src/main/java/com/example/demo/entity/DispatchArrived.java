package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "DispatchArrived")
public class DispatchArrived {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id_DispatchArrived")
    private Integer idDispatchArrived;

    @Column(name = "DispatchArrived_Code", nullable = false, length = 100, unique = true,columnDefinition = "NVARCHAR(100)")
    private String dispatchArrivedCode;

    @Column(name = "Place_Sending", length = 200,columnDefinition = "NVARCHAR(100)")
    private String placeSending;

    @Column(name = "Signer_DispatchArrived", length = 200,columnDefinition = "NVARCHAR(100)")
    private String signerDispatchArrived;

    @Column(name = "Date_Sent")
    private LocalDateTime dateSent;

    @Column(name = "Day_Arrives")
    private LocalDateTime dayArrives;

    @Column(name = "Abstracts", columnDefinition = "NVARCHAR(MAX)")
    private String abstracts;

    @Column(name = "Status", nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    private DispatchStatus status;

    @Column(name = "Prioritize", nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    private DispatchPriority prioritize;

    @Column(name = "File_DispatchArrived")
    @Lob
    private byte[] fileDispatchArrived;

    @Column(name = "Id_User", nullable = false)
    private Integer idUser;

    // Relationships
    @ManyToOne
    @JoinColumn(name = "Id_User", insertable = false, updatable = false)
    private Users user;

    public enum DispatchStatus {
        Chua_xử_lý, Đang_xử_lý, Đã_xử_lý
    }

    public enum DispatchPriority {
        Cao, Trung_bình, Thấp
    }
} 