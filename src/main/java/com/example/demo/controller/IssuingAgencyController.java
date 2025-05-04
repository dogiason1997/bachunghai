package com.example.demo.controller;

import com.example.demo.entity.IssuingAgency;
import com.example.demo.service.IssuingAgencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/issuing-agencies")
@CrossOrigin("*")
public class IssuingAgencyController {

    @Autowired
    private IssuingAgencyService issuingAgencyService;

    @PostMapping
    public ResponseEntity<IssuingAgency> createIssuingAgency(@RequestBody IssuingAgency issuingAgency) {
        IssuingAgency newIssuingAgency = issuingAgencyService.createIssuingAgency(issuingAgency);
        return ResponseEntity.ok(newIssuingAgency);
    }

    @GetMapping
    public ResponseEntity<List<IssuingAgency>> getAllIssuingAgencies() {
        List<IssuingAgency> issuingAgencies = issuingAgencyService.getAllIssuingAgencies();
        return ResponseEntity.ok(issuingAgencies);
    }

    @GetMapping("/{id}")
    public ResponseEntity<IssuingAgency> getIssuingAgencyById(@PathVariable Integer id) {
        return issuingAgencyService.getIssuingAgencyById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<IssuingAgency> updateIssuingAgency(@PathVariable Integer id, @RequestBody IssuingAgency issuingAgency) {
        IssuingAgency updatedIssuingAgency = issuingAgencyService.updateIssuingAgency(id, issuingAgency);
        return ResponseEntity.ok(updatedIssuingAgency);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteIssuingAgency(@PathVariable Integer id) {
        issuingAgencyService.deleteIssuingAgency(id);
        return ResponseEntity.ok("Xóa IssuingAgency thành công");
    }
}