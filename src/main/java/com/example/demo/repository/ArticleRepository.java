package com.example.demo.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.demo.entity.Article;
import com.example.demo.entity.Category;
import com.example.demo.entity.Article.ArticleStatus;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Integer> {
    List<Article> findByCategory(Category category);
    List<Article> findByStatus(ArticleStatus status);
    boolean existsByTitle(String title);
}
