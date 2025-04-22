package com.example.demo.repository;

import com.example.demo.entity.UnitAgency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UnitAgencyRepository extends JpaRepository<UnitAgency, Integer> {
}
