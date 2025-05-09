package com.example.demo.service.impl;

import com.example.demo.dto.WaterResourceDTO;
import com.example.demo.entity.Department;
import com.example.demo.entity.WaterResource;
import com.example.demo.exception.ResourceAlreadyExistsException;
import com.example.demo.repository.DepartmentRepository;
import com.example.demo.repository.WaterResourceRepository;
import com.example.demo.service.WaterResourceService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class WaterResourceServiceImpl implements WaterResourceService {

    private final WaterResourceRepository waterResourceRepository;
    private final DepartmentRepository departmentRepository;

    @Autowired
    public WaterResourceServiceImpl(WaterResourceRepository waterResourceRepository, 
                                   DepartmentRepository departmentRepository) {
        this.waterResourceRepository = waterResourceRepository;
        this.departmentRepository = departmentRepository;
    }

    @Override
    @Transactional
    public WaterResource createWaterResource(WaterResourceDTO waterResourceDTO) {
        // Kiểm tra tên nguồn nước đã tồn tại hay chưa
        if (waterResourceRepository.existsByResourceName(waterResourceDTO.getResourceName())) {
            throw new ResourceAlreadyExistsException("Nguồn nước với tên '" + 
                    waterResourceDTO.getResourceName() + "' đã tồn tại");
        }
        
        WaterResource waterResource = convertToEntity(waterResourceDTO);
        return waterResourceRepository.save(waterResource);
    }

    @Override
    public Optional<WaterResource> getWaterResourceById(Integer id) {
        return waterResourceRepository.findById(id);
    }

    @Override
    public Page<WaterResource> getAllWaterResources(Pageable pageable) {
        return waterResourceRepository.findAll(pageable);
    }

    @Override
    public Page<WaterResource> searchWaterResources(String resourceName, String resourceType, 
                                                     String departmentName, Pageable pageable) {
        Integer departmentId = null;
        
        if (departmentName != null && !departmentName.isEmpty()) {
            departmentId = departmentRepository.findByNameDepartment(departmentName)
                    .map(Department::getIdPhongBan)
                    .orElse(null);
        }
        
        return waterResourceRepository.findByFilters(resourceName, resourceType, departmentId, pageable);
    }

    @Override
    @Transactional
    public WaterResource updateWaterResource(Integer id, WaterResourceDTO waterResourceDTO) {
        // Kiểm tra nguồn nước tồn tại
        WaterResource existingResource = waterResourceRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy nguồn nước với ID: " + id));
        
        // Kiểm tra tên nguồn nước đã tồn tại với nguồn nước khác hay chưa
        if (waterResourceRepository.existsByResourceNameAndIdResourceNot(
                waterResourceDTO.getResourceName(), id)) {
            throw new ResourceAlreadyExistsException("Nguồn nước với tên '" + 
                    waterResourceDTO.getResourceName() + "' đã tồn tại");
        }
        
        WaterResource waterResource = convertToEntity(waterResourceDTO);
        waterResource.setIdResource(id);
        waterResource.setViolates(existingResource.getViolates());
        
        return waterResourceRepository.save(waterResource);
    }

    @Override
    @Transactional
    public void deleteWaterResource(Integer id) {
        if (!waterResourceRepository.existsById(id)) {
            throw new EntityNotFoundException("Không tìm thấy nguồn nước với ID: " + id);
        }
        waterResourceRepository.deleteById(id);
    }

    private WaterResource convertToEntity(WaterResourceDTO dto) {
        WaterResource entity = new WaterResource();
        
        entity.setResourceName(dto.getResourceName());
        entity.setResourceType(dto.getResourceType());
        entity.setLocation(dto.getLocation());
        entity.setLatitude(dto.getLatitude());
        entity.setLongitude(dto.getLongitude());
        
        // Convert department name to department entity
        if (dto.getNameDepartmentManagement() != null && !dto.getNameDepartmentManagement().isEmpty()) {
            Department department = departmentRepository.findByNameDepartment(dto.getNameDepartmentManagement())
                    .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy phòng ban với tên: " + 
                            dto.getNameDepartmentManagement()));
            entity.setDepartment(department);
        }
        
        return entity;
    }
} 