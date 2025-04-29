
package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "Dispatch")
public class Dispatch {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id_Dispatch")
    private Integer idDispatch;

    @Column(name = "Dispatch_Code", nullable = false, length = 100, unique = true)
    private String dispatchCode;

    @Column(name = "Place_Sending", length = 100)
    private String placeSending;

    @Column(name = "Signer_Dispatch", length = 200)
    private String signerDispatch;

    @Column(name = "Date_Sent")
    private LocalDateTime dateSent;

    @Column(name = "Day_Arrives")
    private LocalDateTime dayArrives;

    @Column(name = "Abstracts", columnDefinition = "TEXT")
    private String abstracts;

    @Column(name = "Status", nullable = false, length = 100)
    @Enumerated(EnumType.STRING)
    private DispatchStatus status;

    @Column(name = "Prioritize", nullable = false, length = 100)
    @Enumerated(EnumType.STRING)
    private DispatchPriority prioritize;

    @Column(name = "File_Dispatch")
    @Lob
    private byte[] fileDispatch;

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