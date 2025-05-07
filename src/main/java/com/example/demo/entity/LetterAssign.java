package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "LetterAssign")
public class LetterAssign {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id_LetterAssign")
    private Integer idLetterAssign;

    @Column(name = "AssigneeUserId", nullable = false)
    private Integer assigneeUserId;

    @Column(name = "AssignedBy", nullable = false)
    private Integer assignedBy;

    @Column(name = "Deadline")
    private LocalDateTime deadline;

    @Column(name = "Status", length = 50)
    private String status;

    @Column(name = "PresentStage", length = 100)
    private String presentStage;

    @Column(name = "Remarks", columnDefinition = "TEXT")
    private String remarks;

    @ManyToOne
    @JoinColumn(name = "Id_Letter", nullable = false)
    private Letter letter;

}