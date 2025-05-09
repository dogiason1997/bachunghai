package com.example.demo.repository;

import com.example.demo.entity.WaterResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WaterResourceRepository extends JpaRepository<WaterResource, Integer> {
    Page<WaterResource> findAll(Pageable pageable);
    
    Optional<WaterResource> findByResourceName(String resourceName);
    
    boolean existsByResourceName(String resourceName);
    
    boolean existsByResourceNameAndIdResourceNot(String resourceName, Integer idResource);
    
    @Query("SELECT w FROM WaterResource w WHERE " +
           "(:resourceName IS NULL OR w.resourceName LIKE %:resourceName%) AND " +
           "(:resourceType IS NULL OR w.resourceType = :resourceType) AND " +
           "(:departmentId IS NULL OR w.department.idPhongBan = :departmentId)")
    Page<WaterResource> findByFilters(
            @Param("resourceName") String resourceName,
            @Param("resourceType") String resourceType,
            @Param("departmentId") Integer departmentId,
            Pageable pageable);
} 