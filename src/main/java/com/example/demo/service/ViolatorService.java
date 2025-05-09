package com.example.demo.service;

import com.example.demo.dto.ViolatorDTO;
import com.example.demo.entity.Violator;
import com.example.demo.exception.DuplicateResourceException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ViolatorService {
    /**
     * Tạo mới đối tượng vi phạm
     * @param violatorDTO thông tin đối tượng vi phạm
     * @return thông tin đối tượng vi phạm đã tạo
     * @throws DuplicateResourceException nếu email, phone hoặc identityNumber đã tồn tại
     */
    Violator createViolator(ViolatorDTO violatorDTO) throws DuplicateResourceException;
    
    /**
     * Lấy thông tin đối tượng vi phạm theo ID
     * @param id ID đối tượng vi phạm
     * @return thông tin đối tượng vi phạm
     */
    Optional<Violator> getViolatorById(Integer id);
    
    /**
     * Lấy danh sách tất cả đối tượng vi phạm có phân trang
     * @param pageable thông tin phân trang
     * @return danh sách đối tượng vi phạm
     */
    Page<Violator> getAllViolators(Pageable pageable);
    
    /**
     * Tìm kiếm đối tượng vi phạm theo tên và loại tổ chức
     * @param name tên đối tượng vi phạm
     * @param organizationType loại tổ chức
     * @param pageable thông tin phân trang
     * @return danh sách đối tượng vi phạm tìm thấy
     */
    Page<Violator> searchViolators(String name, String organizationType, Pageable pageable);
    
    /**
     * Cập nhật thông tin đối tượng vi phạm
     * @param id ID đối tượng vi phạm cần cập nhật
     * @param violatorDTO thông tin cập nhật
     * @return thông tin đối tượng vi phạm sau khi cập nhật
     * @throws EntityNotFoundException nếu không tìm thấy đối tượng vi phạm
     * @throws DuplicateResourceException nếu email, phone hoặc identityNumber đã tồn tại (với đối tượng vi phạm khác)
     */
    Violator updateViolator(Integer id, ViolatorDTO violatorDTO) 
            throws EntityNotFoundException, DuplicateResourceException;
    
    /**
     * Xóa đối tượng vi phạm
     * @param id ID đối tượng vi phạm cần xóa
     * @throws EntityNotFoundException nếu không tìm thấy đối tượng vi phạm
     */
    void deleteViolator(Integer id) throws EntityNotFoundException;
} 