package com.example.demo.service;

import com.example.demo.entity.Position;
import com.example.demo.repository.PositionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class PositionService {
    @Autowired
    private PositionRepository positionRepository;

    // Create new position
    public Position createPosition(Position position) {
        if (positionRepository.findByPositionCode(position.getPositionCode()) != null) {
            throw new IllegalArgumentException("Mã phòng ban đã tồn tại.");
        }

        if (positionRepository.findByPositionName(position.getPositionName()) != null) {
            throw new IllegalArgumentException("Tên vị trí đã tồn tại.");
        }
        return positionRepository.save(position);
    }

    // Update position
    public Position updatePosition(Integer id, Position position) {
        if (positionRepository.existsById(id)) {
            Position existingPositionByCode = positionRepository.findByPositionCode(position.getPositionCode()).orElse(null);
            if (existingPositionByCode != null && !existingPositionByCode.getId().equals(id)) {
                throw new IllegalArgumentException("Mã phòng ban đã tồn tại.");
            }

            if (positionRepository.findByPositionName(position.getPositionName()) != null && !positionRepository.findByPositionName(position.getPositionName()).getId().equals(id)) {
                throw new IllegalArgumentException("Tên vị trí đã tồn tại.");
            }

            position.setId(id);
            return positionRepository.save(position);
        }
        return null;
    }

    // Delete position
    public void deletePosition(Integer id) {
        positionRepository.deleteById(id);
    }

    // Get position by ID
    public Optional<Position> getPositionById(Integer id) {
        return positionRepository.findById(id);
    }

    // Get position by position code
    public Position getPositionByCode(String positionCode) {
        return positionRepository.findByPositionCode(positionCode)
                .orElse(null);
    }

    // Get positions by user full name
    public List<Position> getPositionsByUserFullName(String fullName) {
        return positionRepository.findByUserFullName(fullName);
    }

    // Get all positions
    public List<Position> getAllPositions() {
        return positionRepository.findAll();
    }
}