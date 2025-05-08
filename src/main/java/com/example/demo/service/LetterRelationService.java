package com.example.demo.service;

import java.util.List;

import com.example.demo.dto.LetterRelationDTO;

public interface LetterRelationService {
    
    // Lấy tất cả quan hệ công văn
    List<LetterRelationDTO> getAllLetterRelations();
    
    // Lấy một quan hệ công văn theo ID
    LetterRelationDTO getLetterRelationById(Integer id);
    
    // Lấy tất cả quan hệ của một công văn (cả nguồn và đích)
    List<LetterRelationDTO> getLetterRelationsByLetterId(Integer letterId);
    
    // Lấy tất cả quan hệ mà công văn là nguồn
    List<LetterRelationDTO> getRelationsFromLetter(Integer fromLetterId);
    
    // Lấy tất cả quan hệ mà công văn là đích
    List<LetterRelationDTO> getRelationsToLetter(Integer toLetterId);
    
    // Lấy tất cả quan hệ theo loại
    List<LetterRelationDTO> getRelationsByType(String relationType);
    
    // Tạo mới một quan hệ công văn
    LetterRelationDTO createLetterRelation(LetterRelationDTO letterRelationDTO);
    
    // Cập nhật một quan hệ công văn
    LetterRelationDTO updateLetterRelation(Integer id, LetterRelationDTO letterRelationDTO);
    
    // Xóa một quan hệ công văn
    void deleteLetterRelation(Integer id);
} 