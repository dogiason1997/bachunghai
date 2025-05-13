package com.example.demo.dto;

import com.example.demo.entity.Article.ArticleStatus;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ArticleDTO {
    
    @NotBlank(message = "Title is required")
    @Size(max = 200, message = "Title must be less than 200 characters")
    private String title;
    
    @NotBlank(message = "Content is required")
    private String content;
    
    @NotBlank(message = "Category name is required")
    private String categoryName;
    
    @NotNull(message = "Status is required")
    private ArticleStatus status;
    
    private MultipartFile[] files;
} 