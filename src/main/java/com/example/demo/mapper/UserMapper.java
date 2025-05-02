package com.example.demo.mapper;

import com.example.demo.dto.CreateUserDTO;
import com.example.demo.entity.Users;
import com.example.demo.entity.Position;
import com.example.demo.entity.Department;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.ArrayList;

@Component
public class UserMapper {
    
    public Users toEntity(CreateUserDTO dto, Position position, Department department) {
        Users user = new Users();
        user.setUsername(dto.getUsername());
        user.setPassword(dto.getPassword());
        user.setFullName(dto.getFullName());
        user.setEmail(dto.getEmail());
        user.setEnabled(dto.getEnabled() != null ? dto.getEnabled() : true);
        user.setPosition(position);
        user.setDepartment(department);
        user.setDateOfBirth(dto.getDateOfBirth());
        if (position != null) {
            user.setIdPosition(position.getId());
        }
        if (department != null) {
            user.setIdPhongBan(department.getIdPhongBan());
        }

        return user;
    }

    public CreateUserDTO toDto(Users user) {
        if (user == null) {
            return null;
        }
        List<String> authorities = user.getAuthorities() != null ?
            user.getAuthorities().stream()
                .map(auth -> auth.getAuthority())
                .collect(java.util.stream.Collectors.toList()) :
            new ArrayList<>();
            
        return new CreateUserDTO(
            user.getUsername(),
            user.getPassword(),
            user.getFullName(),
            user.getEmail(),
            user.getEnabled(),
            user.getPosition() != null ? user.getPosition().getPositionName() : null,
            user.getDepartment() != null ? user.getDepartment().getNameDepartment() : null,
            user.getDateOfBirth(),
            authorities
        );
    }
}