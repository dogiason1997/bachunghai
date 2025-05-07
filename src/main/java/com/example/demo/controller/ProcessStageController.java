package com.example.demo.controller;

import com.example.demo.entity.ProcessStage;
import com.example.demo.service.ProcessStageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/process-stages")
public class ProcessStageController {

    @Autowired
    private ProcessStageService processStageService;

    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllProcessStages(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "stageOrder,asc") String[] sort) {

        String sortField = sort[0];
        String sortDirection = sort.length > 1 ? sort[1] : "asc";
        
        Sort.Direction direction = Sort.Direction.fromString(sortDirection);
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortField));
        
        Page<ProcessStage> pageProcessStages = processStageService.getAllProcessStages(pageable);

        Map<String, Object> response = new HashMap<>();
        response.put("content", pageProcessStages.getContent());
        response.put("currentPage", pageProcessStages.getNumber());
        response.put("totalItems", pageProcessStages.getTotalElements());
        response.put("totalPages", pageProcessStages.getTotalPages());

        return ResponseEntity.ok(response);
    }

    // @GetMapping("/all")
    // public List<ProcessStage> getAllProcessStagesWithoutPaging() {
    //     return processStageService.getAllProcessStages();
    // }

    @GetMapping("/{id}")
    public ResponseEntity<ProcessStage> getProcessStageById(@PathVariable Integer id) {
        ProcessStage processStage = processStageService.getProcessStageById(id);
        return ResponseEntity.ok(processStage);
    }

    @PostMapping
    public ResponseEntity<ProcessStage> createProcessStage(@RequestBody ProcessStage processStage) {
        ProcessStage createdStage = processStageService.createProcessStage(processStage);
        return ResponseEntity.ok(createdStage);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProcessStage> updateProcessStage(
            @PathVariable Integer id,
            @RequestBody ProcessStage processStage) {
        ProcessStage updatedStage = processStageService.updateProcessStage(id, processStage);
        return ResponseEntity.ok(updatedStage);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteProcessStage(@PathVariable Integer id) {
        processStageService.deleteProcessStage(id);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }
}