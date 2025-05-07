package com.example.demo.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.dto.ArticleDTO;
import com.example.demo.entity.Article;
import com.example.demo.entity.Article.ArticleStatus;
import com.example.demo.service.ArticleService;

@RestController
@RequestMapping("bhh/articles")
@CrossOrigin("*")
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> createArticle(
            @ModelAttribute ArticleDTO articleDTO,
            @RequestParam(name = "userId", required = true) Integer userId) {
        try {
            Article article = articleService.createArticle(articleDTO, userId);
            return ResponseEntity.ok(article);
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                .body("Error creating article: " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllArticles() {
        try {
            return ResponseEntity.ok(articleService.getAllArticles());
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                .body("Error retrieving articles: " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getArticleById(@PathVariable Integer id) {
        try {
            Article article = articleService.getArticleById(id);
            return ResponseEntity.ok(article);
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                .body("Error retrieving article: " + e.getMessage());
        }
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<?> getArticlesByStatus(
            @PathVariable ArticleStatus status) {
        try {
            return ResponseEntity.ok(articleService.getArticlesByStatus(status));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                .body("Error retrieving articles by status: " + e.getMessage());
        }
    }

    @GetMapping("/category/{categoryName}")
    public ResponseEntity<?> getArticlesByCategory(@PathVariable String categoryName) {
    try {
        List<Article> articles = articleService.getArticlesByCategory(categoryName);
        if (articles == null || articles.isEmpty()) {
            return ResponseEntity.badRequest()
                .body("Không tìm thấy bài viết nào cho danh mục: " + categoryName);
        }
        return ResponseEntity.ok(articles);
    } catch (Exception e) {
        return ResponseEntity.badRequest()
            .body("Error retrieving articles by category: " + e.getMessage());
    }
}

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> updateArticle(
            @PathVariable Integer id,
            @ModelAttribute ArticleDTO articleDTO) {
        try {
            return ResponseEntity.ok(articleService.updateArticle(id, articleDTO));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                .body("Error updating article: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteArticle(@PathVariable Integer id) {
        try {
            articleService.deleteArticle(id);
            return ResponseEntity.ok("Xóa bài viết thành công");
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                .body("Error deleting article: " + e.getMessage());
        }
    }

        // @GetMapping("/{id}/image")
    // public ResponseEntity<?> getArticleImage(@PathVariable Integer id) {
    //     try {
    //         Article article = articleService.getArticleById(id);
    //         if (article.getImages() == null) {
    //             return ResponseEntity.badRequest()
    //                 .body("No image found for article with id: " + id);
    //         }
    //         return ResponseEntity.ok()
    //                 .contentType(MediaType.IMAGE_JPEG)
    //                 .body(article.getImages());
    //     } catch (Exception e) {
    //         return ResponseEntity.badRequest()
    //             .body("Error retrieving image: " + e.getMessage());
    //     }
    // }
}
