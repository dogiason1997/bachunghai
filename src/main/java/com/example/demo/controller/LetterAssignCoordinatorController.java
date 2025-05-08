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

import com.example.demo.dto.LetterAssignCoordinatorDTO;
import com.example.demo.service.LetterAssignCoordinatorService;

import jakarta.persistence.EntityNotFoundException;

@RestController
@RequestMapping("/letter-assign-coordinators")
public class LetterAssignCoordinatorController {

    @Autowired
    private LetterAssignCoordinatorService coordinatorService;
    
    @GetMapping
    public ResponseEntity<List<LetterAssignCoordinatorDTO>> getAllCoordinators() {
        List<LetterAssignCoordinatorDTO> coordinators = coordinatorService.getAllCoordinators();
        return ResponseEntity.ok(coordinators);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<LetterAssignCoordinatorDTO> getCoordinatorById(@PathVariable Integer id) {
        try {
            LetterAssignCoordinatorDTO coordinator = coordinatorService.getCoordinatorById(id);
            return ResponseEntity.ok(coordinator);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping("/letter-assign/{letterAssignId}")
    public ResponseEntity<List<LetterAssignCoordinatorDTO>> getCoordinatorsByLetterAssignId(
            @PathVariable Integer letterAssignId) {
        try {
            List<LetterAssignCoordinatorDTO> coordinators = 
                    coordinatorService.getCoordinatorsByLetterAssignId(letterAssignId);
            return ResponseEntity.ok(coordinators);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<LetterAssignCoordinatorDTO>> getLetterAssignsByCoordinatorId(
            @PathVariable Integer userId) {
        try {
            List<LetterAssignCoordinatorDTO> letterAssigns = 
                    coordinatorService.getLetterAssignsByCoordinatorId(userId);
            return ResponseEntity.ok(letterAssigns);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @PostMapping
    public ResponseEntity<?> addCoordinator(@RequestBody LetterAssignCoordinatorDTO coordinatorDTO) {
        try {
            LetterAssignCoordinatorDTO savedCoordinator = coordinatorService.addCoordinator(coordinatorDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedCoordinator);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    
    @PostMapping("/letter-assign/{letterAssignId}/add-multiple")
    public ResponseEntity<?> addCoordinators(
            @PathVariable Integer letterAssignId, 
            @RequestBody List<Integer> coordinatorIds) {
        try {
            List<LetterAssignCoordinatorDTO> savedCoordinators = 
                    coordinatorService.addCoordinators(letterAssignId, coordinatorIds);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedCoordinators);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<?> updateCoordinator(
            @PathVariable Integer id, 
            @RequestBody LetterAssignCoordinatorDTO coordinatorDTO) {
        try {
            LetterAssignCoordinatorDTO updatedCoordinator = 
                    coordinatorService.updateCoordinator(id, coordinatorDTO);
            return ResponseEntity.ok(updatedCoordinator);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCoordinator(@PathVariable Integer id) {
        try {
            coordinatorService.deleteCoordinator(id);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
    
    @DeleteMapping("/letter-assign/{letterAssignId}/coordinator/{coordinatorId}")
    public ResponseEntity<?> removeCoordinatorFromLetterAssign(
            @PathVariable Integer letterAssignId,
            @PathVariable Integer coordinatorId) {
        try {
            coordinatorService.removeCoordinatorFromLetterAssign(letterAssignId, coordinatorId);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
    
    @DeleteMapping("/letter-assign/{letterAssignId}/coordinators")
    public ResponseEntity<?> removeAllCoordinatorsFromLetterAssign(@PathVariable Integer letterAssignId) {
        try {
            coordinatorService.removeAllCoordinatorsFromLetterAssign(letterAssignId);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
} 