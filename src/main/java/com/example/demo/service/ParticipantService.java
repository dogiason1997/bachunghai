package com.example.demo.service;

import com.example.demo.dto.ParticipantDTO;
import com.example.demo.entity.Participant;
import com.example.demo.entity.WorkSchedule;
import com.example.demo.repository.ParticipantRepository;
import com.example.demo.repository.WorkScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ParticipantService {

    private final ParticipantRepository participantRepository;
    private final WorkScheduleRepository workScheduleRepository;

    @Autowired
    public ParticipantService(ParticipantRepository participantRepository, WorkScheduleRepository workScheduleRepository) {
        this.participantRepository = participantRepository;
        this.workScheduleRepository = workScheduleRepository;
    }

    // Convert Entity to DTO
    private ParticipantDTO convertToDTO(Participant participant) {
        ParticipantDTO dto = new ParticipantDTO();
        dto.setName(participant.getName());
        dto.setEmail(participant.getEmail());
        dto.setPhone(participant.getPhone());
        dto.setWorkScheduleId(participant.getWorkSchedule().getIdWorkSchedule());
        return dto;
    }

    // Convert DTO to Entity
    private Participant convertToEntity(ParticipantDTO dto) {
        Participant participant = new Participant();
        participant.setName(dto.getName());
        participant.setEmail(dto.getEmail());
        participant.setPhone(dto.getPhone());
        
        // Find the work schedule
        WorkSchedule workSchedule = workScheduleRepository.findById(dto.getWorkScheduleId())
                .orElseThrow(() -> new RuntimeException("WorkSchedule not found with id: " + dto.getWorkScheduleId()));
        participant.setWorkSchedule(workSchedule);
        
        return participant;
    }

    // CRUD Operations
    
    // Create
    @Transactional
    public ParticipantDTO createParticipant(ParticipantDTO participantDTO) {
        Participant participant = convertToEntity(participantDTO);
        Participant savedParticipant = participantRepository.save(participant);
        return convertToDTO(savedParticipant);
    }
    
    // Read
    public Page<ParticipantDTO> getAllParticipants(Pageable pageable) {
        Page<Participant> participantsPage = participantRepository.findAll(pageable);
        return participantsPage.map(this::convertToDTO);
    }
    
    public List<ParticipantDTO> getAllParticipantsByWorkSchedule(Integer workScheduleId) {
        WorkSchedule workSchedule = workScheduleRepository.findById(workScheduleId)
                .orElseThrow(() -> new RuntimeException("WorkSchedule not found with id: " + workScheduleId));
        
        List<Participant> participants = participantRepository.findByWorkSchedule(workSchedule);
        return participants.stream().map(this::convertToDTO).collect(Collectors.toList());
    }
    
    public Page<ParticipantDTO> getAllParticipantsByWorkSchedule(Integer workScheduleId, Pageable pageable) {
        WorkSchedule workSchedule = workScheduleRepository.findById(workScheduleId)
                .orElseThrow(() -> new RuntimeException("WorkSchedule not found with id: " + workScheduleId));
        
        Page<Participant> participantsPage = participantRepository.findByWorkSchedule(workSchedule, pageable);
        return participantsPage.map(this::convertToDTO);
    }
    
    public ParticipantDTO getParticipantById(Integer id) {
        Participant participant = participantRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Participant not found with id: " + id));
        return convertToDTO(participant);
    }
    
    // Update
    @Transactional
    public ParticipantDTO updateParticipant(Integer id, ParticipantDTO participantDTO) {
        Optional<Participant> optionalParticipant = participantRepository.findById(id);
        if (optionalParticipant.isPresent()) {
            Participant existingParticipant = optionalParticipant.get();
            existingParticipant.setName(participantDTO.getName());
            existingParticipant.setEmail(participantDTO.getEmail());
            existingParticipant.setPhone(participantDTO.getPhone());
            
            // Update work schedule if it has changed
            if (!existingParticipant.getWorkSchedule().getIdWorkSchedule().equals(participantDTO.getWorkScheduleId())) {
                WorkSchedule workSchedule = workScheduleRepository.findById(participantDTO.getWorkScheduleId())
                        .orElseThrow(() -> new RuntimeException("WorkSchedule not found with id: " + participantDTO.getWorkScheduleId()));
                existingParticipant.setWorkSchedule(workSchedule);
            }
            
            Participant updatedParticipant = participantRepository.save(existingParticipant);
            return convertToDTO(updatedParticipant);
        } else {
            throw new RuntimeException("Participant not found with id: " + id);
        }
    }
    
    // Delete
    @Transactional
    public void deleteParticipant(Integer id) {
        if (participantRepository.existsById(id)) {
            participantRepository.deleteById(id);
        } else {
            throw new RuntimeException("Participant not found with id: " + id);
        }
    }
} 