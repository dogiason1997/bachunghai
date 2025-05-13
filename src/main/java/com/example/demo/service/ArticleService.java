package com.example.demo.service;

import com.example.demo.dto.ArticleDTO;
import com.example.demo.entity.Article;
import com.example.demo.entity.Category;
import com.example.demo.entity.FilesSave;
import com.example.demo.exception.ArticleException;
import com.example.demo.repository.ArticleRepository;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.FilesSaveRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ArticleService {

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private FilesSaveRepository filesSaveRepository;

    public Page<Article> getAllArticles(Pageable pageable) {
        return articleRepository.findAll(pageable);
    }

    public Page<Article> getArticlesByUser(Integer userId, Pageable pageable) {
        return articleRepository.findAllByIdUser(userId, pageable);
    }

    public Article getArticleById(Integer id) {
        return articleRepository.findById(id)
                .orElseThrow(() -> new ArticleException("Article not found with id: " + id));
    }

    @Transactional
    public Article createArticle(ArticleDTO articleDTO, Integer userId) {
        // Check for duplicate title
        if (articleRepository.existsByTitle(articleDTO.getTitle())) {
            throw new ArticleException("Article with title '" + articleDTO.getTitle() + "' already exists");
        }

        // Find category by name
        Optional<Category> categoryOpt = categoryRepository.findByName(articleDTO.getCategoryName());
        if (categoryOpt.isEmpty()) {
            throw new ArticleException("Category with name '" + articleDTO.getCategoryName() + "' not found");
        }
        Category category = categoryOpt.get();

        // Create new article
        Article article = new Article();
        article.setTitle(articleDTO.getTitle());
        article.setContent(articleDTO.getContent());
        article.setStatus(articleDTO.getStatus());
        article.setCategory(category);
        article.setIdUser(userId);
        article.setCreationDate(LocalDateTime.now());

        // Save article to get ID
        Article savedArticle = articleRepository.save(article);

        // Process files if any
        if (articleDTO.getFiles() != null && articleDTO.getFiles().length > 0) {
            List<FilesSave> files = new ArrayList<>();
            try {
                for (MultipartFile file : articleDTO.getFiles()) {
                    if (!file.isEmpty()) {
                        FilesSave filesSave = new FilesSave();
                        filesSave.setFileName(file.getOriginalFilename());
                        filesSave.setData(file.getBytes());
                        filesSave.setArticle(savedArticle);
                        files.add(filesSaveRepository.save(filesSave));
                    }
                }
            } catch (IOException e) {
                throw new ArticleException("Failed to process uploaded files: " + e.getMessage());
            }
        }

        return savedArticle;
    }

    @Transactional
    public Article updateArticle(Integer id, ArticleDTO articleDTO, Integer userId) {
        // Find existing article
        Article existingArticle = articleRepository.findById(id)
                .orElseThrow(() -> new ArticleException("Article not found with id: " + id));

        // Check if the user has permission to update (article belongs to user)
        if (!existingArticle.getIdUser().equals(userId)) {
            throw new ArticleException("You do not have permission to update this article");
        }

        // Check if new title exists and it's not the same article
        if (!existingArticle.getTitle().equals(articleDTO.getTitle()) && 
            articleRepository.existsByTitle(articleDTO.getTitle())) {
            throw new ArticleException("Article with title '" + articleDTO.getTitle() + "' already exists");
        }

        // Find category by name
        Optional<Category> categoryOpt = categoryRepository.findByName(articleDTO.getCategoryName());
        if (categoryOpt.isEmpty()) {
            throw new ArticleException("Category with name '" + articleDTO.getCategoryName() + "' not found");
        }
        Category category = categoryOpt.get();

        // Update article properties
        existingArticle.setTitle(articleDTO.getTitle());
        existingArticle.setContent(articleDTO.getContent());
        existingArticle.setStatus(articleDTO.getStatus());
        existingArticle.setCategory(category);

        // Remove existing files if there are new files
        if (articleDTO.getFiles() != null && articleDTO.getFiles().length > 0) {
            if (existingArticle.getFiles() != null && !existingArticle.getFiles().isEmpty()) {
                filesSaveRepository.deleteAll(existingArticle.getFiles());
            }

            // Process new files
            List<FilesSave> files = new ArrayList<>();
            try {
                for (MultipartFile file : articleDTO.getFiles()) {
                    if (!file.isEmpty()) {
                        FilesSave filesSave = new FilesSave();
                        filesSave.setFileName(file.getOriginalFilename());
                        filesSave.setData(file.getBytes());
                        filesSave.setArticle(existingArticle);
                        files.add(filesSaveRepository.save(filesSave));
                    }
                }
            } catch (IOException e) {
                throw new ArticleException("Failed to process uploaded files: " + e.getMessage());
            }
        }

        return articleRepository.save(existingArticle);
    }

    @Transactional
    public void deleteArticle(Integer id, Integer userId) {
        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new ArticleException("Article not found with id: " + id));

        // Check if the user has permission to delete (article belongs to user)
        if (!article.getIdUser().equals(userId)) {
            throw new ArticleException("You do not have permission to delete this article");
        }

        // Delete all associated files
        if (article.getFiles() != null && !article.getFiles().isEmpty()) {
            filesSaveRepository.deleteAll(article.getFiles());
        }

        // Delete the article
        articleRepository.delete(article);
    }
} 