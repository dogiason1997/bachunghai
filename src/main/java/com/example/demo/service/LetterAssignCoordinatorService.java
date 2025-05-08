package com.example.demo.service;

import java.util.List;

import com.example.demo.dto.LetterAssignCoordinatorDTO;

public interface LetterAssignCoordinatorService {
    
    // Lấy tất cả người phối hợp
    List<LetterAssignCoordinatorDTO> getAllCoordinators();
    
    // Lấy người phối hợp theo ID
    LetterAssignCoordinatorDTO getCoordinatorById(Integer id);
    
    // Lấy danh sách người phối hợp của một công văn được giao
    List<LetterAssignCoordinatorDTO> getCoordinatorsByLetterAssignId(Integer letterAssignId);
    
    // Lấy danh sách công văn được giao mà một người dùng được phân công phối hợp
    List<LetterAssignCoordinatorDTO> getLetterAssignsByCoordinatorId(Integer userId);
    
    // Thêm một người phối hợp vào công văn được giao
    LetterAssignCoordinatorDTO addCoordinator(LetterAssignCoordinatorDTO coordinatorDTO);
    
    // Thêm nhiều người phối hợp vào công văn được giao
    List<LetterAssignCoordinatorDTO> addCoordinators(Integer letterAssignId, List<Integer> coordinatorIds);
    
    // Cập nhật thông tin người phối hợp
    LetterAssignCoordinatorDTO updateCoordinator(Integer id, LetterAssignCoordinatorDTO coordinatorDTO);
    
    // Xóa người phối hợp theo ID
    void deleteCoordinator(Integer id);
    
    // Xóa người phối hợp cụ thể khỏi công văn được giao
    void removeCoordinatorFromLetterAssign(Integer letterAssignId, Integer coordinatorId);
    
    // Xóa tất cả người phối hợp của một công văn được giao
    void removeAllCoordinatorsFromLetterAssign(Integer letterAssignId);
} 