package com.example.demo.dto;

import com.example.demo.entity.Document;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class DocumentResponseDTO {
    private Integer idDocument;
    private Integer idUser;
    private String documentNumber;
    private String abstractText;
    private String content;
    private String signer;
    private LocalDate dateOfIssue;
    private LocalDate effectiveDate;
    
    // Danh sách tên file (không bao gồm dữ liệu file)
    private List<String> fileNames;
    
    // Thông tin liên quan
    private String categoryName;
    private String documentTypeName;
    private String documentFieldName;
    private String issuingAgencyName;
    
    // Constructor để chuyển đổi từ Document sang DTO
    public static DocumentResponseDTO fromDocument(Document document) {
        DocumentResponseDTO dto = new DocumentResponseDTO();
        dto.setIdDocument(document.getIdDocument());
        dto.setIdUser(document.getIdUser());
        dto.setDocumentNumber(document.getDocumentNumber());
        dto.setAbstractText(document.getAbstractText());
        dto.setContent(document.getContent());
        dto.setSigner(document.getSigner());
        dto.setDateOfIssue(document.getDateOfIssue());
        dto.setEffectiveDate(document.getEffectiveDate());
        
        // Chỉ lấy tên file, không lấy dữ liệu file
        if (document.getFilesSave() != null) {
            dto.setFileNames(document.getFilesSave().stream()
                    .map(file -> file.getFileName())
                    .collect(Collectors.toList()));
        }
        
        // Lấy thông tin từ các đối tượng liên quan
        if (document.getCategory() != null) {
            dto.setCategoryName(document.getCategory().getName());
        }
        
        if (document.getDocumentType() != null) {
            dto.setDocumentTypeName(document.getDocumentType().getNameDocumentType());
        }
        
        if (document.getDocumentField() != null) {
            dto.setDocumentFieldName(document.getDocumentField().getNameDocumentField());
        }
        
        if (document.getIssuingAgency() != null) {
            dto.setIssuingAgencyName(document.getIssuingAgency().getNameIssuingAgency());
        }
        
        return dto;
    }
} 