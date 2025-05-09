package com.example.demo.repository;

import com.example.demo.entity.Violate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface ViolateRepository extends JpaRepository<Violate, Integer> {
    Page<Violate> findAll(Pageable pageable);
    
    @Query("SELECT v FROM Violate v WHERE " +
           "(:locations IS NULL OR v.locations LIKE %:locations%) AND " +
           "(:idViolator IS NULL OR v.idViolator = :idViolator) AND " +
           "(:idLevel IS NULL OR v.idLevel = :idLevel) AND " +
           "(:idResource IS NULL OR v.idResource = :idResource) AND " +
           "(:startDate IS NULL OR v.dateDiscovery >= :startDate) AND " +
           "(:endDate IS NULL OR v.dateDiscovery <= :endDate)")
    Page<Violate> findByFilters(
            @Param("locations") String locations,
            @Param("idViolator") Integer idViolator,
            @Param("idLevel") Integer idLevel,
            @Param("idResource") Integer idResource,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            Pageable pageable);
} 