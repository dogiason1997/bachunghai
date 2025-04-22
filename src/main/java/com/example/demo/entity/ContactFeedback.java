package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "ContactFeedback")
public class ContactFeedback {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id_Contact")
    private Integer idContact;

    @Column(name = "FullName", nullable = false, length = 200,columnDefinition = "NVARCHAR(100)")
    private String fullName;

    @Column(name = "Email", nullable = false, length = 200,columnDefinition = "NVARCHAR(100)")
    private String email;

    @Column(name = "Title", nullable = false, columnDefinition = "NVARCHAR(MAX)")
    private String title;

    @Column(name = "Id_User", nullable = false)
    private Integer idUser;

    // Relationships
    @ManyToOne
    @JoinColumn(name = "Id_User", insertable = false, updatable = false)
    private Users user;
} 