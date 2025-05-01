package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;

@Data
@Entity
@Table(name = "Users")
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id_User")
    private Integer idUser;

    @Column(name = "FullName", length = 200)
    private String fullName;

    @Column(name = "Username", length = 100, unique = true)
    private String username;

    @Column(name = "Password", length = 200)
    private String password;

    @Column(name = "Email", length = 200, unique = true)
    private String email;

    @Column(name = "Position", length = 100)
    private String position;

    @Column(name = "enabled" )
    private Boolean enabled;

    @Column(name = "Id_PhongBan")
    private Integer idPhongBan;

    @Column(name = "Statuss", length = 20)
    @Enumerated(EnumType.STRING)
    private UserStatus statuss;

    @Column(name = "DateOfBirth")
    private LocalDate dateOfBirth;

    // Relationships
    @ManyToOne
    @JoinColumn(name = "Id_PhongBan", insertable = false, updatable = false)
    private Department department;

///
    @OneToMany(mappedBy = "user")
    private List<Authorities> authorities;

    @OneToMany(mappedBy = "user")
    private List<AuthToken> AuthToken;
    ///



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

    public enum UserStatus {
        active, inactive
    }

}