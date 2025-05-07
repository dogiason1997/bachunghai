package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.LetterAssignDTO;
import com.example.demo.service.LetterAssignService;

import jakarta.persistence.EntityNotFoundException;

@RestController
@RequestMapping("/letter-assigns")
public class LetterAssignController {

    @Autowired
    private LetterAssignService letterAssignService;
    
    @GetMapping
    public ResponseEntity<List<LetterAssignDTO>> getAllLetterAssigns() {
        List<LetterAssignDTO> letterAssigns = letterAssignService.getAllLetterAssigns();
        return ResponseEntity.ok(letterAssigns);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<LetterAssignDTO> getLetterAssignById(@PathVariable Integer id) {
        try {
            LetterAssignDTO letterAssign = letterAssignService.getLetterAssignById(id);
            return ResponseEntity.ok(letterAssign);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @PostMapping
    public ResponseEntity<LetterAssignDTO> createLetterAssign(@RequestBody LetterAssignDTO letterAssignDTO) {
        try {
            LetterAssignDTO createdLetterAssign = letterAssignService.createLetterAssign(letterAssignDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdLetterAssign);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<LetterAssignDTO> updateLetterAssign(
            @PathVariable Integer id, 
            @RequestBody LetterAssignDTO letterAssignDTO) {
        try {
            LetterAssignDTO updatedLetterAssign = letterAssignService.updateLetterAssign(id, letterAssignDTO);
            return ResponseEntity.ok(updatedLetterAssign);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLetterAssign(@PathVariable Integer id) {
        try {
            letterAssignService.deleteLetterAssign(id);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
} 