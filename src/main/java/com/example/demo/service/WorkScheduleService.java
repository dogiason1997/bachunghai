package com.example.demo.service;

import com.example.demo.dto.WorkScheduleDTO;
import com.example.demo.entity.Users;
import com.example.demo.entity.WorkSchedule;
import com.example.demo.repository.UsersRepository;
import com.example.demo.repository.WorkScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class WorkScheduleService {

    private final WorkScheduleRepository workScheduleRepository;
    private final UsersRepository usersRepository;

    @Autowired
    public WorkScheduleService(WorkScheduleRepository workScheduleRepository, UsersRepository usersRepository) {
        this.workScheduleRepository = workScheduleRepository;
        this.usersRepository = usersRepository;
    }

    // Convert Entity to DTO
    private WorkScheduleDTO convertToDTO(WorkSchedule workSchedule) {
        WorkScheduleDTO dto = new WorkScheduleDTO();
        // dto.setId(workSchedule.getIdWorkSchedule());
        dto.setDateWork(workSchedule.getDateWork());
        dto.setHourStart(workSchedule.getHourStart());
        dto.setHourEnd(workSchedule.getHourEnd());
        dto.setContentWork(workSchedule.getContentWork());
        dto.setLocations(workSchedule.getLocations());
        dto.setUserId(workSchedule.getIdUser());
        
        // Lấy tên người tạo lịch
        // if (workSchedule.getUser() != null) {
        //     dto.setCreatorName(workSchedule.getUser().getFullName());
        // }
        
        return dto;
    }

    // Convert DTO to Entity
    private WorkSchedule convertToEntity(WorkScheduleDTO dto) {
        WorkSchedule workSchedule = new WorkSchedule();
        workSchedule.setDateWork(dto.getDateWork());
        workSchedule.setHourStart(dto.getHourStart());
        workSchedule.setHourEnd(dto.getHourEnd());
        workSchedule.setContentWork(dto.getContentWork());
        workSchedule.setLocations(dto.getLocations());
        workSchedule.setIdUser(dto.getUserId());
        
        return workSchedule;
    }

    // CRUD Operations
    
    // Create
    @Transactional
    public WorkScheduleDTO createWorkSchedule(WorkScheduleDTO workScheduleDTO) {
        // Kiểm tra người dùng tồn tại
        Users user = usersRepository.findById(workScheduleDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found with id: " + workScheduleDTO.getUserId()));
        
        WorkSchedule workSchedule = convertToEntity(workScheduleDTO);
        WorkSchedule savedWorkSchedule = workScheduleRepository.save(workSchedule);
        
        // Đảm bảo có thông tin user khi trả về
        savedWorkSchedule.setUser(user);
        
        return convertToDTO(savedWorkSchedule);
    }
    
    // Read
    public Page<WorkScheduleDTO> getAllWorkSchedules(Pageable pageable) {
        Page<WorkSchedule> workSchedulesPage = workScheduleRepository.findAll(pageable);
        return workSchedulesPage.map(this::convertToDTO);
    }
    
    public Page<WorkScheduleDTO> getWorkSchedulesByUserId(Integer userId, Pageable pageable) {
        Page<WorkSchedule> workSchedulesPage = workScheduleRepository.findByIdUser(userId, pageable);
        return workSchedulesPage.map(this::convertToDTO);
    }
    
    public Page<WorkScheduleDTO> getWorkSchedulesByDateRange(LocalDate startDate, LocalDate endDate, Pageable pageable) {
        Page<WorkSchedule> workSchedulesPage = workScheduleRepository.findByDateWorkBetween(startDate, endDate, pageable);
        return workSchedulesPage.map(this::convertToDTO);
    }
    
    public Page<WorkScheduleDTO> getWorkSchedulesByUserIdAndDateRange(Integer userId, LocalDate startDate, LocalDate endDate, Pageable pageable) {
        Page<WorkSchedule> workSchedulesPage = workScheduleRepository.findByIdUserAndDateWorkBetween(userId, startDate, endDate, pageable);
        return workSchedulesPage.map(this::convertToDTO);
    }
    
    public WorkScheduleDTO getWorkScheduleById(Integer id) {
        WorkSchedule workSchedule = workScheduleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("WorkSchedule not found with id: " + id));
        return convertToDTO(workSchedule);
    }
    
    // Update
    @Transactional
    public WorkScheduleDTO updateWorkSchedule(Integer id, WorkScheduleDTO workScheduleDTO) {
        // Kiểm tra lịch tồn tại
        WorkSchedule existingWorkSchedule = workScheduleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("WorkSchedule not found with id: " + id));
        
        // Kiểm tra người dùng tồn tại
        Users user = usersRepository.findById(workScheduleDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found with id: " + workScheduleDTO.getUserId()));
        
        existingWorkSchedule.setDateWork(workScheduleDTO.getDateWork());
        existingWorkSchedule.setHourStart(workScheduleDTO.getHourStart());
        existingWorkSchedule.setHourEnd(workScheduleDTO.getHourEnd());
        existingWorkSchedule.setContentWork(workScheduleDTO.getContentWork());
        existingWorkSchedule.setLocations(workScheduleDTO.getLocations());
        existingWorkSchedule.setIdUser(workScheduleDTO.getUserId());
        
        WorkSchedule updatedWorkSchedule = workScheduleRepository.save(existingWorkSchedule);
        
        // Đảm bảo có thông tin user khi trả về
        updatedWorkSchedule.setUser(user);
        
        return convertToDTO(updatedWorkSchedule);
    }
    
    // Delete
    @Transactional
    public void deleteWorkSchedule(Integer id) {
        if (workScheduleRepository.existsById(id)) {
            workScheduleRepository.deleteById(id);
        } else {
            throw new RuntimeException("WorkSchedule not found with id: " + id);
        }
    }
    
    // Phương thức bổ sung - Lấy tất cả không phân trang (hạn chế số lượng nếu cần)
    public List<WorkScheduleDTO> getAllWorkSchedulesByUser(Integer userId) {
        List<WorkSchedule> workSchedules = workScheduleRepository.findByIdUser(userId);
        return workSchedules.stream().map(this::convertToDTO).collect(Collectors.toList());
    }
} 