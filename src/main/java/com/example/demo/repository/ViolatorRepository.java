package com.example.demo.repository;

import com.example.demo.entity.Violator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ViolatorRepository extends JpaRepository<Violator, Integer> {
    Page<Violator> findAll(Pageable pageable);
    
    Optional<Violator> findByEmail(String email);
    
    Optional<Violator> findByPhone(String phone);
    
    Optional<Violator> findByIdentityNumber(String identityNumber);
    
    boolean existsByEmail(String email);
    
    boolean existsByPhone(String phone);
    
    boolean existsByIdentityNumber(String identityNumber);
    
    boolean existsByEmailAndIdViolatorNot(String email, Integer idViolator);
    
    boolean existsByPhoneAndIdViolatorNot(String phone, Integer idViolator);
    
    boolean existsByIdentityNumberAndIdViolatorNot(String identityNumber, Integer idViolator);
    
    @Query("SELECT v FROM Violator v WHERE " +
           "(:name IS NULL OR v.name LIKE %:name%) AND " +
           "(:organizationType IS NULL OR v.organizationType = :organizationType)")
    Page<Violator> findByNameAndOrganizationType(
            @Param("name") String name,
            @Param("organizationType") String organizationType,
            Pageable pageable);
} 