package com.example.demo.service;

import java.util.List;

import com.example.demo.dto.LetterAssignDTO;

public interface LetterAssignService {
    List<LetterAssignDTO> getAllLetterAssigns();
    LetterAssignDTO getLetterAssignById(Integer id);
    LetterAssignDTO createLetterAssign(LetterAssignDTO letterAssignDTO);
    LetterAssignDTO updateLetterAssign(Integer id, LetterAssignDTO letterAssignDTO);
    void deleteLetterAssign(Integer id);
} 