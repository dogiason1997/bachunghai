package com.example.demo.service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import com.example.demo.entity.Category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.dto.ArticleDTO;
import com.example.demo.entity.Article;
import com.example.demo.entity.Article.ArticleStatus;
import com.example.demo.repository.ArticleRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class ArticleService {
    @Autowired
    private ArticleRepository articleRepository;
    
    @Autowired
    private CategoryService categoryService;

    private void validateImage(MultipartFile file) {
        if (file != null && !file.isEmpty()) {
            // Kiểm tra kích thước file (ví dụ: max 5MB)
            if (file.getSize() > 5 * 1024 * 1024) {
                throw new RuntimeException("File size cannot exceed 5MB");
            }
            
            // Kiểm tra định dạng file
            String contentType = file.getContentType();
            if (contentType == null || !contentType.startsWith("image/")) {
                throw new RuntimeException("Tệp phải là hình ảnh");
            }
        }
    }

    // Tạo bài viết mới
    public Article createArticle(ArticleDTO articleDTO, Integer userId) throws IOException {
        if (articleRepository.existsByTitle(articleDTO.getTitle())) {
            throw new RuntimeException("Tiêu đề bị trùng ,đã tồn tại");
        }

        // Validate và xử lý ảnh
        MultipartFile imageFile = articleDTO.getImage();
        byte[] imageBytes = null;
        
        if (imageFile != null && !imageFile.isEmpty()) {
            validateImage(imageFile);
            imageBytes = imageFile.getBytes();
        }

        Article article = new Article();
        article.setTitle(articleDTO.getTitle());
        article.setContent(articleDTO.getContent());
        article.setStatus(articleDTO.getStatus());
        article.setImages(imageBytes);
        article.setCreationDate(LocalDateTime.now());
        article.setIdUser(userId);
        
        // Set category by name
        Category category = categoryService.getCategoryByName(articleDTO.getCategoryName());
        article.setCategory(category);

        return articleRepository.save(article);
    }

    // Lấy tất cả bài viết
    public List<Article> getAllArticles() {
        List<Article> articles = articleRepository.findAll();
        System.out.println("Number of articles found: " + articles.size());
        return articles;
    }

    // Lấy bài viết theo ID
    public Article getArticleById(Integer id) {
        return articleRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Article not found"));
    }

    // Lấy bài viết theo trạng thái
    public List<Article> getArticlesByStatus(ArticleStatus status) {
        return articleRepository.findByStatus(status);
    }

    // Lấy bài viết theo category name
    public List<Article> getArticlesByCategory(String categoryName) {
        Category category = categoryService.getCategoryByName(categoryName);
        return articleRepository.findByCategory(category);
    }

    // Cập nhật bài viết
    public Article updateArticle(Integer id, ArticleDTO articleDTO) throws IOException {
        Article article = getArticleById(id);
        
        article.setTitle(articleDTO.getTitle());
        article.setContent(articleDTO.getContent());
        article.setStatus(articleDTO.getStatus());
        
        // Handle image update
        MultipartFile imageFile = articleDTO.getImage();
        if (imageFile != null && !imageFile.isEmpty()) {
            validateImage(imageFile);
            article.setImages(imageFile.getBytes());
        }
        
        // Update category if changed
        Category newCategory = categoryService.getCategoryByName(articleDTO.getCategoryName());
        if (!article.getCategory().equals(newCategory)) {
            article.setCategory(newCategory);
        }

        return articleRepository.save(article);
    }

    // Xóa bài viết
    public void deleteArticle(Integer id) {
        Article article = getArticleById(id);
        articleRepository.delete(article);
    }
}