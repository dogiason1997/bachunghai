package com.example.demo.service;

import com.example.demo.dto.UnitAgencyDTO;
import com.example.demo.entity.Department;
import com.example.demo.entity.UnitAgency;
import com.example.demo.mapper.UnitAgencyMapper;
import com.example.demo.repository.DepartmentRepository;
import com.example.demo.repository.UnitAgencyRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UnitAgencyService {

    private final UnitAgencyRepository unitAgencyRepository;
    private final DepartmentRepository departmentRepository;
    private final UnitAgencyMapper unitAgencyMapper;

    public UnitAgencyService(UnitAgencyRepository unitAgencyRepository, DepartmentRepository departmentRepository, UnitAgencyMapper unitAgencyMapper) {
        this.unitAgencyRepository = unitAgencyRepository;
        this.departmentRepository = departmentRepository;
        this.unitAgencyMapper = unitAgencyMapper;
    }

    public List<UnitAgencyDTO> getAllUnitAgencies() {
        return unitAgencyRepository.findAll()
                .stream()
                .map(unitAgencyMapper::toDTO)
                .collect(Collectors.toList());
    }

    public UnitAgencyDTO getUnitAgencyById(Integer id) {
        return unitAgencyRepository.findById(id)
                .map(unitAgencyMapper::toDTO)
                .orElseThrow(() -> new RuntimeException("UnitAgency not found with id: " + id));
    }

    public UnitAgencyDTO createUnitAgency(UnitAgencyDTO unitAgencyDTO) {
        Department department = departmentRepository.findAll()
                .stream()
                .filter(dep -> dep.getNameDepartment().equals(unitAgencyDTO.getNameDepartment()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Không tìm thấy phòng ban"));
        UnitAgency unitAgency = unitAgencyMapper.toEntity(unitAgencyDTO, department);
        UnitAgency savedUnitAgency = unitAgencyRepository.save(unitAgency);
        return unitAgencyMapper.toDTO(savedUnitAgency);
        
    }

    public UnitAgencyDTO updateUnitAgency(Integer id, UnitAgencyDTO unitAgencyDTO) {
        Department department = departmentRepository.findAll()
                .stream()
                .filter(dep -> dep.getNameDepartment().equals(unitAgencyDTO.getNameDepartment()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Không tìm thấy phòng ban"));
        return unitAgencyRepository.findById(id).map(unitAgency -> {
            unitAgency.setNameUnit(unitAgencyDTO.getNameUnit());
            unitAgency.setUnitCode(unitAgencyDTO.getUnitCode());
            unitAgency.setIdPhongBan(department.getIdPhongBan());
            UnitAgency updatedUnitAgency = unitAgencyRepository.save(unitAgency);
            return unitAgencyMapper.toDTO(updatedUnitAgency);
        }).orElseThrow(() -> new RuntimeException("Không có đơn vị với id: " + id));
    }

    public void deleteUnitAgency(Integer id) {
        unitAgencyRepository.deleteById(id);
    }
}