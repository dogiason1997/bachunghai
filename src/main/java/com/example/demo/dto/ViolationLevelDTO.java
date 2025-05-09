package com.example.demo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
public class ViolationLevelDTO {
    @NotBlank(message = "Tên mức độ vi phạm không được để trống")
    @Size(max = 50, message = "Tên mức độ vi phạm không được vượt quá 50 ký tự")
    private String levelName;
    
    @Size(max = 255, message = "Mô tả không được vượt quá 255 ký tự")
    private String description;
    
    @Size(max = 100, message = "Phạm vi phạt không được vượt quá 100 ký tự")
    private String penaltyRange;
    
    @NotNull(message = "Điểm mức độ nghiêm trọng không được để trống")
    @Min(value = 1, message = "Điểm mức độ nghiêm trọng phải lớn hơn hoặc bằng 1")
    private Integer severityScore;
} 