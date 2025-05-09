package com.example.demo.controller;

import com.example.demo.dto.ViolationLevelDTO;
import com.example.demo.entity.ViolationLevel;
import com.example.demo.service.ViolationLevelService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/violation-levels")
public class ViolationLevelController {

    private final ViolationLevelService violationLevelService;

    @Autowired
    public ViolationLevelController(ViolationLevelService violationLevelService) {
        this.violationLevelService = violationLevelService;
    }

    @PostMapping
    public ResponseEntity<ViolationLevel> createViolationLevel(@Valid @RequestBody ViolationLevelDTO violationLevelDTO) {
        ViolationLevel createdLevel = violationLevelService.createViolationLevel(violationLevelDTO);
        return new ResponseEntity<>(createdLevel, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ViolationLevel> getViolationLevelById(@PathVariable Integer id) {
        return violationLevelService.getViolationLevelById(id)
                .map(level -> new ResponseEntity<>(level, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllViolationLevels(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "severityScore") String sortBy,
            @RequestParam(defaultValue = "asc") String direction) {
        
        Sort.Direction sortDirection = direction.equalsIgnoreCase("desc") ? 
                Sort.Direction.DESC : Sort.Direction.ASC;
        
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, sortBy));
        Page<ViolationLevel> levelPage = violationLevelService.getAllViolationLevels(pageable);
        
        return createPageResponse(levelPage);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ViolationLevel> updateViolationLevel(
            @PathVariable Integer id, 
            @Valid @RequestBody ViolationLevelDTO violationLevelDTO) {
        ViolationLevel updatedLevel = violationLevelService.updateViolationLevel(id, violationLevelDTO);
        return new ResponseEntity<>(updatedLevel, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteViolationLevel(@PathVariable Integer id) {
        violationLevelService.deleteViolationLevel(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    
    private ResponseEntity<Map<String, Object>> createPageResponse(Page<ViolationLevel> page) {
        Map<String, Object> response = new HashMap<>();
        response.put("content", page.getContent());
        response.put("currentPage", page.getNumber());
        response.put("totalItems", page.getTotalElements());
        response.put("totalPages", page.getTotalPages());
        
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
} 