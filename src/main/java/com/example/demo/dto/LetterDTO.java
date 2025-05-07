package com.example.demo.dto;

import lombok.Data;
import java.time.LocalDate;
import org.springframework.web.multipart.MultipartFile;

@Data
public class LetterDTO {
    private String letterCode;
    private String letterTitle;
    private String placeSending;
    private String signerLetter;
    private String infomartionUserLetter;
    private LocalDate dateIssue;
    private LocalDate daySigner;
    private LocalDate dateEffective;
    private String abstracts;
    private String deadline;
    private String prioritize;
    private String letterType;
    private String letterSercurity;
    private String categoryName;
    private MultipartFile[] files;
} 