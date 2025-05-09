package com.example.demo.controller;

import com.example.demo.dto.ViolateDTO;
import com.example.demo.entity.Violate;
import com.example.demo.service.ViolateService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/violates")
public class ViolateController {

    private final ViolateService violateService;

    @Autowired
    public ViolateController(ViolateService violateService) {
        this.violateService = violateService;
    }

    @PostMapping
    public ResponseEntity<Violate> createViolate(@Valid @RequestBody ViolateDTO violateDTO) {
        // Lấy ID người dùng đăng nhập từ context bảo mật
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Integer idUser = getUserIdFromAuthentication(authentication);
        
        Violate createdViolate = violateService.createViolate(violateDTO, idUser);
        return new ResponseEntity<>(createdViolate, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Violate> getViolateById(@PathVariable Integer id) {
        return violateService.getViolateById(id)
                .map(violate -> new ResponseEntity<>(violate, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllViolates(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "dateDiscovery") String sortBy,
            @RequestParam(defaultValue = "desc") String direction) {
        
        Sort.Direction sortDirection = direction.equalsIgnoreCase("desc") ? 
                Sort.Direction.DESC : Sort.Direction.ASC;
        
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, sortBy));
        Page<Violate> violatePage = violateService.getAllViolates(pageable);
        
        return createPageResponse(violatePage);
    }

    @GetMapping("/search")
    public ResponseEntity<Map<String, Object>> searchViolates(
            @RequestParam(required = false) String locations,
            @RequestParam(required = false) Integer idViolator,
            @RequestParam(required = false) Integer idLevel,
            @RequestParam(required = false) Integer idResource,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "dateDiscovery") String sortBy,
            @RequestParam(defaultValue = "desc") String direction) {
        
        Sort.Direction sortDirection = direction.equalsIgnoreCase("desc") ? 
                Sort.Direction.DESC : Sort.Direction.ASC;
        
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, sortBy));
        Page<Violate> violatePage = violateService.searchViolates(
                locations, idViolator, idLevel, idResource, startDate, endDate, pageable);
        
        return createPageResponse(violatePage);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Violate> updateViolate(
            @PathVariable Integer id, 
            @Valid @RequestBody ViolateDTO violateDTO) {
        Violate updatedViolate = violateService.updateViolate(id, violateDTO);
        return new ResponseEntity<>(updatedViolate, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteViolate(@PathVariable Integer id) {
        violateService.deleteViolate(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    
    private ResponseEntity<Map<String, Object>> createPageResponse(Page<Violate> page) {
        Map<String, Object> response = new HashMap<>();
        response.put("content", page.getContent());
        response.put("currentPage", page.getNumber());
        response.put("totalItems", page.getTotalElements());
        response.put("totalPages", page.getTotalPages());
        
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
    // Helper method để lấy ID người dùng từ Authentication
    private Integer getUserIdFromAuthentication(Authentication authentication) {
        // Trong thực tế, phương thức này sẽ lấy ID người dùng từ đối tượng Authentication
        // Trong ví dụ này, chúng ta trả về một ID cố định
        return 1; // ID người dùng mặc định
    }
} 