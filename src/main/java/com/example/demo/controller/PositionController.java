package com.example.demo.controller;

import com.example.demo.entity.Position;
import com.example.demo.service.PositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/positions")
@CrossOrigin("*")
public class PositionController {

    @Autowired
    private PositionService positionService;
 
    @PostMapping
    public ResponseEntity<Position> createPosition(@RequestBody Position position) {
        Position newPosition = positionService.createPosition(position);
        return ResponseEntity.ok(newPosition);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Position> updatePosition(@PathVariable Integer id, @RequestBody Position position) {
        Position updatedPosition = positionService.updatePosition(id, position);
        if (updatedPosition != null) {
            return ResponseEntity.ok(updatedPosition);
        }
        return ResponseEntity.notFound().build();
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePosition(@PathVariable Integer id) {
        positionService.deletePosition(id);
        return ResponseEntity.ok().body("Xóa vị trí thành công");
    }

    @GetMapping("/{id}")
    public ResponseEntity<Position> getPositionById(@PathVariable Integer id) {
        return positionService.getPositionById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/code/{positionCode}")
    public ResponseEntity<Position> getPositionByCode(@PathVariable String positionCode) {
        Position position = positionService.getPositionByCode(positionCode);
        if (position != null) {
            return ResponseEntity.ok(position);
        }
        return ResponseEntity.notFound().build();
    }

    // Get positions by user full name
    @GetMapping("/user")
    public ResponseEntity<List<Position>> getPositionsByUserFullName(@RequestParam String fullName) {
        List<Position> positions = positionService.getPositionsByUserFullName(fullName);
        return ResponseEntity.ok(positions);
    }

    // Get all positions
    @GetMapping
    public ResponseEntity<List<Position>> getAllPositions() {
        List<Position> positions = positionService.getAllPositions();
        return ResponseEntity.ok(positions);
    }
} 