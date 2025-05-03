package com.example.demo.dto;

import lombok.Data;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

@Data
public class NotificationDTO {
    private String title;
    private String content;
    private String statuss;
    private String categoryName;
    private MultipartFile[] files;
    private List<Integer> departmentIds;
    private List<Integer> positionIds;
} 