package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

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

    @ManyToOne
    // @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id", nullable = false)
    @JsonBackReference
    private Category category;
    
    @Column(name = "CreationDate", nullable = false)
    private LocalDateTime creationDate;

    @Column(name = "Id_User", nullable = false)
    private Integer idUser;

    @Column(name = "Images")
    @Lob
    private byte[] images;

    @Column(name = "Status", nullable = false)
    @Convert(converter = ArticleStatusConverter.class)
    private ArticleStatus status;

    @Column(name = "Content", columnDefinition = "TEXT")
    private String content;

    // Relationships
    @ManyToOne
    @JoinColumn(name = "Id_User", insertable = false, updatable = false)
    @JsonIgnore
    private Users user;

    public enum ArticleStatus {
        NHAP("Nháp"),
        DA_XUAT_BAN("Đã xuất bản");

        private final String value;

        ArticleStatus(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    @Converter
    public static class ArticleStatusConverter implements AttributeConverter<ArticleStatus, String> {
        @Override
        public String convertToDatabaseColumn(ArticleStatus attribute) {
            return attribute == null ? null : attribute.getValue();
        }

        @Override
        public ArticleStatus convertToEntityAttribute(String dbData) {
            if (dbData == null) {
                return null;
            }
            
            for (ArticleStatus status : ArticleStatus.values()) {
                if (status.getValue().equals(dbData)) {
                    return status;
                }
            }
            throw new IllegalArgumentException("Unknown database value: " + dbData);
        }
    }
} 