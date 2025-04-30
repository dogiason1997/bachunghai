package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.demo.dto.DepartmentDTO;
import com.example.demo.entity.Department;
import com.example.demo.mapper.DepartmentMapper;
import com.example.demo.repository.DepartmentRepository;

@Service
public class DepartmentService {
    private final DepartmentRepository departmentRepository;
    private final DepartmentMapper departmentMapper;

    public DepartmentService(DepartmentRepository departmentRepository, DepartmentMapper departmentMapper) {
        this.departmentRepository = departmentRepository;
        this.departmentMapper = departmentMapper;
    }

    public List<DepartmentDTO> getAllDepartments() {
        return departmentRepository.findAll().stream().map(departmentMapper::toDTO) // Entity sang DTO
                .collect(Collectors.toList());
        // List<Department> departmentList = departmentRepository.findAll();
        // List<DepartmentDTO> dtoList = new ArrayList<>();
        // for(Department dept : departmentList){
        //     DepartmentDTO dto = departmentMapper.toDTO(dept);
        //     dtoList.add(dto);
        // }
        // return dtoList;
    }

    public Page<DepartmentDTO> getAllDepartmentsPaged(Pageable pageable) {
        return departmentRepository.findAll(pageable).map(departmentMapper::toDTO);
    }

    public DepartmentDTO getDepartmentById(Integer id) {
        return departmentRepository.findById(id)
                .map(departmentMapper::toDTO) //Entity sang DTO
                .orElseThrow(() -> new RuntimeException("Department not found with id: " + id));
        // Department dept = departmentRepository.findById(id).orElseThrow(() -> new RuntimeException("Department not found with id: " + id));
        // DepartmentDTO dto = departmentMapper.toDTO(dept);
        // return dto;


    }

    public DepartmentDTO createDepartment(DepartmentDTO departmentDTO) {
        Department department = departmentMapper.toEntity(departmentDTO); // DTO sang Entity
        Department savedDepartment = departmentRepository.save(department);
        DepartmentDTO responseDTO = departmentMapper.toDTO(savedDepartment); // Entity sang DTO
        return responseDTO; 
    }

    public DepartmentDTO updateDepartment(Integer id, DepartmentDTO departmentDTO) {
        return departmentRepository.findById(id).map(department -> {
            department.setNameDepartment(departmentDTO.getNameDepartment());
            department.setDepartmentCode(departmentDTO.getDepartmentCode());
            department.setDescribe(departmentDTO.getDescribe());
            Department updatedDepartment = departmentRepository.save(department);
            return departmentMapper.toDTO(updatedDepartment); // Chuyển từ Entity sang DTO
        }).orElseThrow(() -> new RuntimeException("Department not found with id: " + id));
    }

    public void deleteDepartment(Integer id) {
        departmentRepository.deleteById(id);
    }
    
}
