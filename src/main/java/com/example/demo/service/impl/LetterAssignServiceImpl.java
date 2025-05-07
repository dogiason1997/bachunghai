package com.example.demo.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dto.LetterAssignDTO;
import com.example.demo.entity.Letter;
import com.example.demo.entity.LetterAssign;
import com.example.demo.entity.Users;
import com.example.demo.repository.LetterAssignRepository;
import com.example.demo.repository.LetterRepository;
import com.example.demo.repository.UserRepository;
// import com.example.demo.repository.UsersRepository;
import com.example.demo.service.LetterAssignService;

import jakarta.persistence.EntityNotFoundException;

@Service
@Transactional
public class LetterAssignServiceImpl implements LetterAssignService {

    @Autowired
    private LetterAssignRepository letterAssignRepository;
    
    @Autowired
    private UserRepository usersRepository;
    
    @Autowired
    private LetterRepository letterRepository;

    @Override
    @Transactional(readOnly = true)
    public List<LetterAssignDTO> getAllLetterAssigns() {
        return letterAssignRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public LetterAssignDTO getLetterAssignById(Integer id) {
        LetterAssign letterAssign = letterAssignRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("LetterAssign not found with id: " + id));
        return convertToDTO(letterAssign);
    }

    @Override
    public LetterAssignDTO createLetterAssign(LetterAssignDTO letterAssignDTO) {
        LetterAssign letterAssign = convertToEntity(letterAssignDTO);
        // Đảm bảo giá trị cho trường remarks không null
        if (letterAssign.getRemarks() == null) {
            letterAssign.setRemarks("");
        }
        LetterAssign savedLetterAssign = letterAssignRepository.save(letterAssign);
        return convertToDTO(savedLetterAssign);
    }

    @Override
    public LetterAssignDTO updateLetterAssign(Integer id, LetterAssignDTO letterAssignDTO) {
        if (!letterAssignRepository.existsById(id)) {
            throw new EntityNotFoundException("LetterAssign not found with id: " + id);
        }
        
        letterAssignDTO.setIdLetterAssign(id);
        LetterAssign letterAssign = convertToEntity(letterAssignDTO);
        // Đảm bảo giá trị cho trường remarks không null
        if (letterAssign.getRemarks() == null) {
            letterAssign.setRemarks("");
        }
        LetterAssign updatedLetterAssign = letterAssignRepository.save(letterAssign);
        return convertToDTO(updatedLetterAssign);
    }

    @Override
    public void deleteLetterAssign(Integer id) {
        if (!letterAssignRepository.existsById(id)) {
            throw new EntityNotFoundException("LetterAssign not found with id: " + id);
        }
        letterAssignRepository.deleteById(id);
    }
    
    private LetterAssignDTO convertToDTO(LetterAssign letterAssign) {
        LetterAssignDTO dto = new LetterAssignDTO();
        dto.setIdLetterAssign(letterAssign.getIdLetterAssign());
        dto.setDeadline(letterAssign.getDeadline());
        dto.setStatus(letterAssign.getStatus());
        dto.setPresentStage(letterAssign.getPresentStage());
        dto.setRemarks(letterAssign.getRemarks());
        
        // Set assigner information
        if (letterAssign.getUserassigner() != null) {
            dto.setAssignerName(letterAssign.getUserassigner().getFullName());
            dto.setAssignerId(letterAssign.getUserassigner().getIdUser());
        }
        
        // Set letter information
        if (letterAssign.getLetter() != null) {
            dto.setLetterTitle(letterAssign.getLetter().getLetterTitle());
            dto.setLetterId(letterAssign.getLetter().getIdLetter());
        }
        
        // Set main processor information
        if (letterAssign.getMainProcessor() != null) {
            dto.setProcessorName(letterAssign.getMainProcessor().getFullName());
            dto.setProcessorId(letterAssign.getMainProcessor().getIdUser());
        }
        
        return dto;
    }
    
    private LetterAssign convertToEntity(LetterAssignDTO dto) {
        LetterAssign letterAssign = new LetterAssign();
        
        if (dto.getIdLetterAssign() != null) {
            letterAssign.setIdLetterAssign(dto.getIdLetterAssign());
        }
        
        letterAssign.setDeadline(dto.getDeadline());
        letterAssign.setStatus(dto.getStatus());
        letterAssign.setPresentStage(dto.getPresentStage());
        letterAssign.setRemarks(dto.getRemarks() != null ? dto.getRemarks() : "");
        
        // Set assigner
        if (dto.getAssignerId() != null) {
            Users assigner = usersRepository.findById(dto.getAssignerId())
                    .orElseThrow(() -> new EntityNotFoundException("Assigner not found with id: " + dto.getAssignerId()));
            letterAssign.setUserassigner(assigner);
        } else if (dto.getAssignerName() != null && !dto.getAssignerName().isEmpty()) {
            Users assigner = usersRepository.findByFullName(dto.getAssignerName())
                    .orElseThrow(() -> new EntityNotFoundException("Assigner not found with name: " + dto.getAssignerName()));
            letterAssign.setUserassigner(assigner);
        }
        
        // Set letter
        if (dto.getLetterId() != null) {
            Letter letter = letterRepository.findById(dto.getLetterId())
                    .orElseThrow(() -> new EntityNotFoundException("Letter not found with id: " + dto.getLetterId()));
            letterAssign.setLetter(letter);
        } else if (dto.getLetterTitle() != null && !dto.getLetterTitle().isEmpty()) {
            Letter letter = letterRepository.findByLetterTitle(dto.getLetterTitle())
                    .orElseThrow(() -> new EntityNotFoundException("Letter not found with title: " + dto.getLetterTitle()));
            letterAssign.setLetter(letter);
        }
        
        // Set main processor
        if (dto.getProcessorId() != null) {
            Users processor = usersRepository.findById(dto.getProcessorId())
                    .orElseThrow(() -> new EntityNotFoundException("Processor not found with id: " + dto.getProcessorId()));
            letterAssign.setMainProcessor(processor);
        } else if (dto.getProcessorName() != null && !dto.getProcessorName().isEmpty()) {
            Users processor = usersRepository.findByFullName(dto.getProcessorName())
                    .orElseThrow(() -> new EntityNotFoundException("Processor not found with name: " + dto.getProcessorName()));
            letterAssign.setMainProcessor(processor);
        }
        
        return letterAssign;
    }
} 