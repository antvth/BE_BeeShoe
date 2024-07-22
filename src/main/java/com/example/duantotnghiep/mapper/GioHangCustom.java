package com.example.duantotnghiep.mapper;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class GioHangCustom {

    UUID idGioHang;
    UUID idSanPhamChiTiet;
    String image;
    String tenSanPham;
    BigDecimal giaBan;
    BigDecimal giaGiam;
    Integer soLuong;
    Integer size;
    String chatLieu;
    String mauSac;
    BigDecimal tongTien;

    public GioHangCustom(UUID idGioHang, UUID idSanPhamChiTiet, String image, String tenSanPham, BigDecimal giaBan, BigDecimal giaGiam, Integer soLuong, Integer size, String chatLieu, String mauSac, BigDecimal tongTien) {
        this.idGioHang = idGioHang;
        this.idSanPhamChiTiet = idSanPhamChiTiet;
        this.image = image;
        this.tenSanPham = tenSanPham;
        this.giaBan = giaBan;
        this.giaGiam = giaGiam;
        this.soLuong = soLuong;
        this.size = size;
        this.chatLieu = chatLieu;
        this.mauSac = mauSac;
        this.tongTien = tongTien;
    }

    public GioHangCustom(UUID idGioHang, String image, String tenSanPham, BigDecimal giaBan, BigDecimal giaGiam, Integer soLuong, Integer size, String chatLieu, String mauSac, BigDecimal tongTien) {
        this.idGioHang = idGioHang;
        this.image = image;
        this.tenSanPham = tenSanPham;
        this.giaBan = giaBan;
        this.giaGiam = giaGiam;
        this.soLuong = soLuong;
        this.size = size;
        this.chatLieu = chatLieu;
        this.mauSac = mauSac;
        this.tongTien = tongTien;
    }
}
