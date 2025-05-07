package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "LetterAssignCoordinators")
public class LetterAssignCoordinators {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id_Coordinator")
    private Integer idCoordinator;

    @ManyToOne
    @JoinColumn(name = "Id_LetterAssign", nullable = false)
    private LetterAssign letterAssign;

    @ManyToOne
    @JoinColumn(name = "Id_User", nullable = false)
    private Users coordinator;
}