package com.example.demo.mapper;

import org.springframework.stereotype.Component;

import com.example.demo.dto.DepartmentDTO;
import com.example.demo.entity.Department;

@Component
public class DepartmentMapper {
    public  DepartmentDTO toDTO(Department department) {
        if (department == null) {
            return null;
        }
        DepartmentDTO dto = new DepartmentDTO();
        dto.setIdPhongBan(department.getIdPhongBan());
        dto.setNameDepartment(department.getNameDepartment());
        dto.setDepartmentCode(department.getDepartmentCode());
        dto.setDescribe(department.getDescribe());
        return dto;
    }

    public  Department toEntity(DepartmentDTO dto) {
        if (dto == null) {
            return null;
        }
        Department department = new Department();
        department.setIdPhongBan(dto.getIdPhongBan());
        department.setNameDepartment(dto.getNameDepartment());
        department.setDepartmentCode(dto.getDepartmentCode());
        department.setDescribe(dto.getDescribe());
        return department;
    }
    
}
