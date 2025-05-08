package com.example.demo.controller;

import com.example.demo.dto.WorkScheduleDTO;
import com.example.demo.service.WorkScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/work-schedules")
public class WorkScheduleController {

    private final WorkScheduleService workScheduleService;

    @Autowired
    public WorkScheduleController(WorkScheduleService workScheduleService) {
        this.workScheduleService = workScheduleService;
    }

    // Create a new work schedule
    @PostMapping
    public ResponseEntity<WorkScheduleDTO> createWorkSchedule(@RequestBody WorkScheduleDTO workScheduleDTO) {
        WorkScheduleDTO createdWorkSchedule = workScheduleService.createWorkSchedule(workScheduleDTO);
        return new ResponseEntity<>(createdWorkSchedule, HttpStatus.CREATED);
    }

    // Get all work schedules with pagination
    @GetMapping
    public ResponseEntity<Page<WorkScheduleDTO>> getAllWorkSchedules(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "dateWork") String sortBy,
            @RequestParam(defaultValue = "DESC") String sortDir) {
        
        Sort sort = sortDir.equalsIgnoreCase("DESC") ? 
                    Sort.by(sortBy).descending() : 
                    Sort.by(sortBy).ascending();
        
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<WorkScheduleDTO> workSchedules = workScheduleService.getAllWorkSchedules(pageable);
        return ResponseEntity.ok(workSchedules);
    }

    // Get work schedules by user ID with pagination
    @GetMapping("/by-user/{userId}")
    public ResponseEntity<Page<WorkScheduleDTO>> getWorkSchedulesByUser(
            @PathVariable Integer userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "dateWork") String sortBy,
            @RequestParam(defaultValue = "DESC") String sortDir) {
        
        Sort sort = sortDir.equalsIgnoreCase("DESC") ? 
                    Sort.by(sortBy).descending() : 
                    Sort.by(sortBy).ascending();
        
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<WorkScheduleDTO> workSchedules = workScheduleService.getWorkSchedulesByUserId(userId, pageable);
        return ResponseEntity.ok(workSchedules);
    }

    // Get all work schedules by user ID (no pagination)
    @GetMapping("/list/by-user/{userId}")
    public ResponseEntity<List<WorkScheduleDTO>> getAllWorkSchedulesByUser(
            @PathVariable Integer userId) {
        List<WorkScheduleDTO> workSchedules = workScheduleService.getAllWorkSchedulesByUser(userId);
        return ResponseEntity.ok(workSchedules);
    }

    // Get work schedules by date range with pagination
    @GetMapping("/by-date-range")
    public ResponseEntity<Page<WorkScheduleDTO>> getWorkSchedulesByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "dateWork") String sortBy,
            @RequestParam(defaultValue = "DESC") String sortDir) {
        
        Sort sort = sortDir.equalsIgnoreCase("DESC") ? 
                    Sort.by(sortBy).descending() : 
                    Sort.by(sortBy).ascending();
        
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<WorkScheduleDTO> workSchedules = workScheduleService.getWorkSchedulesByDateRange(startDate, endDate, pageable);
        return ResponseEntity.ok(workSchedules);
    }

    // Get work schedules by user ID and date range with pagination
    @GetMapping("/by-user/{userId}/date-range")
    public ResponseEntity<Page<WorkScheduleDTO>> getWorkSchedulesByUserAndDateRange(
            @PathVariable Integer userId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "dateWork") String sortBy,
            @RequestParam(defaultValue = "DESC") String sortDir) {
        
        Sort sort = sortDir.equalsIgnoreCase("DESC") ? 
                    Sort.by(sortBy).descending() : 
                    Sort.by(sortBy).ascending();
        
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<WorkScheduleDTO> workSchedules = workScheduleService.getWorkSchedulesByUserIdAndDateRange(userId, startDate, endDate, pageable);
        return ResponseEntity.ok(workSchedules);
    }

    // Get work schedule by ID
    @GetMapping("/{id}")
    public ResponseEntity<WorkScheduleDTO> getWorkScheduleById(@PathVariable Integer id) {
        WorkScheduleDTO workSchedule = workScheduleService.getWorkScheduleById(id);
        return ResponseEntity.ok(workSchedule);
    }

    // Update a work schedule
    @PutMapping("/{id}")
    public ResponseEntity<WorkScheduleDTO> updateWorkSchedule(
            @PathVariable Integer id,
            @RequestBody WorkScheduleDTO workScheduleDTO) {
        
        WorkScheduleDTO updatedWorkSchedule = workScheduleService.updateWorkSchedule(id, workScheduleDTO);
        return ResponseEntity.ok(updatedWorkSchedule);
    }

    // Delete a work schedule
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWorkSchedule(@PathVariable Integer id) {
        workScheduleService.deleteWorkSchedule(id);
        return ResponseEntity.noContent().build();
    }
} 