package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

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

    @Column(name = "Text_Type", nullable = false, length = 50,columnDefinition = "NVARCHAR(100)")
    @Enumerated(EnumType.STRING)
    private TextType textType;

    @Column(name = "Text_Form", nullable = false, length = 50,columnDefinition = "NVARCHAR(100)")
    @Enumerated(EnumType.STRING)
    private TextForm textForm;

    @Column(name = "Document_Number", nullable = false, length = 100, unique = true,columnDefinition = "NVARCHAR(100)")
    private String documentNumber;

    @Column(name = "Abstract", columnDefinition = "NVARCHAR(100)")
    private String abstractText;

    @Column(name = "Content", columnDefinition = "NVARCHAR(100)")
    private String content;

    @Column(name = "Field", nullable = false, length = 50,columnDefinition = "NVARCHAR(100)")
    @Enumerated(EnumType.STRING)
    private DocumentField field;

    @Column(name = "Issuing_Agency", nullable = false, length = 100,columnDefinition = "NVARCHAR(100)")
    @Enumerated(EnumType.STRING)
    private IssuingAgency issuingAgency;

    @Column(name = "Signer", length = 100,columnDefinition = "NVARCHAR(100)")
    private String signer;

    @Column(name = "Date_of_Issue")
    private LocalDateTime dateOfIssue;

    @Column(name = "Effective_Date")
    private LocalDateTime effectiveDate;

    @Column(name = "Files")
    @Lob
    private byte[] files;

    // Relationships
    @ManyToOne
    @JoinColumn(name = "Id_User", insertable = false, updatable = false)
    private Users user;

    public enum TextType {
        Văn_bản_điều_hành, Văn_bản_pháp_quy
    }

    public enum TextForm {
        Quyết_định, nghị_định, thông_tư, công_văn, kế_hoạch
    }

    public enum DocumentField {
        Thủ_tục_hành_chính, kế_hoạch, tài_chính, nhân_sự, đầu_tư
    }

    public enum IssuingAgency {
        Bộ_Nông_Nghiệp_và_Môi_Trường, Sở_Nông_Nghiệp_và_Môi_Trường, UBND_Tỉnh
    }
} 