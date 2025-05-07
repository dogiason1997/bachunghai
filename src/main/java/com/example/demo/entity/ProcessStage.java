package com.example.demo.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "ProcessStage")
public class ProcessStage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "StageId")
    private Integer stageId;

    @Column(name = "StageOrder", nullable = false)
    private Integer stageOrder;

    @Column(name = "StageName", nullable = false, length = 100)
    private String stageName;

    @Column(name = "Description", columnDefinition = "TEXT")
    private String description;

    @OneToMany(mappedBy = "processStage")
    @JsonIgnore
    private List<LetterProcessing> letterProcessings;
}