package com.example.demo.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

@Data
public class DocumentDTO {
    private String documentNumber;
    private String abstractText;
    private String content;
    private String signer;
    private LocalDate dateOfIssue;
    private LocalDate effectiveDate;

    private String nameDocumentType;
    private String nameDocumentField;
    private String nameIssuingAgency;
    private String categoryName;

    private List<MultipartFile> files; // Dùng cho upload nhiều file
} 