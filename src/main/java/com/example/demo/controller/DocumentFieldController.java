package com.example.demo.controller;

import com.example.demo.entity.DocumentField;
import com.example.demo.service.DocumentFieldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/document-fields")
@CrossOrigin("*")
public class DocumentFieldController {

    @Autowired
    private DocumentFieldService documentFieldService;

    @PostMapping
    public ResponseEntity<DocumentField> createDocumentField(@RequestBody DocumentField documentField) {
        DocumentField newDocumentField = documentFieldService.createDocumentField(documentField);
        return ResponseEntity.ok(newDocumentField);
    }

    @GetMapping
    public ResponseEntity<List<DocumentField>> getAllDocumentFields() {
        List<DocumentField> documentFields = documentFieldService.getAllDocumentFields();
        return ResponseEntity.ok(documentFields);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DocumentField> getDocumentFieldById(@PathVariable Integer id) {
        return documentFieldService.getDocumentFieldById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<DocumentField> updateDocumentField(@PathVariable Integer id, @RequestBody DocumentField documentField) {
        DocumentField updatedDocumentField = documentFieldService.updateDocumentField(id, documentField);
        return ResponseEntity.ok(updatedDocumentField);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDocumentField(@PathVariable Integer id) {
        documentFieldService.deleteDocumentField(id);
        return ResponseEntity.ok("Xóa DocumentField thành công");
    }
}