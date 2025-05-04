package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Data
@Entity
@Table(name = "Document")
public class Document {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id_Document")
    private Integer idDocument;

    @Column(name = "Id_User", nullable = false)
    private Integer idUser;

    @Column(name = "Document_Number", nullable = false, length = 100, unique = true)
    private String documentNumber;

    @Column(name = "Abstract", length = 100 )
    private String abstractText;

    @Column(name = "Content", length = 100)
    private String content;

    @Column(name = "Signer", length = 100)
    private String signer;

    @Column(name = "Date_of_Issue")
    private LocalDate dateOfIssue;

    @Column(name = "Effective_Date")
    private LocalDate effectiveDate;

    @OneToMany(mappedBy = "document")
    private List<FilesSave> filesSave;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "Id_User", insertable = false, updatable = false)
    private Users user;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;
    
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "id_documentField")
    private DocumentField documentField;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "id_issuingAgency")
    private IssuingAgency issuingAgency;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "id_documentType")
    private DocumentType documentType;

} 