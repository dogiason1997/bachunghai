package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Data
@Entity
@Table(name = "LetterAssign")
public class LetterAssign {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id_LetterAssign")
    private Integer idLetterAssign;

    @Column(name = "Deadline")
    private LocalDateTime deadline;

    @Column(name = "Status", length = 50)
    private String status;

    @Column(name = "PresentStage", length = 100)
    private String presentStage;

    @Column(name = "Remarks", columnDefinition = "TEXT")
    private String remarks;

    @ManyToOne
    @JoinColumn(name = "AssignedById", nullable = false)
    private Users Userassigner;

    @ManyToOne
    @JoinColumn(name = "Id_Letter", nullable = false)
    private Letter letter;

    @ManyToOne
    @JoinColumn(name = "MainProcessorId", nullable = false)
    private Users mainProcessor;

    @OneToMany(mappedBy = "letterAssign", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<LetterAssignCoordinators> coordinators;

}