package com.example.demo.repository;

import com.example.demo.entity.ProcessStage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProcessStageRepository extends JpaRepository<ProcessStage, Integer> {
}