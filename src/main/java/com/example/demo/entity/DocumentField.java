package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Data
@Entity
@Table(name = "DocumentField")
public class DocumentField {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_documentField;

    @Column(name = "name", nullable = false, length = 100, unique = true)
    private String nameDocumentField;

    @Column(name = "description", length = 255)
    private String description;

    @OneToMany(mappedBy = "documentField")
    @JsonIgnore
    private List<Document> documents;
}