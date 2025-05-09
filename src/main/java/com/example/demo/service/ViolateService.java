package com.example.demo.service;

import com.example.demo.dto.ViolateDTO;
import com.example.demo.entity.Violate;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.Optional;

public interface ViolateService {
    /**
     * Tạo mới vi phạm
     * @param violateDTO thông tin vi phạm
     * @param idUser ID người dùng tạo vi phạm
     * @return thông tin vi phạm đã tạo
     * @throws EntityNotFoundException nếu không tìm thấy đối tượng tham chiếu (violator, level, resource)
     */
    Violate createViolate(ViolateDTO violateDTO, Integer idUser) throws EntityNotFoundException;
    
    /**
     * Lấy thông tin vi phạm theo ID
     * @param id ID vi phạm
     * @return thông tin vi phạm
     */
    Optional<Violate> getViolateById(Integer id);
    
    /**
     * Lấy danh sách tất cả vi phạm có phân trang
     * @param pageable thông tin phân trang
     * @return danh sách vi phạm
     */
    Page<Violate> getAllViolates(Pageable pageable);
    
    /**
     * Tìm kiếm vi phạm theo các tiêu chí
     * @param locations địa điểm
     * @param idViolator ID đối tượng vi phạm
     * @param idLevel ID mức độ vi phạm
     * @param idResource ID nguồn nước
     * @param startDate ngày bắt đầu
     * @param endDate ngày kết thúc
     * @param pageable thông tin phân trang
     * @return danh sách vi phạm tìm thấy
     */
    Page<Violate> searchViolates(
            String locations,
            Integer idViolator,
            Integer idLevel,
            Integer idResource,
            LocalDate startDate,
            LocalDate endDate,
            Pageable pageable);
    
    /**
     * Cập nhật thông tin vi phạm
     * @param id ID vi phạm cần cập nhật
     * @param violateDTO thông tin cập nhật
     * @return thông tin vi phạm sau khi cập nhật
     * @throws EntityNotFoundException nếu không tìm thấy vi phạm hoặc đối tượng tham chiếu
     */
    Violate updateViolate(Integer id, ViolateDTO violateDTO) throws EntityNotFoundException;
    
    /**
     * Xóa vi phạm
     * @param id ID vi phạm cần xóa
     * @throws EntityNotFoundException nếu không tìm thấy vi phạm
     */
    void deleteViolate(Integer id) throws EntityNotFoundException;
} 