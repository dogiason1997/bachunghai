package com.example.demo.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class DocumentSearchDTO {
    private String documentNumber;
    private String abstractText;
    private String content;
    private String signer;
    private LocalDate dateOfIssueFrom;
    private LocalDate dateOfIssueTo;
    private LocalDate effectiveDateFrom;
    private LocalDate effectiveDateTo;
    private String nameDocumentType;
    private String nameDocumentField;
    private String nameIssuingAgency;
    private String categoryName;
} 