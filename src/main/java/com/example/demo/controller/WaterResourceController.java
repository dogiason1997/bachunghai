package com.example.demo.controller;

import com.example.demo.dto.WaterResourceDTO;
import com.example.demo.entity.WaterResource;
import com.example.demo.exception.ResourceAlreadyExistsException;
import com.example.demo.service.WaterResourceService;
import jakarta.persistence.EntityNotFoundException;
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
@RequestMapping("/api/water-resources")
public class WaterResourceController {

    private final WaterResourceService waterResourceService;

    @Autowired
    public WaterResourceController(WaterResourceService waterResourceService) {
        this.waterResourceService = waterResourceService;
    }

    @PostMapping
    public ResponseEntity<WaterResource> createWaterResource(@Valid @RequestBody WaterResourceDTO waterResourceDTO) {
        WaterResource createdResource = waterResourceService.createWaterResource(waterResourceDTO);
        return new ResponseEntity<>(createdResource, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<WaterResource> getWaterResourceById(@PathVariable Integer id) {
        return waterResourceService.getWaterResourceById(id)
                .map(resource -> new ResponseEntity<>(resource, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllWaterResources(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "resourceName") String sortBy,
            @RequestParam(defaultValue = "asc") String direction) {
        
        Sort.Direction sortDirection = direction.equalsIgnoreCase("desc") ? 
                Sort.Direction.DESC : Sort.Direction.ASC;
        
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, sortBy));
        Page<WaterResource> resourcePage = waterResourceService.getAllWaterResources(pageable);
        
        return createPageResponse(resourcePage);
    }

    @GetMapping("/search")
    public ResponseEntity<Map<String, Object>> searchWaterResources(
            @RequestParam(required = false) String resourceName,
            @RequestParam(required = false) String resourceType,
            @RequestParam(required = false) String departmentName,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "resourceName") String sortBy,
            @RequestParam(defaultValue = "asc") String direction) {
        
        Sort.Direction sortDirection = direction.equalsIgnoreCase("desc") ? 
                Sort.Direction.DESC : Sort.Direction.ASC;
        
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, sortBy));
        Page<WaterResource> resourcePage = waterResourceService.searchWaterResources(
                resourceName, resourceType, departmentName, pageable);
        
        return createPageResponse(resourcePage);
    }

    @PutMapping("/{id}")
    public ResponseEntity<WaterResource> updateWaterResource(
            @PathVariable Integer id, 
            @Valid @RequestBody WaterResourceDTO waterResourceDTO) {
        WaterResource updatedResource = waterResourceService.updateWaterResource(id, waterResourceDTO);
        return new ResponseEntity<>(updatedResource, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWaterResource(@PathVariable Integer id) {
        waterResourceService.deleteWaterResource(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    
    private ResponseEntity<Map<String, Object>> createPageResponse(Page<WaterResource> page) {
        Map<String, Object> response = new HashMap<>();
        response.put("content", page.getContent());
        response.put("currentPage", page.getNumber());
        response.put("totalItems", page.getTotalElements());
        response.put("totalPages", page.getTotalPages());
        
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
} 