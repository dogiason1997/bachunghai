package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "Users")
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id_User")
    private Integer idUser;

    @Column(name = "FullName", nullable = false, length = 200, columnDefinition = "NVARCHAR(100)")
    private String fullName;

    @Column(name = "Username", nullable = false, length = 100, unique = true, columnDefinition = "NVARCHAR(100)")
    private String username;

    @Column(name = "Passwords", nullable = false, length = 200)
    private String passwords;

    @Column(name = "Email", nullable = false, length = 200, unique = true, columnDefinition = "NVARCHAR(100)")
    private String email;

    @Column(name = "Roles", nullable = false, length = 20, columnDefinition = "NVARCHAR(100)")
    @Enumerated(EnumType.STRING)
    private UserRole roles;

    @Column(name = "Id_PhongBan", nullable = false)
    private Integer idPhongBan;

    @Column(name = "Statuss", nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    private UserStatus statuss;

    @Column(name = "DateOfBirth")
    private LocalDateTime dateOfBirth;

    // Relationships
    @ManyToOne
    @JoinColumn(name = "Id_PhongBan", insertable = false, updatable = false)
    private Department department;

    @OneToMany(mappedBy = "user")
    private List<Article> articles;

    @OneToMany(mappedBy = "user")
    private List<Notification> notifications;

    @OneToMany(mappedBy = "user")
    private List<Plans> plans;

    @OneToMany(mappedBy = "user")
    private List<Vehicle> vehicles;

    @OneToMany(mappedBy = "user")
    private List<Document> documents;

    @OneToMany(mappedBy = "user")
    private List<ContactFeedback> contactFeedbacks;

    @OneToMany(mappedBy = "user")
    private List<WorkSchedule> workSchedules;

    @OneToMany(mappedBy = "user")
    private List<MonitoringData> monitoringData;

    @OneToMany(mappedBy = "user")
    private List<Violate> violates;

    @OneToMany(mappedBy = "user")
    private List<DispatchArrived> dispatchArrived;

    @OneToMany(mappedBy = "user")
    private List<Dispatch> dispatches;

    @OneToMany(mappedBy = "user")
    private List<Operation> operations;

    public enum UserRole {
        Admin, Guest, Station, Department
    }

    public enum UserStatus {
        active, inactive
    }
} 