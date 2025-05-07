package com.example.demo.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class LetterSearchDTO {
    private String letterCode;
    private String letterTitle;
    private String placeSending;
    private String signerLetter;
    private String infomartionUserLetter;
    private LocalDate dateIssueFrom;
    private LocalDate dateIssueTo;
    private LocalDate dateEffectiveFrom;
    private LocalDate dateEffectiveTo;
    private String daySigner;
    private String abstracts;
    private String deadline;
    private String prioritize;
    private String letterType;
    private String letterSercurity;
    private String categoryName;
}