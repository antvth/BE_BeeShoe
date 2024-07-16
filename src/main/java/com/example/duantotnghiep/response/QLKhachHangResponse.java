package com.example.duantotnghiep.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class QLKhachHangResponse {

    private UUID id;

    private String ten;

    private String email;

    private String soDienThoai;

    private String image;

    private Boolean gioiTinh;

    private String maTaiKhoan;

    private String userName;

    private String matKhau;

    private Date ngaySinh;

    private Integer trangThai;

    private String diaChi;
}
