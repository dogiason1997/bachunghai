package com.example.demo.controller;

import com.example.demo.entity.DocumentType;
import com.example.demo.service.DocumentTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/document-types")
@CrossOrigin("*")
public class DocumentTypeController {

    @Autowired
    private DocumentTypeService documentTypeService;

    @PostMapping
    public ResponseEntity<DocumentType> createDocumentType(@RequestBody DocumentType documentType) {
        DocumentType newDocumentType = documentTypeService.createDocumentType(documentType);
        return ResponseEntity.ok(newDocumentType);
    }

    @GetMapping
    public ResponseEntity<List<DocumentType>> getAllDocumentTypes() {
        List<DocumentType> documentTypes = documentTypeService.getAllDocumentTypes();
        return ResponseEntity.ok(documentTypes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DocumentType> getDocumentTypeById(@PathVariable Integer id) {
        return documentTypeService.getDocumentTypeById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<DocumentType> updateDocumentType(@PathVariable Integer id, @RequestBody DocumentType documentType) {
        DocumentType updatedDocumentType = documentTypeService.updateDocumentType(id, documentType);
        return ResponseEntity.ok(updatedDocumentType);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDocumentType(@PathVariable Integer id) {
        documentTypeService.deleteDocumentType(id);
        return ResponseEntity.ok("Xóa DocumentType thành công");
    }
}