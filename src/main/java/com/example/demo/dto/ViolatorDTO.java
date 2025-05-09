package com.example.demo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class ViolatorDTO {
    @NotBlank(message = "Tên không được để trống")
    @Size(max = 200, message = "Tên không được vượt quá 200 ký tự")
    private String name;
    
    @Size(max = 500, message = "Địa chỉ không được vượt quá 500 ký tự")
    private String address;
    
    @Pattern(regexp = "^(\\+?84|0)[3|5|7|8|9][0-9]{8}$", message = "Số điện thoại không hợp lệ. Vui lòng nhập đúng định dạng số điện thoại Việt Nam")
    private String phone;
    
    @Email(message = "Email không hợp lệ")
    @Size(max = 100, message = "Email không được vượt quá 100 ký tự")
    private String email;
    
    @Size(max = 50, message = "Số giấy tờ tùy thân không được vượt quá 50 ký tự")
    private String identityNumber;
    
    @Size(max = 50, message = "Loại tổ chức không được vượt quá 50 ký tự")
    private String organizationType;
    
    @Size(max = 100, message = "Người đại diện không được vượt quá 100 ký tự")
    private String representative;
} 