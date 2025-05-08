package com.example.demo.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dto.LetterRelationDTO;
import com.example.demo.entity.Letter;
import com.example.demo.entity.LetterRelation;
import com.example.demo.repository.LetterRelationRepository;
import com.example.demo.repository.LetterRepository;
import com.example.demo.service.LetterRelationService;

import jakarta.persistence.EntityNotFoundException;

@Service
@Transactional
public class LetterRelationServiceImpl implements LetterRelationService {

    @Autowired
    private LetterRelationRepository letterRelationRepository;
    
    @Autowired
    private LetterRepository letterRepository;

    @Override
    @Transactional(readOnly = true)
    public List<LetterRelationDTO> getAllLetterRelations() {
        return letterRelationRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public LetterRelationDTO getLetterRelationById(Integer id) {
        LetterRelation relation = letterRelationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Letter relation not found with ID: " + id));
        return convertToDTO(relation);
    }

    @Override
    @Transactional(readOnly = true)
    public List<LetterRelationDTO> getLetterRelationsByLetterId(Integer letterId) {
        if (!letterRepository.existsById(letterId)) {
            throw new EntityNotFoundException("Letter not found with ID: " + letterId);
        }
        
        return letterRelationRepository.findByFromLetter_IdLetterOrToLetter_IdLetter(letterId, letterId)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<LetterRelationDTO> getRelationsFromLetter(Integer fromLetterId) {
        if (!letterRepository.existsById(fromLetterId)) {
            throw new EntityNotFoundException("Letter not found with ID: " + fromLetterId);
        }
        
        return letterRelationRepository.findByFromLetter_IdLetter(fromLetterId)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<LetterRelationDTO> getRelationsToLetter(Integer toLetterId) {
        if (!letterRepository.existsById(toLetterId)) {
            throw new EntityNotFoundException("Letter not found with ID: " + toLetterId);
        }
        
        return letterRelationRepository.findByToLetter_IdLetter(toLetterId)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<LetterRelationDTO> getRelationsByType(String relationType) {
        if (relationType == null || relationType.isEmpty()) {
            throw new IllegalArgumentException("Relation type cannot be empty");
        }
        
        return letterRelationRepository.findByRelationType(relationType)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public LetterRelationDTO createLetterRelation(LetterRelationDTO letterRelationDTO) {
        // Kiểm tra từng công văn có tồn tại không
        Letter fromLetter = null;
        Letter toLetter = null;
        
        if (letterRelationDTO.getFromLetterId() != null) {
            fromLetter = letterRepository.findById(letterRelationDTO.getFromLetterId())
                    .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy id Công Văn gửi " + letterRelationDTO.getFromLetterId()));
        } else if (letterRelationDTO.getFromLetterTitle() != null && !letterRelationDTO.getFromLetterTitle().isEmpty()) {
            fromLetter = letterRepository.findByLetterTitle(letterRelationDTO.getFromLetterTitle())
                    .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy tiêu đề Công Văn gửi: " + letterRelationDTO.getFromLetterTitle()));
        } else {
            throw new IllegalArgumentException("Phải cung cấp Id hoặc tiêu đề Công Văn");
        }
        
        if (letterRelationDTO.getToLetterId() != null) {
            toLetter = letterRepository.findById(letterRelationDTO.getToLetterId())
                    .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy id Công Văn đến: " + letterRelationDTO.getToLetterId()));
        } else if (letterRelationDTO.getToLetterTitle() != null && !letterRelationDTO.getToLetterTitle().isEmpty()) {
            toLetter = letterRepository.findByLetterTitle(letterRelationDTO.getToLetterTitle())
                    .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy tiêu đề công Văn đến: " + letterRelationDTO.getToLetterTitle()));
        } else {
            throw new IllegalArgumentException("Phải cung cấp tiêu đề hoặc id Công Văn");
        }
        
        // Kiểm tra xem quan hệ đã tồn tại chưa
        if (letterRelationRepository.existsByFromLetter_IdLetterAndToLetter_IdLetter(
                fromLetter.getIdLetter(), toLetter.getIdLetter())) {
            throw new IllegalStateException("Mối quan hệ giữa hai công văn đã tồn tại");
        }
        
        // Tạo mới quan hệ
        LetterRelation relation = new LetterRelation();
        relation.setFromLetter(fromLetter);
        relation.setToLetter(toLetter);
        relation.setRelationType(letterRelationDTO.getRelationType());
        relation.setNotes(letterRelationDTO.getNotes());
        relation.setCreatedAt(LocalDateTime.now()); // Tự động thiết lập thời gian hiện tại
        
        LetterRelation savedRelation = letterRelationRepository.save(relation);
        return convertToDTO(savedRelation);
    }

    @Override
    public LetterRelationDTO updateLetterRelation(Integer id, LetterRelationDTO letterRelationDTO) {
        // Kiểm tra xem quan hệ có tồn tại không
        LetterRelation relation = letterRelationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy mối quan hệ công văn với id: " + id));
        
        // Cập nhật thông tin công văn nguồn nếu cần
        if (letterRelationDTO.getFromLetterId() != null && 
                !relation.getFromLetter().getIdLetter().equals(letterRelationDTO.getFromLetterId())) {
            Letter fromLetter = letterRepository.findById(letterRelationDTO.getFromLetterId())
                    .orElseThrow(() -> new EntityNotFoundException("From Letter not found with ID: " + letterRelationDTO.getFromLetterId()));
            relation.setFromLetter(fromLetter);
        } else if (letterRelationDTO.getFromLetterTitle() != null && !letterRelationDTO.getFromLetterTitle().isEmpty() && 
                !relation.getFromLetter().getLetterTitle().equals(letterRelationDTO.getFromLetterTitle())) {
            Letter fromLetter = letterRepository.findByLetterTitle(letterRelationDTO.getFromLetterTitle())
                    .orElseThrow(() -> new EntityNotFoundException("From Letter not found with title: " + letterRelationDTO.getFromLetterTitle()));
            relation.setFromLetter(fromLetter);
        }
        
        // Cập nhật thông tin công văn đích nếu cần
        if (letterRelationDTO.getToLetterId() != null && 
                !relation.getToLetter().getIdLetter().equals(letterRelationDTO.getToLetterId())) {
            Letter toLetter = letterRepository.findById(letterRelationDTO.getToLetterId())
                    .orElseThrow(() -> new EntityNotFoundException("To Letter not found with ID: " + letterRelationDTO.getToLetterId()));
            relation.setToLetter(toLetter);
        } else if (letterRelationDTO.getToLetterTitle() != null && !letterRelationDTO.getToLetterTitle().isEmpty() && 
                !relation.getToLetter().getLetterTitle().equals(letterRelationDTO.getToLetterTitle())) {
            Letter toLetter = letterRepository.findByLetterTitle(letterRelationDTO.getToLetterTitle())
                    .orElseThrow(() -> new EntityNotFoundException("To Letter not found with title: " + letterRelationDTO.getToLetterTitle()));
            relation.setToLetter(toLetter);
        }
        
        // Cập nhật các trường khác
        if (letterRelationDTO.getRelationType() != null) {
            relation.setRelationType(letterRelationDTO.getRelationType());
        }
        
        if (letterRelationDTO.getNotes() != null) {
            relation.setNotes(letterRelationDTO.getNotes());
        }
        
        // Không cập nhật createdAt vì đó là thời gian tạo ban đầu
        
        LetterRelation updatedRelation = letterRelationRepository.save(relation);
        return convertToDTO(updatedRelation);
    }

    @Override
    public void deleteLetterRelation(Integer id) {
        if (!letterRelationRepository.existsById(id)) {
            throw new EntityNotFoundException("Letter relation not found with ID: " + id);
        }
        letterRelationRepository.deleteById(id);
    }
    
    // Phương thức hỗ trợ chuyển đổi Entity sang DTO
    private LetterRelationDTO convertToDTO(LetterRelation relation) {
        LetterRelationDTO dto = new LetterRelationDTO();
        dto.setRelationId(relation.getRelationId());
        
        if (relation.getFromLetter() != null) {
            dto.setFromLetterId(relation.getFromLetter().getIdLetter());
            dto.setFromLetterTitle(relation.getFromLetter().getLetterTitle());
        }
        
        if (relation.getToLetter() != null) {
            dto.setToLetterId(relation.getToLetter().getIdLetter());
            dto.setToLetterTitle(relation.getToLetter().getLetterTitle());
        }
        
        dto.setRelationType(relation.getRelationType());
        dto.setNotes(relation.getNotes());
        
        return dto;
    }
} 