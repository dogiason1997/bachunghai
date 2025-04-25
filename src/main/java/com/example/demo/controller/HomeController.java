package com.example.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
    
    @GetMapping("/")
    public String home() {
        return "Xin chào Bạn đến với Dự Án Bắc Hưng Hải";
    }
    
    @GetMapping("/test")
    public String test() {
        return "API test đang hoạt động!";
    }
} 