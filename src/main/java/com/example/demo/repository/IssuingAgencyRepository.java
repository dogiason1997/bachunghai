package com.example.demo.repository;

import com.example.demo.entity.IssuingAgency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface IssuingAgencyRepository extends JpaRepository<IssuingAgency, Integer> {
    @Query("SELECT ia FROM IssuingAgency ia WHERE ia.nameIssuingAgency = :name")
    List<IssuingAgency> findByNameIssuingAgency(@Param("name") String name);
}