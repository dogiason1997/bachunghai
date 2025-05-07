package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "LetterRelation")
public class LetterRelation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RelationId")
    private Integer relationId;

    @ManyToOne
    @JoinColumn(name = "FromLetterId")
    private Letter fromLetter;

    @ManyToOne
    @JoinColumn(name = "ToLetterId")
    private Letter toLetter;

    @Column(name = "RelationType", length = 50)
    private String relationType;

    @Column(name = "CreatedAt", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "Notes", columnDefinition = "TEXT")
    private String notes;
}