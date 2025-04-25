package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "Notification")
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id_Notification")
    private Integer idNotification;

    @Column(name = "Title", nullable = false, length = 200, unique = true)
    private String title;

    @Column(name = "Id_User", nullable = false)
    private Integer idUser;

    @Column(name = "Category", nullable = false, length = 300)
    @Enumerated(EnumType.STRING)
    private NotificationCategory category;

    @Column(name = "Content", length = 100)
    private String content;

    @Column(name = "Statuss", nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    private NotificationStatus statuss;

    @Column(name = "CreationDate", nullable = false)
    private LocalDateTime creationDate;

    @Column(name = "Tags", length = 200)
    private String tags;

    @Column(name = "Image")
    @Lob
    private byte[] image;

    // Relationships
    @ManyToOne
    @JoinColumn(name = "Id_User", insertable = false, updatable = false)
    private Users user;

    public enum NotificationCategory {
        Thông_báo_nội_bộ, quy_định, sự_kiện
    }

    public enum NotificationStatus {
        Bản_nháp, Xuất_bản
    }
} 