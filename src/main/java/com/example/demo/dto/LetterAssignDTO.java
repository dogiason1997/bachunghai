package com.example.demo.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class LetterAssignDTO {
    private Integer idLetterAssign;
    private LocalDateTime deadline;
    private String status;
    private String presentStage;
    private String remarks;
    private String assignerName;
    private Integer assignerId;
    private String letterTitle;
    private Integer letterId;
    private String processorName;
    private Integer processorId;
} 