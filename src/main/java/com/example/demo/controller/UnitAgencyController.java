package com.example.demo.controller;

import com.example.demo.dto.UnitAgencyDTO;
import com.example.demo.service.UnitAgencyService;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/unit-agencies")
public class UnitAgencyController {

    private final UnitAgencyService unitAgencyService;

    public UnitAgencyController(UnitAgencyService unitAgencyService) {
        this.unitAgencyService = unitAgencyService;
    }

    @GetMapping
    public ResponseEntity<List<UnitAgencyDTO>> getAllUnitAgencies() {
        return ResponseEntity.ok(unitAgencyService.getAllUnitAgencies());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UnitAgencyDTO> getUnitAgencyById(@PathVariable Integer id) {
        return ResponseEntity.ok(unitAgencyService.getUnitAgencyById(id));
    }

    @PostMapping
    public ResponseEntity<UnitAgencyDTO> createUnitAgency(@Valid @RequestBody UnitAgencyDTO unitAgencyDTO) {
        UnitAgencyDTO createdUnitAgency = unitAgencyService.createUnitAgency(unitAgencyDTO);
        return ResponseEntity.ok().header("Message", "Tạo đơn vị thành công").body(createdUnitAgency);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UnitAgencyDTO> updateUnitAgency(@PathVariable Integer id, @Valid @RequestBody UnitAgencyDTO unitAgencyDTO) {
        UnitAgencyDTO updatedUnitAgency = unitAgencyService.updateUnitAgency(id, unitAgencyDTO);
        return ResponseEntity.ok().header("Message", "Cập nhật đơn vị thành công").body(updatedUnitAgency);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUnitAgency(@PathVariable Integer id) {
        unitAgencyService.deleteUnitAgency(id);
        return ResponseEntity.ok().header("Message", "Xóa đơn vị thành công").body("Xóa đơn vị thành công");
    }
}