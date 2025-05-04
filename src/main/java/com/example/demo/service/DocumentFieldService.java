package com.example.demo.service;

import com.example.demo.entity.DocumentField;
import com.example.demo.repository.DocumentFieldRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DocumentFieldService {

    @Autowired
    private DocumentFieldRepository documentFieldRepository;

    public DocumentField createDocumentField(DocumentField documentField) {
        if (documentFieldRepository.findByNameDocumentField(documentField.getNameDocumentField()).isPresent()) {
            throw new DataIntegrityViolationException("Tên trường văn bản đã tồn tại");
        }
        return documentFieldRepository.save(documentField);
    }

    public List<DocumentField> getAllDocumentFields() {
        return documentFieldRepository.findAll();
    }

    public Optional<DocumentField> getDocumentFieldById(Integer id) {
        return documentFieldRepository.findById(id);
    }

    public DocumentField updateDocumentField(Integer id, DocumentField documentField) {
        Optional<DocumentField> existingField = documentFieldRepository.findByNameDocumentField(documentField.getNameDocumentField());
        if (existingField.isPresent() && !existingField.get().getId_documentField().equals(id)) {
            throw new DataIntegrityViolationException("Tên trường văn bản đã tồn tại");
        }
        documentField.setId_documentField(id);
        return documentFieldRepository.save(documentField);
    }

    public void deleteDocumentField(Integer id) {
        documentFieldRepository.deleteById(id);
    }
}