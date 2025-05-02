package com.example.demo.repository;

import com.example.demo.entity.FilesSave;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FilesSaveRepository extends JpaRepository<FilesSave, Integer> {
}