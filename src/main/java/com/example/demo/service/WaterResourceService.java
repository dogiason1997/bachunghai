package com.example.demo.service;

import com.example.demo.dto.WaterResourceDTO;
import com.example.demo.entity.WaterResource;
import com.example.demo.exception.ResourceAlreadyExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface WaterResourceService {
    /**
     * Tạo mới nguồn nước
     * @param waterResourceDTO thông tin nguồn nước
     * @return thông tin nguồn nước đã tạo
     * @throws ResourceAlreadyExistsException nếu tên nguồn nước đã tồn tại
     */
    WaterResource createWaterResource(WaterResourceDTO waterResourceDTO) throws ResourceAlreadyExistsException;
    
    /**
     * Lấy thông tin nguồn nước theo ID
     * @param id ID nguồn nước
     * @return thông tin nguồn nước
     */
    Optional<WaterResource> getWaterResourceById(Integer id);
    
    /**
     * Lấy danh sách tất cả nguồn nước có phân trang
     * @param pageable thông tin phân trang
     * @return danh sách nguồn nước
     */
    Page<WaterResource> getAllWaterResources(Pageable pageable);
    
    /**
     * Tìm kiếm nguồn nước theo các tiêu chí
     * @param resourceName tên nguồn nước
     * @param resourceType loại nguồn nước
     * @param departmentName tên phòng ban quản lý
     * @param pageable thông tin phân trang
     * @return danh sách nguồn nước tìm thấy
     */
    Page<WaterResource> searchWaterResources(String resourceName, String resourceType, String departmentName, Pageable pageable);
    
    /**
     * Cập nhật thông tin nguồn nước
     * @param id ID nguồn nước cần cập nhật
     * @param waterResourceDTO thông tin cập nhật
     * @return thông tin nguồn nước sau khi cập nhật
     * @throws EntityNotFoundException nếu không tìm thấy nguồn nước
     * @throws ResourceAlreadyExistsException nếu tên nguồn nước đã tồn tại (với nguồn nước khác)
     */
    WaterResource updateWaterResource(Integer id, WaterResourceDTO waterResourceDTO) throws EntityNotFoundException, ResourceAlreadyExistsException;
    
    /**
     * Xóa nguồn nước
     * @param id ID nguồn nước cần xóa
     * @throws EntityNotFoundException nếu không tìm thấy nguồn nước
     */
    void deleteWaterResource(Integer id) throws EntityNotFoundException;
} 