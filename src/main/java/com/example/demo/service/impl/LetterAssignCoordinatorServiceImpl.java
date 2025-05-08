package com.example.demo.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dto.LetterAssignCoordinatorDTO;
import com.example.demo.entity.LetterAssign;
import com.example.demo.entity.LetterAssignCoordinators;
import com.example.demo.entity.Users;
import com.example.demo.repository.LetterAssignCoordinatorRepository;
import com.example.demo.repository.LetterAssignRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.LetterAssignCoordinatorService;

import jakarta.persistence.EntityNotFoundException;

@Service
@Transactional
public class LetterAssignCoordinatorServiceImpl implements LetterAssignCoordinatorService {

    @Autowired
    private LetterAssignCoordinatorRepository coordinatorRepository;
    
    @Autowired
    private LetterAssignRepository letterAssignRepository;
    
    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public List<LetterAssignCoordinatorDTO> getAllCoordinators() {
        return coordinatorRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public LetterAssignCoordinatorDTO getCoordinatorById(Integer id) {
        LetterAssignCoordinators coordinator = coordinatorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Coordinator not found with ID: " + id));
        return convertToDTO(coordinator);
    }

    @Override
    @Transactional(readOnly = true)
    public List<LetterAssignCoordinatorDTO> getCoordinatorsByLetterAssignId(Integer letterAssignId) {
        if (!letterAssignRepository.existsById(letterAssignId)) {
            throw new EntityNotFoundException("Letter assign not found with ID: " + letterAssignId);
        }
        
        return coordinatorRepository.findByLetterAssign_IdLetterAssign(letterAssignId)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<LetterAssignCoordinatorDTO> getLetterAssignsByCoordinatorId(Integer userId) {
        if (!userRepository.existsById(userId)) {
            throw new EntityNotFoundException("User not found with ID: " + userId);
        }
        
        return coordinatorRepository.findByCoordinatorId(userId)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public LetterAssignCoordinatorDTO addCoordinator(LetterAssignCoordinatorDTO coordinatorDTO) {
        // Lấy thông tin công văn được giao
        LetterAssign letterAssign = letterAssignRepository.findById(coordinatorDTO.getLetterAssignId())
                .orElseThrow(() -> new EntityNotFoundException("Letter assign not found with ID: " + coordinatorDTO.getLetterAssignId()));

        // Lấy thông tin người dùng được phân công phối hợp
        Users coordinator;
        if (coordinatorDTO.getCoordinatorId() != null) {
            coordinator = userRepository.findById(coordinatorDTO.getCoordinatorId())
                    .orElseThrow(() -> new EntityNotFoundException("User not found with ID: " + coordinatorDTO.getCoordinatorId()));
        } else if (coordinatorDTO.getCoordinatorName() != null && !coordinatorDTO.getCoordinatorName().isEmpty()) {
            coordinator = userRepository.findByFullName(coordinatorDTO.getCoordinatorName())
                    .orElseThrow(() -> new EntityNotFoundException("User not found with name: " + coordinatorDTO.getCoordinatorName()));
        } else {
            throw new IllegalArgumentException("Coordinator ID or name must be provided");
        }
        
        // Kiểm tra xem người dùng đã được phân công phối hợp cho công văn này chưa
        if (coordinatorRepository.existsByLetterAssign_IdLetterAssignAndCoordinator_IdUser(
                letterAssign.getIdLetterAssign(), coordinator.getIdUser())) {
            throw new IllegalStateException("This user is already a coordinator for this letter assignment");
        }
        
        // Tạo và lưu thông tin người phối hợp
        LetterAssignCoordinators newCoordinator = new LetterAssignCoordinators();
        newCoordinator.setLetterAssign(letterAssign);
        newCoordinator.setCoordinator(coordinator);
        
        LetterAssignCoordinators savedCoordinator = coordinatorRepository.save(newCoordinator);
        return convertToDTO(savedCoordinator);
    }

    @Override
    public List<LetterAssignCoordinatorDTO> addCoordinators(Integer letterAssignId, List<Integer> coordinatorIds) {
        // Lấy thông tin công văn được giao
        LetterAssign letterAssign = letterAssignRepository.findById(letterAssignId)
                .orElseThrow(() -> new EntityNotFoundException("Letter assign not found with ID: " + letterAssignId));
        
        List<LetterAssignCoordinatorDTO> results = new ArrayList<>();
        
        for (Integer coordinatorId : coordinatorIds) {
            // Bỏ qua nếu người dùng đã được phân công
            if (coordinatorRepository.existsByLetterAssign_IdLetterAssignAndCoordinator_IdUser(
                    letterAssignId, coordinatorId)) {
                continue;
            }
            
            // Lấy thông tin người dùng
            Users coordinator = userRepository.findById(coordinatorId)
                    .orElseThrow(() -> new EntityNotFoundException("User not found with ID: " + coordinatorId));
            
            // Tạo và lưu thông tin người phối hợp
            LetterAssignCoordinators newCoordinator = new LetterAssignCoordinators();
            newCoordinator.setLetterAssign(letterAssign);
            newCoordinator.setCoordinator(coordinator);
            
            LetterAssignCoordinators savedCoordinator = coordinatorRepository.save(newCoordinator);
            results.add(convertToDTO(savedCoordinator));
        }
        
        return results;
    }

    @Override
    public LetterAssignCoordinatorDTO updateCoordinator(Integer id, LetterAssignCoordinatorDTO coordinatorDTO) {
        // Kiểm tra xem bản ghi có tồn tại không
        LetterAssignCoordinators coordinator = coordinatorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Coordinator not found with ID: " + id));
        
        // Có thể cập nhật người phối hợp nếu cần
        if (coordinatorDTO.getCoordinatorId() != null && 
                !coordinator.getCoordinator().getIdUser().equals(coordinatorDTO.getCoordinatorId())) {
            Users newCoordinator = userRepository.findById(coordinatorDTO.getCoordinatorId())
                    .orElseThrow(() -> new EntityNotFoundException("User not found with ID: " + coordinatorDTO.getCoordinatorId()));
            coordinator.setCoordinator(newCoordinator);
        } else if (coordinatorDTO.getCoordinatorName() != null && !coordinatorDTO.getCoordinatorName().isEmpty() && 
                !coordinator.getCoordinator().getFullName().equals(coordinatorDTO.getCoordinatorName())) {
            Users newCoordinator = userRepository.findByFullName(coordinatorDTO.getCoordinatorName())
                    .orElseThrow(() -> new EntityNotFoundException("User not found with name: " + coordinatorDTO.getCoordinatorName()));
            coordinator.setCoordinator(newCoordinator);
        }
        
        // Có thể cập nhật công văn được giao nếu cần
        if (coordinatorDTO.getLetterAssignId() != null && 
                !coordinator.getLetterAssign().getIdLetterAssign().equals(coordinatorDTO.getLetterAssignId())) {
            LetterAssign newLetterAssign = letterAssignRepository.findById(coordinatorDTO.getLetterAssignId())
                    .orElseThrow(() -> new EntityNotFoundException("Letter assign not found with ID: " + coordinatorDTO.getLetterAssignId()));
            coordinator.setLetterAssign(newLetterAssign);
        }
        
        LetterAssignCoordinators updatedCoordinator = coordinatorRepository.save(coordinator);
        return convertToDTO(updatedCoordinator);
    }

    @Override
    public void deleteCoordinator(Integer id) {
        if (!coordinatorRepository.existsById(id)) {
            throw new EntityNotFoundException("Coordinator not found with ID: " + id);
        }
        coordinatorRepository.deleteById(id);
    }

    @Override
    public void removeCoordinatorFromLetterAssign(Integer letterAssignId, Integer coordinatorId) {
        if (!letterAssignRepository.existsById(letterAssignId)) {
            throw new EntityNotFoundException("Letter assign not found with ID: " + letterAssignId);
        }
        
        if (!userRepository.existsById(coordinatorId)) {
            throw new EntityNotFoundException("User not found with ID: " + coordinatorId);
        }
        
        coordinatorRepository.deleteByLetterAssign_IdLetterAssignAndCoordinator_IdUser(letterAssignId, coordinatorId);
    }

    @Override
    public void removeAllCoordinatorsFromLetterAssign(Integer letterAssignId) {
        if (!letterAssignRepository.existsById(letterAssignId)) {
            throw new EntityNotFoundException("Letter assign not found with ID: " + letterAssignId);
        }
        
        List<LetterAssignCoordinators> coordinators = coordinatorRepository.findByLetterAssign_IdLetterAssign(letterAssignId);
        coordinatorRepository.deleteAll(coordinators);
    }
    
    // Chuyển đổi entity thành DTO
    private LetterAssignCoordinatorDTO convertToDTO(LetterAssignCoordinators coordinator) {
        LetterAssignCoordinatorDTO dto = new LetterAssignCoordinatorDTO();
        dto.setIdCoordinator(coordinator.getIdCoordinator());
        
        if (coordinator.getLetterAssign() != null) {
            dto.setLetterAssignId(coordinator.getLetterAssign().getIdLetterAssign());
            if (coordinator.getLetterAssign().getLetter() != null) {
                dto.setLetterTitle(coordinator.getLetterAssign().getLetter().getLetterTitle());
            }
        }
        
        if (coordinator.getCoordinator() != null) {
            dto.setCoordinatorId(coordinator.getCoordinator().getIdUser());
            dto.setCoordinatorName(coordinator.getCoordinator().getFullName());
        }
        
        return dto;
    }
} 