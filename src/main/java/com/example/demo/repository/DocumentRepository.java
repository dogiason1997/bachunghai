package com.example.demo.repository;

import com.example.demo.entity.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Integer> {

@Modifying
@Query("DELETE FROM FilesSave f WHERE f.document.id = :documentId")
void deleteByDocumentId(@Param("documentId") Integer documentId);
} 