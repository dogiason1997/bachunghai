package com.example.demo.controller;

import com.example.demo.dto.LetterDTO;
import com.example.demo.entity.Letter;
import com.example.demo.service.LetterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bhh/letters")
public class LetterController {

    @Autowired
    private LetterService letterService;

    @GetMapping
    public ResponseEntity<Page<Letter>> getAllLetters(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "idLetter") String sortBy,
            @RequestParam(defaultValue = "desc") String direction) {
        
        Sort.Direction sortDirection = direction.equalsIgnoreCase("asc") ? 
                Sort.Direction.ASC : Sort.Direction.DESC;
        
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, sortBy));
        Page<Letter> letters = letterService.getAllLetters(pageable);
        
        return ResponseEntity.ok(letters);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Letter> getLetterById(@PathVariable Integer id) {
        Letter letter = letterService.getLetterById(id);
        return ResponseEntity.ok(letter);
    }

    @PostMapping
    public ResponseEntity<Letter> createLetter(
            @ModelAttribute LetterDTO letterDTO,
            @RequestParam Integer userId) {
        Letter createdLetter = letterService.createLetter(letterDTO, userId);
        return new ResponseEntity<>(createdLetter, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Letter> updateLetter(
            @PathVariable Integer id,
            @ModelAttribute LetterDTO letterDTO) {
        Letter updatedLetter = letterService.updateLetter(id, letterDTO);
        return ResponseEntity.ok(updatedLetter);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLetter(@PathVariable Integer id) {
        letterService.deleteLetter(id);
        return ResponseEntity.noContent().build();
    }
} 