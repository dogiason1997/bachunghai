package com.example.demo.mapper;

import com.example.demo.dto.UnitAgencyDTO;
import com.example.demo.entity.Department;
import com.example.demo.entity.UnitAgency;
import org.springframework.stereotype.Component;

@Component
public class UnitAgencyMapper {

    public UnitAgencyDTO toDTO(UnitAgency unitAgency) {
        if (unitAgency == null) {
            return null;
        }
        UnitAgencyDTO dto = new UnitAgencyDTO();
        dto.setIdUnit(unitAgency.getIdUnit());
        dto.setNameUnit(unitAgency.getNameUnit());
        dto.setUnitCode(unitAgency.getUnitCode());
        if (unitAgency.getDepartment() != null) {
            dto.setNameDepartment(unitAgency.getDepartment().getNameDepartment());
        }
        return dto;
    }

    public UnitAgency toEntity(UnitAgencyDTO dto, Department department) {
        if (dto == null) {
            return null;
        }
        UnitAgency unitAgency = new UnitAgency();
        unitAgency.setIdUnit(dto.getIdUnit());
        unitAgency.setNameUnit(dto.getNameUnit());
        unitAgency.setUnitCode(dto.getUnitCode());
        unitAgency.setIdPhongBan(department.getIdPhongBan()); // Liên kết với phòng ban qua ID
        return unitAgency;
    }
}