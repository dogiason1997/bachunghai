package com.example.demo.repository;

import com.example.demo.entity.DocumentType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface DocumentTypeRepository extends JpaRepository<DocumentType, Integer> {
    @Query("SELECT dt FROM DocumentType dt WHERE dt.nameDocumentType = :name")
    List<DocumentType> findByNameDocumentType(@Param("name") String name);
}