package com.example.demo.service.impl;

import com.example.demo.dto.ViolatorDTO;
import com.example.demo.entity.Violator;
import com.example.demo.exception.DuplicateResourceException;
import com.example.demo.repository.ViolatorRepository;
import com.example.demo.service.ViolatorService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Optional;

@Service
public class ViolatorServiceImpl implements ViolatorService {

    private final ViolatorRepository violatorRepository;

    @Autowired
    public ViolatorServiceImpl(ViolatorRepository violatorRepository) {
        this.violatorRepository = violatorRepository;
    }

    @Override
    @Transactional
    public Violator createViolator(ViolatorDTO violatorDTO) {
        // Kiểm tra email đã tồn tại hay chưa
        if (StringUtils.hasText(violatorDTO.getEmail()) && 
                violatorRepository.existsByEmail(violatorDTO.getEmail())) {
            throw new DuplicateResourceException("Email '" + 
                    violatorDTO.getEmail() + "' đã được sử dụng");
        }
        
        // Kiểm tra số điện thoại đã tồn tại hay chưa
        if (StringUtils.hasText(violatorDTO.getPhone()) && 
                violatorRepository.existsByPhone(violatorDTO.getPhone())) {
            throw new DuplicateResourceException("Số điện thoại '" + 
                    violatorDTO.getPhone() + "' đã được sử dụng");
        }
        
        // Kiểm tra số giấy tờ đã tồn tại hay chưa
        if (StringUtils.hasText(violatorDTO.getIdentityNumber()) && 
                violatorRepository.existsByIdentityNumber(violatorDTO.getIdentityNumber())) {
            throw new DuplicateResourceException("Số giấy tờ tùy thân '" + 
                    violatorDTO.getIdentityNumber() + "' đã được sử dụng");
        }
        
        Violator violator = convertToEntity(violatorDTO);
        return violatorRepository.save(violator);
    }

    @Override
    public Optional<Violator> getViolatorById(Integer id) {
        return violatorRepository.findById(id);
    }

    @Override
    public Page<Violator> getAllViolators(Pageable pageable) {
        return violatorRepository.findAll(pageable);
    }

    @Override
    public Page<Violator> searchViolators(String name, String organizationType, Pageable pageable) {
        return violatorRepository.findByNameAndOrganizationType(name, organizationType, pageable);
    }

    @Override
    @Transactional
    public Violator updateViolator(Integer id, ViolatorDTO violatorDTO) {
        // Kiểm tra đối tượng vi phạm tồn tại
        Violator existingViolator = violatorRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy đối tượng vi phạm với ID: " + id));
        
        // Kiểm tra email đã tồn tại với đối tượng vi phạm khác hay chưa
        if (StringUtils.hasText(violatorDTO.getEmail()) && 
                violatorRepository.existsByEmailAndIdViolatorNot(violatorDTO.getEmail(), id)) {
            throw new DuplicateResourceException("Email '" + 
                    violatorDTO.getEmail() + "' đã được sử dụng");
        }
        
        // Kiểm tra số điện thoại đã tồn tại với đối tượng vi phạm khác hay chưa
        if (StringUtils.hasText(violatorDTO.getPhone()) && 
                violatorRepository.existsByPhoneAndIdViolatorNot(violatorDTO.getPhone(), id)) {
            throw new DuplicateResourceException("Số điện thoại '" + 
                    violatorDTO.getPhone() + "' đã được sử dụng");
        }
        
        // Kiểm tra số giấy tờ đã tồn tại với đối tượng vi phạm khác hay chưa
        if (StringUtils.hasText(violatorDTO.getIdentityNumber()) && 
                violatorRepository.existsByIdentityNumberAndIdViolatorNot(violatorDTO.getIdentityNumber(), id)) {
            throw new DuplicateResourceException("Số giấy tờ tùy thân '" + 
                    violatorDTO.getIdentityNumber() + "' đã được sử dụng");
        }
        
        Violator violator = convertToEntity(violatorDTO);
        violator.setIdViolator(id);
        violator.setViolates(existingViolator.getViolates());
        
        return violatorRepository.save(violator);
    }

    @Override
    @Transactional
    public void deleteViolator(Integer id) {
        if (!violatorRepository.existsById(id)) {
            throw new EntityNotFoundException("Không tìm thấy đối tượng vi phạm với ID: " + id);
        }
        violatorRepository.deleteById(id);
    }

    private Violator convertToEntity(ViolatorDTO dto) {
        Violator entity = new Violator();
        
        entity.setName(dto.getName());
        entity.setAddress(dto.getAddress());
        entity.setPhone(dto.getPhone());
        entity.setEmail(dto.getEmail());
        entity.setIdentityNumber(dto.getIdentityNumber());
        entity.setOrganizationType(dto.getOrganizationType());
        entity.setRepresentative(dto.getRepresentative());
        
        return entity;
    }
} 