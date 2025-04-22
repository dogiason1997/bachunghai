package com.example.demo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class DepartmentDTO {

    private Integer idPhongBan; 
    
    @NotBlank(message = "Tên phòng ban không được để trống")
    @Size(max = 100, message = "Tên phòng ban tối đa 100 ký tự")
    private String nameDepartment; 

    @NotBlank(message = "Mã phòng ban không được để trống")
    @Size(max = 50, message = "Mã phòng ban tối đa 50 ký tự")
    private String departmentCode; 

    @NotBlank(message = "Mô tả chức năng không được để trống")
    @Size(max = 50, message = "Mã phòng ban tối đa 50 ký tự")
    private String describe; 
}