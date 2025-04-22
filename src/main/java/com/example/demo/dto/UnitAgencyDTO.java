package com.example.demo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UnitAgencyDTO {

    private Integer idUnit; 
    @NotBlank(message = "Tên đơn vị không được để trống")
    @Size(max = 100, message = "Tên đơn vị tối đa 100 ký tự")
    private String nameUnit;

    @NotBlank(message = "Mã đơn vị không được để trống")
    @Size(max = 50, message = "Mã đơn vị tối đa 50 ký tự")
    private String unitCode; 

    @NotBlank(message = "Tên phòng ban không được để trống")
    private String nameDepartment; 
}