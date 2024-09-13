package com.example.duantotnghiep.request;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CreateKhachRequest {

    @NotBlank(message = "Không được để trống họ tên")
    @Pattern(regexp = "^[A-Za-z\\p{L}\\s]+$", message = "Họ tên chỉ chứa chữ cái, dấu cách và các ký tự tiếng Việt")
    private String hoTen;

    @NotBlank(message = "Không được để trống số điện thoại")
    @Pattern(regexp = "\\d+", message = "Số điện thoại chỉ chứa số")
    @Min(value = 1, message = "Số điện thoại phải lớn hơn 0")
    private String soDienThoai;

    @NotBlank(message = "Không được để trống email")
    @Pattern(regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$", message = "Địa chỉ email không hợp lệ")
    private String email;

    @NotBlank(message = "Không được để trống địa chỉ")
    private String diaChi;

    @NotNull(message = "Không được để trống ngày sinh")
    @Past(message = "Ngày sinh phải trước ngày hiện tại")
    private Date ngaySinh;
}
