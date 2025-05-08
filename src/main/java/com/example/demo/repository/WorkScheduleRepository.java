package com.example.demo.repository;

import com.example.demo.entity.WorkSchedule;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface WorkScheduleRepository extends JpaRepository<WorkSchedule, Integer> {
    Page<WorkSchedule> findByIdUser(Integer userId, Pageable pageable);
    List<WorkSchedule> findByIdUser(Integer userId);
    Page<WorkSchedule> findByDateWorkBetween(LocalDate startDate, LocalDate endDate, Pageable pageable);
    Page<WorkSchedule> findByIdUserAndDateWorkBetween(Integer userId, LocalDate startDate, LocalDate endDate, Pageable pageable);
} 