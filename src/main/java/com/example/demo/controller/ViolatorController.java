package com.example.demo.controller;

import com.example.demo.dto.ViolatorDTO;
import com.example.demo.entity.Violator;
import com.example.demo.service.ViolatorService;
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
@RequestMapping("/violators")
public class ViolatorController {

    private final ViolatorService violatorService;

    @Autowired
    public ViolatorController(ViolatorService violatorService) {
        this.violatorService = violatorService;
    }

    @PostMapping
    public ResponseEntity<Violator> createViolator(@Valid @RequestBody ViolatorDTO violatorDTO) {
        Violator createdViolator = violatorService.createViolator(violatorDTO);
        return new ResponseEntity<>(createdViolator, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Violator> getViolatorById(@PathVariable Integer id) {
        return violatorService.getViolatorById(id)
                .map(violator -> new ResponseEntity<>(violator, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllViolators(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "name") String sortBy,
            @RequestParam(defaultValue = "asc") String direction) {
        
        Sort.Direction sortDirection = direction.equalsIgnoreCase("desc") ? 
                Sort.Direction.DESC : Sort.Direction.ASC;
        
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, sortBy));
        Page<Violator> violatorPage = violatorService.getAllViolators(pageable);
        
        return createPageResponse(violatorPage);
    }

    @GetMapping("/search")
    public ResponseEntity<Map<String, Object>> searchViolators(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String organizationType,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "name") String sortBy,
            @RequestParam(defaultValue = "asc") String direction) {
        
        Sort.Direction sortDirection = direction.equalsIgnoreCase("desc") ? 
                Sort.Direction.DESC : Sort.Direction.ASC;
        
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, sortBy));
        Page<Violator> violatorPage = violatorService.searchViolators(name, organizationType, pageable);
        
        return createPageResponse(violatorPage);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Violator> updateViolator(
            @PathVariable Integer id, 
            @Valid @RequestBody ViolatorDTO violatorDTO) {
        Violator updatedViolator = violatorService.updateViolator(id, violatorDTO);
        return new ResponseEntity<>(updatedViolator, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteViolator(@PathVariable Integer id) {
        violatorService.deleteViolator(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    
    private ResponseEntity<Map<String, Object>> createPageResponse(Page<Violator> page) {
        Map<String, Object> response = new HashMap<>();
        response.put("content", page.getContent());
        response.put("currentPage", page.getNumber());
        response.put("totalItems", page.getTotalElements());
        response.put("totalPages", page.getTotalPages());
        
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
} 