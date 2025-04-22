package com.example.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
    
    @GetMapping("/")
    public String home() {
        return "Xin chào! Spring Boot đang chạy thành công!";
    }
    
    @GetMapping("/test")
    public String test() {
        return "API test đang hoạt động!";
    }
} 