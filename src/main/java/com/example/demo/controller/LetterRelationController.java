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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.LetterRelationDTO;
import com.example.demo.service.LetterRelationService;

import jakarta.persistence.EntityNotFoundException;

@RestController
@RequestMapping("/letter-relations")
public class LetterRelationController {

    @Autowired
    private LetterRelationService letterRelationService;
    
    @GetMapping
    public ResponseEntity<List<LetterRelationDTO>> getAllLetterRelations() {
        List<LetterRelationDTO> relations = letterRelationService.getAllLetterRelations();
        return ResponseEntity.ok(relations);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<LetterRelationDTO> getLetterRelationById(@PathVariable Integer id) {
        try {
            LetterRelationDTO relation = letterRelationService.getLetterRelationById(id);
            return ResponseEntity.ok(relation);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping("/letter/{letterId}")
    public ResponseEntity<List<LetterRelationDTO>> getLetterRelationsByLetterId(@PathVariable Integer letterId) {
        try {
            List<LetterRelationDTO> relations = letterRelationService.getLetterRelationsByLetterId(letterId);
            return ResponseEntity.ok(relations);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping("/from/{letterId}")
    public ResponseEntity<List<LetterRelationDTO>> getRelationsFromLetter(@PathVariable Integer letterId) {
        try {
            List<LetterRelationDTO> relations = letterRelationService.getRelationsFromLetter(letterId);
            return ResponseEntity.ok(relations);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping("/to/{letterId}")
    public ResponseEntity<List<LetterRelationDTO>> getRelationsToLetter(@PathVariable Integer letterId) {
        try {
            List<LetterRelationDTO> relations = letterRelationService.getRelationsToLetter(letterId);
            return ResponseEntity.ok(relations);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping("/by-type")
    public ResponseEntity<List<LetterRelationDTO>> getRelationsByType(@RequestParam String relationType) {
        try {
            List<LetterRelationDTO> relations = letterRelationService.getRelationsByType(relationType);
            return ResponseEntity.ok(relations);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @PostMapping
    public ResponseEntity<?> createLetterRelation(@RequestBody LetterRelationDTO letterRelationDTO) {
        try {
            LetterRelationDTO createdRelation = letterRelationService.createLetterRelation(letterRelationDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdRelation);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<?> updateLetterRelation(
            @PathVariable Integer id, 
            @RequestBody LetterRelationDTO letterRelationDTO) {
        try {
            LetterRelationDTO updatedRelation = letterRelationService.updateLetterRelation(id, letterRelationDTO);
            return ResponseEntity.ok(updatedRelation);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteLetterRelation(@PathVariable Integer id) {
        try {
            letterRelationService.deleteLetterRelation(id);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
} 