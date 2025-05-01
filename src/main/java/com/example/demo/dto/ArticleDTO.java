package com.example.demo.dto;

import org.springframework.web.multipart.MultipartFile;
import com.example.demo.entity.Article.ArticleStatus;
import lombok.Data;

@Data
public class ArticleDTO {
    private String title;           // Tiêu đề
    private String content;         // Nội dung
    private String categoryName;    // Tên category 
    private ArticleStatus status;   // Trạng thái
    private MultipartFile image;    // File ảnh upload
}


