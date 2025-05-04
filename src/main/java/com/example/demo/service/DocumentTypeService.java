package com.example.demo.service;

import com.example.demo.entity.DocumentType;
import com.example.demo.repository.DocumentTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DocumentTypeService {

    @Autowired
    private DocumentTypeRepository documentTypeRepository;

    public DocumentType createDocumentType(DocumentType documentType) {
        return documentTypeRepository.save(documentType);
    }

    public List<DocumentType> getAllDocumentTypes() {
        return documentTypeRepository.findAll();
    }

    public Optional<DocumentType> getDocumentTypeById(Integer id) {
        return documentTypeRepository.findById(id);
    }

    public DocumentType updateDocumentType(Integer id, DocumentType documentType) {
        documentType.setId_documentType(id);
        return documentTypeRepository.save(documentType);
    }

    public void deleteDocumentType(Integer id) {
        documentTypeRepository.deleteById(id);
    }
}