package com.example.demo.repository;

import com.example.demo.entity.Participant;
import com.example.demo.entity.WorkSchedule;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParticipantRepository extends JpaRepository<Participant, Integer> {
    Page<Participant> findByWorkSchedule(WorkSchedule workSchedule, Pageable pageable);
    List<Participant> findByWorkSchedule(WorkSchedule workSchedule);
} 