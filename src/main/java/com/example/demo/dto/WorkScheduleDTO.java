package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkScheduleDTO {
    private LocalDate dateWork;
    private LocalTime hourStart;
    private LocalTime hourEnd;
    private String contentWork;
    private String locations;
    private Integer userId;
    
    // Thông tin bổ sung cho response
    // private Integer id;
    // private String creatorName;
} 