package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "LetterProcessing")
public class LetterProcessing {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "StageId", nullable = false)
    private ProcessStage processStage;

    @ManyToOne
    @JoinColumn(name = "Id_Letter", nullable = false)
    private Letter letter;
}