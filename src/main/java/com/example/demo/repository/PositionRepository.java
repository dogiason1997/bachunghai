package com.example.demo.repository;

import com.example.demo.entity.Position;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PositionRepository extends JpaRepository<Position, Long> {
    Optional<Position> findByPositionCode(String positionCode);
    
    @Query("SELECT p FROM Position p JOIN p.users u WHERE u.fullName LIKE %:fullName%")
    List<Position> findByUserFullName(@Param("fullName") String fullName);

    // Position findByPositionCode(String positionCode);
    Position findByPositionName(String positionName);
} 