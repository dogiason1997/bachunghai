package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Data
@Entity
@Table(name = "IssuingAgency")
public class IssuingAgency {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_issuingAgency;

    @Column(name = "name", nullable = false, length = 100, unique = true)
    private String nameIssuingAgency;

    @Column(name = "description", length = 255)
    private String description;

    @OneToMany(mappedBy = "issuingAgency")
    @JsonIgnore
    private List<Document> documents;
}