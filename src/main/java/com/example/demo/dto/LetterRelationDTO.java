package com.example.demo.dto;

import lombok.Data;

@Data
public class LetterRelationDTO {
    private Integer relationId;
    private Integer fromLetterId;
    private String fromLetterTitle;
    private Integer toLetterId;
    private String toLetterTitle;
    private String relationType;
    private String notes;
} 