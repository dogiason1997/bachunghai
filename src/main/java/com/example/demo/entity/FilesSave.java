package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Data
@Entity
@Table(name = "FilesSave")
public class FilesSave {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id_File")
    private Integer id;

    @Column(name = "FileName", nullable = false, length = 255)
    private String fileName;

    @Lob
    @Column(name = "Data", nullable = false)
    @JsonIgnore
    private byte[] data;

    @Column(name = "UploadDate", nullable = false)
    private LocalDateTime uploadDate;

    @ManyToOne
    @JoinColumn(name = "Id_Notification")
    @JsonIgnore
    private Notification notification;

    @ManyToOne
    @JoinColumn(name = "Id_Document")
    @JsonIgnore
    private Document document;

    @ManyToOne
    @JoinColumn(name = "Id_Letter")
    @JsonIgnore
    private Letter letter;

    @ManyToOne
    @JoinColumn(name = "Id_Violate")
    @JsonIgnore
    private Violate violate;


    @PrePersist
    protected void onCreate() {
        this.uploadDate = LocalDateTime.now();
    }
} 