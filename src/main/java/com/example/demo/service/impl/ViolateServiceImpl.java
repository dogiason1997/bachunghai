package com.example.demo.service.impl;

import com.example.demo.dto.ViolateDTO;
import com.example.demo.entity.Violate;
import com.example.demo.entity.ViolationLevel;
import com.example.demo.entity.Violator;
import com.example.demo.entity.WaterResource;
import com.example.demo.repository.ViolateRepository;
import com.example.demo.repository.ViolationLevelRepository;
import com.example.demo.repository.ViolatorRepository;
import com.example.demo.repository.WaterResourceRepository;
import com.example.demo.service.ViolateService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

@Service
public class ViolateServiceImpl implements ViolateService {

    private final ViolateRepository violateRepository;
    private final ViolatorRepository violatorRepository;
    private final ViolationLevelRepository violationLevelRepository;
    private final WaterResourceRepository waterResourceRepository;

    @Autowired
    public ViolateServiceImpl(
            ViolateRepository violateRepository,
            ViolatorRepository violatorRepository,
            ViolationLevelRepository violationLevelRepository,
            WaterResourceRepository waterResourceRepository) {
        this.violateRepository = violateRepository;
        this.violatorRepository = violatorRepository;
        this.violationLevelRepository = violationLevelRepository;
        this.waterResourceRepository = waterResourceRepository;
    }

    @Override
    @Transactional
    public Violate createViolate(ViolateDTO violateDTO, Integer idUser) {
        // Kiểm tra đối tượng vi phạm tồn tại
        Violator violator = violatorRepository.findById(violateDTO.getIdViolator())
                .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy đối tượng vi phạm với ID: " + 
                        violateDTO.getIdViolator()));
        
        // Kiểm tra mức độ vi phạm tồn tại
        ViolationLevel violationLevel = violationLevelRepository.findByLevelName(violateDTO.getLevelName())
                .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy mức độ vi phạm với tên: " + 
                        violateDTO.getLevelName()));
        
        // Kiểm tra nguồn nước tồn tại
        WaterResource waterResource = waterResourceRepository.findByResourceName(violateDTO.getResourceName())
                .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy nguồn nước với tên: " + 
                        violateDTO.getResourceName()));
        
        Violate violate = convertToEntity(violateDTO);
        violate.setIdUser(idUser);
        violate.setIdViolator(violator.getIdViolator());
        violate.setIdLevel(violationLevel.getIdLevel());
        violate.setIdResource(waterResource.getIdResource());
        
        // Khởi tạo các mối quan hệ
        violate.setViolationProcessings(new ArrayList<>());
        violate.setViolateTypeMappings(new ArrayList<>());
        violate.setViolateStepDetails(new ArrayList<>());
        violate.setFilesSaves(new ArrayList<>());
        violate.setUserAssignments(new ArrayList<>());
        
        return violateRepository.save(violate);
    }

    @Override
    public Optional<Violate> getViolateById(Integer id) {
        return violateRepository.findById(id);
    }

    @Override
    public Page<Violate> getAllViolates(Pageable pageable) {
        return violateRepository.findAll(pageable);
    }

    @Override
    public Page<Violate> searchViolates(
            String locations,
            Integer idViolator,
            Integer idLevel,
            Integer idResource,
            LocalDate startDate,
            LocalDate endDate,
            Pageable pageable) {
        return violateRepository.findByFilters(
                locations, idViolator, idLevel, idResource, startDate, endDate, pageable);
    }

    @Override
    @Transactional
    public Violate updateViolate(Integer id, ViolateDTO violateDTO) {
        // Kiểm tra vi phạm tồn tại
        Violate existingViolate = violateRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy vi phạm với ID: " + id));
        
        // Kiểm tra đối tượng vi phạm tồn tại
        Violator violator = violatorRepository.findById(violateDTO.getIdViolator())
                .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy đối tượng vi phạm với ID: " + 
                        violateDTO.getIdViolator()));
        
        // Kiểm tra mức độ vi phạm tồn tại
        ViolationLevel violationLevel = violationLevelRepository.findByLevelName(violateDTO.getLevelName())
                .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy mức độ vi phạm với tên: " + 
                        violateDTO.getLevelName()));
        
        // Kiểm tra nguồn nước tồn tại
        WaterResource waterResource = waterResourceRepository.findByResourceName(violateDTO.getResourceName())
                .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy nguồn nước với tên: " + 
                        violateDTO.getResourceName()));
        
        Violate violate = convertToEntity(violateDTO);
        violate.setIdViolate(id);
        violate.setIdUser(existingViolate.getIdUser());
        violate.setIdViolator(violator.getIdViolator());
        violate.setIdLevel(violationLevel.getIdLevel());
        violate.setIdResource(waterResource.getIdResource());
        
        // Giữ nguyên các mối quan hệ
        violate.setViolationProcessings(existingViolate.getViolationProcessings());
        violate.setViolateTypeMappings(existingViolate.getViolateTypeMappings());
        violate.setViolateStepDetails(existingViolate.getViolateStepDetails());
        violate.setFilesSaves(existingViolate.getFilesSaves());
        violate.setUserAssignments(existingViolate.getUserAssignments());
        
        return violateRepository.save(violate);
    }

    @Override
    @Transactional
    public void deleteViolate(Integer id) {
        if (!violateRepository.existsById(id)) {
            throw new EntityNotFoundException("Không tìm thấy vi phạm với ID: " + id);
        }
        violateRepository.deleteById(id);
    }

    private Violate convertToEntity(ViolateDTO dto) {
        Violate entity = new Violate();
        
        entity.setLocations(dto.getLocations());
        entity.setDateDiscovery(dto.getDateDiscovery());
        entity.setLatitude(dto.getLatitude());
        entity.setLongitude(dto.getLongitude());
        entity.setOrganize(dto.getOrganize());
        entity.setDescribeViolation(dto.getDescribeViolation());
        
        return entity;
    }
} 