package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.LetterAssignCoordinators;

@Repository
public interface LetterAssignCoordinatorRepository extends JpaRepository<LetterAssignCoordinators, Integer> {
    
    // Tìm danh sách người phối hợp theo ID công văn được giao
    List<LetterAssignCoordinators> findByLetterAssign_IdLetterAssign(Integer letterAssignId);
    
    // Tìm danh sách công văn được giao mà một người dùng được phân công phối hợp
    @Query("SELECT lac FROM LetterAssignCoordinators lac WHERE lac.coordinator.idUser = :userId")
    List<LetterAssignCoordinators> findByCoordinatorId(@Param("userId") Integer userId);
    
    // Kiểm tra xem người dùng đã được phân công phối hợp cho công văn được giao này chưa
    boolean existsByLetterAssign_IdLetterAssignAndCoordinator_IdUser(Integer letterAssignId, Integer userId);
    
    // Xóa người phối hợp cụ thể khỏi công văn được giao
    void deleteByLetterAssign_IdLetterAssignAndCoordinator_IdUser(Integer letterAssignId, Integer userId);
} 