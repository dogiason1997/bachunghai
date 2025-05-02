package com.example.demo.dto;

import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserDTO {
    private String username;
    private String password;
    private String fullName;
    private String email;
    private Boolean enabled;
    private String positionName;
    private String nameDepartment;
    private LocalDate dateOfBirth;
    private List<String> authorities;
}
