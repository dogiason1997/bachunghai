package com.example.demo.repository;

import com.example.demo.entity.LetterAssign;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LetterAssignRepository extends JpaRepository<LetterAssign, Integer> {
}