package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;


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

    @Column(name = "enabled" )
    private Boolean enabled;

    @Column(name = "Id_PhongBan")
    private Integer idPhongBan;

    @Column(name = "DateOfBirth")
    private LocalDate dateOfBirth;

    @Column(name = "Id_Position")
    private Integer idPosition;

    // Relationships
    @ManyToOne
    @JoinColumn(name = "Id_PhongBan", insertable = false, updatable = false)
    private Department department;

    @ManyToOne
    @JoinColumn(name = "Id_Position", insertable = false, updatable = false)
    private Position position;

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<Authorities> authorities;

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<AuthToken> AuthToken;
    
    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<Article> articles;

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<Notification> notifications;

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<Plans> plans;

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<Vehicle> vehicles;

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<Document> documents;

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<ContactFeedback> contactFeedbacks;

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<WorkSchedule> workSchedules;

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<MonitoringData> monitoringData;

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<Violate> violates;

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<Letter> letters;

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<Operation> operations;

    @OneToMany(mappedBy = "mainProcessor")
    @JsonIgnore
    private List<LetterAssign> assignedLetters;

    @OneToMany(mappedBy = "Userassigner")
    @JsonIgnore
    private List<LetterAssign> assignedLettersByMe;

    // @OneToMany(mappedBy = "assigner")
    // @JsonIgnore
    // private List<LetterAssign> assignedByMe;

    @OneToMany(mappedBy = "coordinator")
    @JsonIgnore
    private List<LetterAssignCoordinators> letterCoordinations;

}