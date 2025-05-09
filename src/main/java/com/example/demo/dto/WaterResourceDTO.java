package com.example.demo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Digits;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class WaterResourceDTO {
    // private Integer idResource;
    @NotBlank(message = "Tên nguồn nước không được để trống")
    @Size(max = 200, message = "Tên nguồn nước không được vượt quá 200 ký tự")
    private String resourceName;
    
    @NotBlank(message = "Loại nguồn nước không được để trống")
    @Size(max = 100, message = "Loại nguồn nước không được vượt quá 100 ký tự")
    private String resourceType;
    
    @NotBlank(message = "Vị trí không được để trống")
    @Size(max = 500, message = "Vị trí không được vượt quá 500 ký tự")
    private String location;
    
    @NotNull(message = "Vĩ độ không được để trống")
    @Digits(integer = 3, fraction = 6, message = "Vĩ độ phải có định dạng hợp lệ (tối đa 3 chữ số nguyên và 6 chữ số thập phân)")
    private BigDecimal latitude;
    
    @NotNull(message = "Kinh độ không được để trống")
    @Digits(integer = 3, fraction = 6, message = "Kinh độ phải có định dạng hợp lệ (tối đa 3 chữ số nguyên và 6 chữ số thập phân)")
    private BigDecimal longitude;
    
    @NotBlank(message = "Phòng ban quản lý không được để trống")
    private String nameDepartmentManagement;
    // private String description;
} 