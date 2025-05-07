package com.example.demo.repository;

import com.example.demo.entity.Letter;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface LetterRepository extends JpaRepository<Letter, Integer>, JpaSpecificationExecutor<Letter> {
    Page<Letter> findAll(Pageable pageable);
    // Optional<Letter> findByLetterTitle(String letterTitle);

    @Query("SELECT l FROM Letter l WHERE l.LetterTitle = :letterTitle")
    Optional<Letter> findByLetterTitle(@Param("letterTitle") String letterTitle);
} 