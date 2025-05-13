package com.example.demo.controller;

import com.example.demo.dto.ArticleDTO;
import com.example.demo.entity.Article;
import com.example.demo.exception.ArticleException;
import com.example.demo.service.ArticleService;
import com.example.demo.entity.Users;
import com.example.demo.repository.UserRepository;
// import com.example.demo.repository.UsersRepository;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/articles")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private UserRepository usersRepository;

    // Get all articles with pagination
    @GetMapping
    public ResponseEntity<Page<Article>> getAllArticles(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "creationDate") String sortBy,
            @RequestParam(defaultValue = "desc") String direction) {
        
        Sort.Direction sortDirection = direction.equalsIgnoreCase("asc") ? 
            Sort.Direction.ASC : Sort.Direction.DESC;
        
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, sortBy));
        Page<Article> articles = articleService.getAllArticles(pageable);
        
        return ResponseEntity.ok(articles);
    }

    // Get articles by current user
    @GetMapping("/my-articles")
    public ResponseEntity<Page<Article>> getMyArticles(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "creationDate") String sortBy,
            @RequestParam(defaultValue = "desc") String direction) {
        
        // Get current user ID from security context
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Integer userId = getUserIdFromAuthentication(authentication);
        
        Sort.Direction sortDirection = direction.equalsIgnoreCase("asc") ? 
            Sort.Direction.ASC : Sort.Direction.DESC;
        
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, sortBy));
        Page<Article> articles = articleService.getArticlesByUser(userId, pageable);
        
        return ResponseEntity.ok(articles);
    }

    // Get article by ID
    @GetMapping("/{id}")
    public ResponseEntity<Article> getArticleById(@PathVariable Integer id) {
        try {
            Article article = articleService.getArticleById(id);
            return ResponseEntity.ok(article);
        } catch (ArticleException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Create new article
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> createArticle(@Valid @ModelAttribute ArticleDTO articleDTO) {
        try {
            // Get current user ID from security context
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            Integer userId = getUserIdFromAuthentication(authentication);
            
            Article createdArticle = articleService.createArticle(articleDTO, userId);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdArticle);
        } catch (ArticleException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    // Update article
    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> updateArticle(
            @PathVariable Integer id,
            @Valid @ModelAttribute ArticleDTO articleDTO) {
        try {
            // Get current user ID from security context
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            Integer userId = getUserIdFromAuthentication(authentication);
            
            Article updatedArticle = articleService.updateArticle(id, articleDTO, userId);
            return ResponseEntity.ok(updatedArticle);
        } catch (ArticleException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }

    // Delete article
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteArticle(@PathVariable Integer id) {
        try {
            // Get current user ID from security context
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            Integer userId = getUserIdFromAuthentication(authentication);
            
            articleService.deleteArticle(id, userId);
            return ResponseEntity.noContent().build();
        } catch (ArticleException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }

    // Helper method to get user ID from authentication
    private Integer getUserIdFromAuthentication(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new ArticleException("User not authenticated");
        }
        
        // Lấy userDetails từ principal
        Object principal = authentication.getPrincipal();
        if (principal instanceof org.springframework.security.core.userdetails.UserDetails) {
            // Lấy username từ UserDetails
            String username = ((org.springframework.security.core.userdetails.UserDetails) principal).getUsername();
            
            // Sử dụng UsersRepository để tìm user từ username
            Users user = usersRepository.findByUsername(username);
            if (user == null) {
                throw new ArticleException("User not found with username: " + username);
            }
            
            return user.getIdUser();
        }
        
        throw new ArticleException("Could not determine user ID");
    }

    // Exception handler for validation errors
    @ExceptionHandler(jakarta.validation.ConstraintViolationException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(
            jakarta.validation.ConstraintViolationException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getConstraintViolations().forEach(violation -> {
            String fieldName = violation.getPropertyPath().toString();
            String errorMessage = violation.getMessage();
            errors.put(fieldName, errorMessage);
        });
        return ResponseEntity.badRequest().body(errors);
    }
} 