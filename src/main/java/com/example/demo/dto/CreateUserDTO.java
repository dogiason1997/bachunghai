package com.example.demo.dto;

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
    private List<String> authorities; // Quyền của người dùng (ví dụ: USER, ADMIN)
}
