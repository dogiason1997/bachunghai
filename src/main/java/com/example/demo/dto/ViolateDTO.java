package com.example.demo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class ViolateDTO {
    @NotBlank(message = "Địa điểm không được để trống")
    @Size(max = 200, message = "Địa điểm không được vượt quá 200 ký tự")
    private String locations;
    
    @NotNull(message = "Ngày phát hiện không được để trống")
    @PastOrPresent(message = "Ngày phát hiện phải là ngày hiện tại hoặc trong quá khứ")
    private LocalDate dateDiscovery;
    
    @Digits(integer = 3, fraction = 6, message = "Vĩ độ phải có định dạng hợp lệ (tối đa 3 chữ số nguyên và 6 chữ số thập phân)")
    private BigDecimal latitude;
    
    @Digits(integer = 3, fraction = 6, message = "Kinh độ phải có định dạng hợp lệ (tối đa 3 chữ số nguyên và 6 chữ số thập phân)")
    private BigDecimal longitude;
    
    @Size(max = 200, message = "Tổ chức không được vượt quá 200 ký tự")
    private String organize;
    
    @Size(max = 100, message = "Mô tả vi phạm không được vượt quá 100 ký tự")
    private String describeViolation;
    
    @NotNull(message = "ID đối tượng vi phạm không được để trống")
    private Integer idViolator;
    
    @NotBlank(message = "Tên mức độ vi phạm không được để trống")
    private String levelName;
    
    @NotBlank(message = "Tên nguồn nước không được để trống")
    private String resourceName;
} 