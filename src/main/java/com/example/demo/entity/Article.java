package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "Article")
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id_Article")
    private Integer idArticle;

    @Column(name = "Title", nullable = false, length = 200,unique = true)
    private String title;

    @Column(name = "Category", nullable = false, length = 100)
    @Enumerated(EnumType.STRING)
    private ArticleCategory category;

    @Column(name = "CreationDate", nullable = false)
    private LocalDateTime creationDate;

    @Column(name = "Id_User", nullable = false)
    private Integer idUser;

    @Column(name = "Images")
    @Lob
    private byte[] images;

    @Column(name = "Statuss", nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    private ArticleStatus statuss;

    @Column(name = "Content", columnDefinition = "TEXT")
    private String content;

    // Relationships
    @ManyToOne
    @JoinColumn(name = "Id_User", insertable = false, updatable = false)
    private Users user;

    public enum ArticleCategory {
        Công_ty, ngành, dự_án, sự_kiện
    }

    public enum ArticleStatus {
        Nháp, Đã_xuất_bản
    }
} 