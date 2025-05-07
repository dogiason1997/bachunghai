package com.example.demo.controller;

import com.example.demo.dto.DocumentDTO;
import com.example.demo.dto.DocumentSearchDTO;
import com.example.demo.entity.Document;
import com.example.demo.service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/documents")
public class DocumentController {

    @Autowired
    private DocumentService documentService;

    @PostMapping
    public ResponseEntity<?> createDocument(
            @ModelAttribute DocumentDTO documentDTO,
            @RequestParam Integer userId
    ) {
        try {
            Document document = documentService.createDocument(documentDTO, userId);
            return new ResponseEntity<>(document, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getDocument(@PathVariable Integer id) {
        try {
            Document document = documentService.getDocument(id);
            return new ResponseEntity<>(document, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public ResponseEntity<List<Document>> getAllDocuments() {
        List<Document> documents = documentService.getAllDocuments();
        return new ResponseEntity<>(documents, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateDocument(
            @PathVariable Integer id,
            @ModelAttribute DocumentDTO documentDTO,
            @RequestParam Integer userId
    ) {
        try {
            Document document = documentService.updateDocument(id, documentDTO, userId);
            return new ResponseEntity<>(document, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteDocument(@PathVariable Integer id) {
        try {
            documentService.deleteDocument(id);
            return new ResponseEntity<>("Document deleted successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/search-fillter")
    public ResponseEntity<?> searchDocuments(@RequestBody DocumentSearchDTO searchDTO) {
        try {
            List<Document> documents = documentService.searchDocuments(searchDTO);
            return new ResponseEntity<>(documents, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
} 