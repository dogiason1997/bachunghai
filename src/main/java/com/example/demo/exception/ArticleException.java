package com.example.demo.exception;

public class ArticleException extends RuntimeException {
    public ArticleException(String message) {
        super(message);
    }
}

class ArticleNotFoundException extends ArticleException {
    public ArticleNotFoundException(Integer id) {
        super("Không tìm thấy bài viết với id: " + id);
    }
}

class DuplicateArticleTitleException extends ArticleException {
    public DuplicateArticleTitleException(String title) {
        super("Bài viết với tiêu đề '" + title + "' đã có");
    }
}

class CategoryNotFoundException extends ArticleException {
    public CategoryNotFoundException(String name) {
        super("Danh mục với tên '" + name + "' không tìm thấy");
    }
} 