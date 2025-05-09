package com.example.demo.repository;

import com.example.demo.entity.ViolationLevel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ViolationLevelRepository extends JpaRepository<ViolationLevel, Integer> {
    Page<ViolationLevel> findAll(Pageable pageable);
    
    Optional<ViolationLevel> findByLevelName(String levelName);
    
    boolean existsByLevelName(String levelName);
    
    boolean existsByLevelNameAndIdLevelNot(String levelName, Integer idLevel);
} 