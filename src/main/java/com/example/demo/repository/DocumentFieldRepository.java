package com.example.demo.repository;

import com.example.demo.entity.DocumentField;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DocumentFieldRepository extends JpaRepository<DocumentField, Integer> {
    Optional<DocumentField> findByNameDocumentField(String nameDocumentField);
    // List<DocumentField> findByNameDocumentField(String nameDocumentField);
}