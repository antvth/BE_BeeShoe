package com.example.duantotnghiep.repository;

import com.example.duantotnghiep.entity.GioHang;
import com.example.duantotnghiep.entity.GioHangChiTiet;
import com.example.duantotnghiep.mapper.GioHangCustom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.example.duantotnghiep.entity.GioHangChiTiet;
import com.example.duantotnghiep.mapper.GioHangCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public interface GioHangChiTietRepository extends JpaRepository<GioHangChiTiet, UUID> {

    @Query("SELECT ghct.id,spct.id, i.tenImage, sp.tenSanPham, ghct.donGia, ghct.donGiaKhiGiam, ghct.soLuong, s.size, cl.tenChatLieu,ms.tenMauSac " +
            "FROM SanPhamChiTiet spct " +
            "JOIN spct.sanPham sp " +
            "JOIN sp.listImage i " +
            "JOIN spct.size s " +
            "JOIN spct.mauSac ms " +
            "JOIN spct.chatLieu cl " +
            "JOIN spct.gioHangChiTietList ghct " +
            "JOIN ghct.gioHang gh " +
            "JOIN gh.taiKhoan tk " +
            "WHERE i.isDefault = true AND gh.trangThai = 1 AND tk.id = :id")
    Page<Object[]> loadOnGioHang(@Param("id") UUID id, Pageable pageable);

    @Query("SELECT ghct.id, i.tenImage, sp.tenSanPham, ghct.donGia, ghct.donGiaKhiGiam, ghct.soLuong, s.size, cl.tenChatLieu,ms.tenMauSac " +
            "FROM SanPhamChiTiet spct " +
            "JOIN spct.sanPham sp " +
            "JOIN sp.listImage i " +
            "JOIN spct.size s " +
            "JOIN spct.mauSac ms " +
            "JOIN spct.chatLieu cl " +
            "JOIN spct.gioHangChiTietList ghct " +
            "JOIN ghct.gioHang gh " +
            "JOIN gh.taiKhoan tk " +
            "WHERE i.isDefault = true AND gh.trangThai = 1 AND tk.id = :id")
    List<Object[]> loadOnGioHangTien(@Param("id") UUID id);

    @Query("SELECT ghct.donGiaKhiGiam, ghct.soLuong " +
            "FROM SanPhamChiTiet spct " +
            "JOIN spct.sanPham sp " +
            "JOIN sp.listImage i " +
            "JOIN spct.size s " +
            "JOIN spct.mauSac ms " +
            "JOIN spct.chatLieu cl " +
            "JOIN spct.gioHangChiTietList ghct " +
            "JOIN ghct.gioHang gh " +
            "JOIN gh.taiKhoan tk " +
            "WHERE i.isDefault = true AND gh.trangThai = 1 AND tk.id = :id")
    List<Object[]> tienThieu(@Param("id") UUID id);

    @Query("SELECT ghct.donGiaKhiGiam, ghct.soLuong " +
            "FROM SanPhamChiTiet spct " +
            "JOIN spct.sanPham sp " +
            "JOIN sp.listImage i " +
            "JOIN spct.size s " +
            "JOIN spct.mauSac ms " +
            "JOIN spct.chatLieu cl " +
            "JOIN spct.gioHangChiTietList ghct " +
            "JOIN ghct.gioHang gh " +
            "JOIN gh.taiKhoan tk " +
            "WHERE i.isDefault = true AND gh.trangThai = 1 AND tk.id = :id")
    List<Object[]> tongTien(@Param("id") UUID id);

    // Tìm mục trong giỏ hàng chi tiết dựa trên idGioHang và idSanPhamChiTiet
    GioHangChiTiet findByGioHangAndSanPhamChiTiet_Id(GioHang gioHang, UUID idSanPhamChiTiet);

    GioHangChiTiet findByGioHang(GioHang gioHang);

    List<GioHangChiTiet> findByGioHang_Id(UUID id);

}
