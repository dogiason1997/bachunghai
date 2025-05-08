package com.example.demo.controller;

import com.example.demo.dto.ParticipantDTO;
import com.example.demo.service.ParticipantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/participants")
public class ParticipantController {

    private final ParticipantService participantService;

    @Autowired
    public ParticipantController(ParticipantService participantService) {
        this.participantService = participantService;
    }

    // Create a new participant
    @PostMapping
    public ResponseEntity<ParticipantDTO> createParticipant(@RequestBody ParticipantDTO participantDTO) {
        ParticipantDTO createdParticipant = participantService.createParticipant(participantDTO);
        return new ResponseEntity<>(createdParticipant, HttpStatus.CREATED);
    }

    // Get all participants with pagination
    @GetMapping
    public ResponseEntity<Page<ParticipantDTO>> getAllParticipants(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "name") String sortBy) {
        
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        Page<ParticipantDTO> participants = participantService.getAllParticipants(pageable);
        return ResponseEntity.ok(participants);
    }

    // Get participants by work schedule ID with pagination
    @GetMapping("/by-work-schedule/{workScheduleId}")
    public ResponseEntity<Page<ParticipantDTO>> getParticipantsByWorkSchedule(
            @PathVariable Integer workScheduleId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "name") String sortBy) {
        
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        Page<ParticipantDTO> participants = participantService.getAllParticipantsByWorkSchedule(workScheduleId, pageable);
        return ResponseEntity.ok(participants);
    }

    // Get all participants by work schedule ID (no pagination)
    @GetMapping("/list/by-work-schedule/{workScheduleId}")
    public ResponseEntity<List<ParticipantDTO>> getAllParticipantsByWorkSchedule(
            @PathVariable Integer workScheduleId) {
        List<ParticipantDTO> participants = participantService.getAllParticipantsByWorkSchedule(workScheduleId);
        return ResponseEntity.ok(participants);
    }

    // Get participant by ID
    @GetMapping("/{id}")
    public ResponseEntity<ParticipantDTO> getParticipantById(@PathVariable Integer id) {
        ParticipantDTO participant = participantService.getParticipantById(id);
        return ResponseEntity.ok(participant);
    }

    // Update a participant
    @PutMapping("/{id}")
    public ResponseEntity<ParticipantDTO> updateParticipant(
            @PathVariable Integer id,
            @RequestBody ParticipantDTO participantDTO) {
        
        ParticipantDTO updatedParticipant = participantService.updateParticipant(id, participantDTO);
        return ResponseEntity.ok(updatedParticipant);
    }

    // Delete a participant
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteParticipant(@PathVariable Integer id) {
        participantService.deleteParticipant(id);
        return ResponseEntity.noContent().build();
    }
} 