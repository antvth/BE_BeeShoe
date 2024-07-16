package com.example.duantotnghiep.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ThongTinDonHang {

    String ma;

    Integer trangThai;

    String tenLoaiDon;

    String diaChi;

    String tenNguoiNhan;

    String sdtNguoiNhan;

    String tenNguoiShip;

    String sdtNguoiShip;

    UUID idNhanVien;

    String email;
}
