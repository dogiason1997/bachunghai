package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Data
@Entity
@Table(name = "DocumentType")
public class DocumentType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_documentType;

    @Column(name = "name", nullable = false, length = 100, unique = true)
    private String nameDocumentType;

    @Column(name = "description", length = 255)
    private String description;

    @OneToMany(mappedBy = "documentType")
    @JsonIgnore
    private List<Document> documents;
}