package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.LetterRelation;

@Repository
public interface LetterRelationRepository extends JpaRepository<LetterRelation, Integer> {
    
    // Tìm tất cả các mối quan hệ của một công văn (cả nguồn và đích)
    List<LetterRelation> findByFromLetter_IdLetterOrToLetter_IdLetter(Integer fromLetterId, Integer toLetterId);
    
    // Tìm tất cả các mối quan hệ mà công văn là nguồn
    List<LetterRelation> findByFromLetter_IdLetter(Integer letterId);
    
    // Tìm tất cả các mối quan hệ mà công văn là đích
    List<LetterRelation> findByToLetter_IdLetter(Integer letterId);
    
    // Tìm tất cả các mối quan hệ theo loại
    List<LetterRelation> findByRelationType(String relationType);
    
    // Kiểm tra xem đã tồn tại mối quan hệ giữa hai công văn chưa
    boolean existsByFromLetter_IdLetterAndToLetter_IdLetter(Integer fromLetterId, Integer toLetterId);
} 