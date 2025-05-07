package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "Letter_Department")
public class LetterDepartment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id_Letter_Department")
    private Integer idLetterDepartment;

    @ManyToOne
    @JoinColumn(name = "Id_Letter", nullable = false)
    private Letter letter;

    @ManyToOne
    @JoinColumn(name = "Id_Department_Owner", nullable = false)
    private Department departmentOwner; // Phòng ban chủ trì

    @ManyToOne
    @JoinColumn(name = "Id_Department_Combination", nullable = false)
    private Department departmentCombination; // Phòng ban phối hợp
}
