package com.example.demo.service;

import com.example.demo.dto.ViolationLevelDTO;
import com.example.demo.entity.ViolationLevel;
import com.example.demo.exception.ResourceAlreadyExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ViolationLevelService {
    /**
     * Tạo mới mức độ vi phạm
     * @param violationLevelDTO thông tin mức độ vi phạm
     * @return thông tin mức độ vi phạm đã tạo
     * @throws ResourceAlreadyExistsException nếu tên mức độ vi phạm đã tồn tại
     */
    ViolationLevel createViolationLevel(ViolationLevelDTO violationLevelDTO) throws ResourceAlreadyExistsException;
    
    /**
     * Lấy thông tin mức độ vi phạm theo ID
     * @param id ID mức độ vi phạm
     * @return thông tin mức độ vi phạm
     */
    Optional<ViolationLevel> getViolationLevelById(Integer id);
    
    /**
     * Lấy danh sách tất cả mức độ vi phạm có phân trang
     * @param pageable thông tin phân trang
     * @return danh sách mức độ vi phạm
     */
    Page<ViolationLevel> getAllViolationLevels(Pageable pageable);
    
    /**
     * Cập nhật thông tin mức độ vi phạm
     * @param id ID mức độ vi phạm cần cập nhật
     * @param violationLevelDTO thông tin cập nhật
     * @return thông tin mức độ vi phạm sau khi cập nhật
     * @throws EntityNotFoundException nếu không tìm thấy mức độ vi phạm
     * @throws ResourceAlreadyExistsException nếu tên mức độ vi phạm đã tồn tại (với mức độ vi phạm khác)
     */
    ViolationLevel updateViolationLevel(Integer id, ViolationLevelDTO violationLevelDTO) 
            throws EntityNotFoundException, ResourceAlreadyExistsException;
    
    /**
     * Xóa mức độ vi phạm
     * @param id ID mức độ vi phạm cần xóa
     * @throws EntityNotFoundException nếu không tìm thấy mức độ vi phạm
     */
    void deleteViolationLevel(Integer id) throws EntityNotFoundException;
} 