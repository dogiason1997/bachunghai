package com.example.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import com.example.demo.entity.Article.ArticleStatus;
import com.example.demo.entity.Article.ArticleStatusConverter;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    
    @Override
    public void addFormatters(FormatterRegistry registry) {
        ArticleStatusConverter converter = new ArticleStatusConverter();
        registry.addConverter(String.class, ArticleStatus.class, source -> converter.convertToEntityAttribute(source));
    }
} 