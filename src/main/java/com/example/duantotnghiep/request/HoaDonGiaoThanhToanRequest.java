package com.example.duantotnghiep.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class HoaDonGiaoThanhToanRequest {

    private UUID idHoaDon;

    @NotBlank(message = "Họ và tên không được để trống")
    private String tenKhach;

    @NotBlank(message = "Số điện thoại không được để trống")
    private String soDienThoai;

    @NotBlank(message = "Địa chỉ không được để trống")
    private String diaChi;

    @NotBlank(message = "Email không được để trống")
    @Pattern(regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$", message = "Địa chỉ email không hợp lệ")
    private String email;

    @NotNull(message = "Tiền vận chuyển không được để trống")
    private BigDecimal tienGiao;

    private BigDecimal tongTien;

    private BigDecimal tienKhachTra;

    private BigDecimal tienThua;

    private List<UUID> gioHangChiTietList;
}
