package com.example.demo.service.impl;

import com.example.demo.dto.ViolationLevelDTO;
import com.example.demo.entity.ViolationLevel;
import com.example.demo.exception.ResourceAlreadyExistsException;
import com.example.demo.repository.ViolationLevelRepository;
import com.example.demo.service.ViolationLevelService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class ViolationLevelServiceImpl implements ViolationLevelService {

    private final ViolationLevelRepository violationLevelRepository;

    @Autowired
    public ViolationLevelServiceImpl(ViolationLevelRepository violationLevelRepository) {
        this.violationLevelRepository = violationLevelRepository;
    }

    @Override
    @Transactional
    public ViolationLevel createViolationLevel(ViolationLevelDTO violationLevelDTO) {
        // Kiểm tra tên mức độ vi phạm đã tồn tại hay chưa
        if (violationLevelRepository.existsByLevelName(violationLevelDTO.getLevelName())) {
            throw new ResourceAlreadyExistsException("Mức độ vi phạm với tên '" + 
                    violationLevelDTO.getLevelName() + "' đã tồn tại");
        }
        
        ViolationLevel violationLevel = convertToEntity(violationLevelDTO);
        return violationLevelRepository.save(violationLevel);
    }

    @Override
    public Optional<ViolationLevel> getViolationLevelById(Integer id) {
        return violationLevelRepository.findById(id);
    }

    @Override
    public Page<ViolationLevel> getAllViolationLevels(Pageable pageable) {
        return violationLevelRepository.findAll(pageable);
    }

    @Override
    @Transactional
    public ViolationLevel updateViolationLevel(Integer id, ViolationLevelDTO violationLevelDTO) {
        // Kiểm tra mức độ vi phạm tồn tại
        ViolationLevel existingLevel = violationLevelRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy mức độ vi phạm với ID: " + id));
        
        // Kiểm tra tên mức độ vi phạm đã tồn tại với mức độ vi phạm khác hay chưa
        if (violationLevelRepository.existsByLevelNameAndIdLevelNot(
                violationLevelDTO.getLevelName(), id)) {
            throw new ResourceAlreadyExistsException("Mức độ vi phạm với tên '" + 
                    violationLevelDTO.getLevelName() + "' đã tồn tại");
        }
        
        ViolationLevel violationLevel = convertToEntity(violationLevelDTO);
        violationLevel.setIdLevel(id);
        violationLevel.setViolates(existingLevel.getViolates());
        
        return violationLevelRepository.save(violationLevel);
    }

    @Override
    @Transactional
    public void deleteViolationLevel(Integer id) {
        if (!violationLevelRepository.existsById(id)) {
            throw new EntityNotFoundException("Không tìm thấy mức độ vi phạm với ID: " + id);
        }
        violationLevelRepository.deleteById(id);
    }

    private ViolationLevel convertToEntity(ViolationLevelDTO dto) {
        ViolationLevel entity = new ViolationLevel();
        
        entity.setLevelName(dto.getLevelName());
        entity.setDescription(dto.getDescription());
        entity.setPenaltyRange(dto.getPenaltyRange());
        entity.setSeverityScore(dto.getSeverityScore());
        
        return entity;
    }
} 