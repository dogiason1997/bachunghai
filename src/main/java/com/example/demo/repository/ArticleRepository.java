package com.example.demo.repository;

import com.example.demo.entity.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Integer> {
    Page<Article> findAllByIdUser(Integer idUser, Pageable pageable);
    boolean existsByTitle(String title);
} 