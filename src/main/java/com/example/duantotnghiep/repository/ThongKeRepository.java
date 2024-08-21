package com.example.duantotnghiep.repository;

import com.example.duantotnghiep.entity.HoaDon;
import com.example.duantotnghiep.response.DoanhThuResponse;
import com.example.duantotnghiep.response.SanPhamBanChayResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Repository
public interface ThongKeRepository extends JpaRepository<HoaDon, UUID> {

    @Query(value = "SELECT CONVERT(date, CONVERT(date, hoadon.ngaycapnhap)) AS Ngay, SUM(hoadonchitiet.soluong * hoadonchitiet.dongiasaugiam) AS DoanhThu\n" +
            "FROM dbo.hoadon \n" +
            "INNER JOIN dbo.hoadonchitiet ON dbo.hoadon.id = dbo.hoadonchitiet.idhoadon\n" +
            "WHERE hoadon.trangthai = 5 AND CONVERT(date, hoadon.ngaycapnhap) BETWEEN ? AND ?\n" +
            "GROUP BY CONVERT(date, CONVERT(date, hoadon.ngaycapnhap))\n" +
            "ORDER BY Ngay;", nativeQuery = true)
    List<DoanhThuResponse> doanhThu(Date ngayBd,Date ngayKt);

    @Query("SELECT new com.example.duantotnghiep.response.SanPhamBanChayResponse(i.tenImage, sp.tenSanPham, hdct.donGia, hdct.donGiaSauGiam, SUM(hdct.soLuong)) " +
            "FROM HoaDonChiTiet hdct " +
            "JOIN hdct.sanPhamChiTiet spct " +
            "JOIN spct.sanPham sp " +
            "JOIN sp.listImage i WHERE i.isDefault = true GROUP BY i.tenImage, sp.tenSanPham, hdct.donGia, hdct.donGiaSauGiam")
    List<SanPhamBanChayResponse> sanPhamBanChay();

    @Query(value = "select SUM(hoadonchitiet.dongiasaugiam * hoadonchitiet.soluong) from\n" +
            "dbo.hoadon INNER JOIN dbo.hoadonchitiet\n" +
            "ON dbo.hoadon.id = dbo.hoadonchitiet.idhoadon\n" +
            "where hoadon.trangthai = 5 AND CONVERT(date, hoadon.ngaycapnhap) = CONVERT(DATE, GETDATE())", nativeQuery = true)
    BigDecimal tongTienDonHomNay();

    @Query(value = "select SUM(hoadonchitiet.dongiasaugiam * hoadonchitiet.soluong) from\n" +
            "dbo.hoadon INNER JOIN dbo.hoadonchitiet\n" +
            "ON dbo.hoadon.id = dbo.hoadonchitiet.idhoadon\n" +
            "where hoadon.trangthai = 5 AND DATEPART(WEEK, hoadon.ngaycapnhap) = DATEPART(WEEK, GETDATE()) AND YEAR(hoadon.ngaycapnhap) = YEAR(GETDATE());\n", nativeQuery = true)
    BigDecimal tongTienDonTuanNay();

    @Query(value = "select SUM(hoadonchitiet.dongiasaugiam * hoadonchitiet.soluong) from\n" +
            "dbo.hoadon INNER JOIN dbo.hoadonchitiet\n" +
            "ON dbo.hoadon.id = dbo.hoadonchitiet.idhoadon\n" +
            "where hoadon.trangthai = 5 AND MONTH(hoadon.ngaycapnhap) = MONTH(GETDATE()) AND YEAR(hoadon.ngaycapnhap) = YEAR(GETDATE());\n", nativeQuery = true)
    BigDecimal tongTienDonThangNay();

    @Query(value = "select SUM(hoadonchitiet.dongiasaugiam * hoadonchitiet.soluong) from\n" +
            "dbo.hoadon INNER JOIN dbo.hoadonchitiet\n" +
            "ON dbo.hoadon.id = dbo.hoadonchitiet.idhoadon\n" +
            "where hoadon.trangthai = 5 AND YEAR(hoadon.ngaycapnhap) = YEAR(GETDATE())", nativeQuery = true)
    BigDecimal tongTienDonNamNay();

    @Query(value = "SELECT COUNT(hoadon.id)\n" +
            "FROM dbo.hoadon WHERE CONVERT(date, hoadon.ngaycapnhap) = CONVERT(DATE, GETDATE())", nativeQuery = true)
    Integer soDonHomNay();

    @Query(value = "SELECT COUNT(*) AS TongSoDonHang\n" +
            "FROM dbo.hoadon \n" +
            "WHERE DATEPART(WEEK, hoadon.ngaycapnhap) = DATEPART(WEEK, GETDATE()) \n" +
            "  AND YEAR(hoadon.ngaycapnhap) = YEAR(GETDATE());", nativeQuery = true)
    Integer soDonTuanNay();

    @Query(value = "SELECT COUNT(*) AS TongSoDonHang\n" +
            "FROM dbo.hoadon \n" +
            "WHERE MONTH(hoadon.ngaycapnhap) = MONTH(GETDATE()) \n" +
            "AND YEAR(hoadon.ngaycapnhap) = YEAR(GETDATE());", nativeQuery = true)
    Integer soDonThangNay();

    @Query(value = "SELECT count(*)\n" +
            "FROM dbo.hoadon \n" +
            "WHERE YEAR(hoadon.ngaycapnhap) = YEAR(GETDATE());\n", nativeQuery = true)
    Integer soDonNamNay();

    @Query(value = "SELECT COUNT(hoadon.id)\n" +
            "FROM dbo.hoadon WHERE hoadon.trangthai = 6 AND CONVERT(date, hoadon.ngaycapnhap) = CONVERT(DATE, GETDATE());", nativeQuery = true)
    Integer soDonHuyHomNay();

    @Query(value = "SELECT COUNT(hoadon.id)\n" +
            "FROM dbo.hoadon\n" +
            "WHERE hoadon.trangthai = 6\n" +
            "AND DATEPART(WEEK, hoadon.ngaycapnhap) = DATEPART(WEEK, GETDATE())\n" +
            "AND YEAR(hoadon.ngaycapnhap) = YEAR(GETDATE());", nativeQuery = true)
    Integer soDonHuyTuanNay();

    @Query(value = "SELECT COUNT(hoadon.id)\n" +
            "FROM dbo.hoadon \n" +
            "WHERE hoadon.trangthai = 6 AND MONTH(hoadon.ngaycapnhap) = MONTH(GETDATE()) \n" +
            "AND YEAR(hoadon.ngaycapnhap) = YEAR(GETDATE());", nativeQuery = true)
    Integer soDonHuyThangNay();

    @Query(value = "SELECT COUNT(hoadon.id)\n" +
            "FROM dbo.hoadon \n" +
            "WHERE hoadon.trangthai = 6 AND YEAR(hoadon.ngaycapnhap) = YEAR(GETDATE());", nativeQuery = true)
    Integer soDonHuyNamNay();

    @Query(value = "SELECT SUM(dbo.hoadonchitiet.soluong) FROM \n" +
            "dbo.hoadon INNER JOIN dbo.hoadonchitiet \n" +
            "ON dbo.hoadon.id = dbo.hoadonchitiet.idhoadon WHERE CONVERT(date, hoadon.ngaycapnhap) = CONVERT(DATE, GETDATE())", nativeQuery = true)
    Integer soSanPhamBanRaHomNay();

    @Query(value = "SELECT SUM(dbo.hoadonchitiet.soluong) \n" +
            "FROM dbo.hoadon \n" +
            "INNER JOIN dbo.hoadonchitiet ON dbo.hoadon.id = dbo.hoadonchitiet.idhoadon \n" +
            "WHERE DATEPART(WEEK, hoadon.ngaycapnhap) = DATEPART(WEEK, GETDATE()) \n" +
            "  AND YEAR(hoadon.ngaycapnhap) = YEAR(GETDATE());\n", nativeQuery = true)
    Integer soSanPhamBanRaTuanNay();

    @Query(value = "SELECT SUM(dbo.hoadonchitiet.soluong) \n" +
            "FROM dbo.hoadon \n" +
            "INNER JOIN dbo.hoadonchitiet ON dbo.hoadon.id = dbo.hoadonchitiet.idhoadon \n" +
            "WHERE MONTH(hoadon.ngaycapnhap) = MONTH(GETDATE()) \n" +
            "AND YEAR(hoadon.ngaycapnhap) = YEAR(GETDATE());", nativeQuery = true)
    Integer soSanPhamBanRaThangNay();

    @Query(value = "SELECT SUM(dbo.hoadonchitiet.soluong) \n" +
            "FROM dbo.hoadon \n" +
            "INNER JOIN dbo.hoadonchitiet ON dbo.hoadon.id = dbo.hoadonchitiet.idhoadon \n" +
            "WHERE YEAR(hoadon.ngaycapnhap) = YEAR(GETDATE());\n", nativeQuery = true)
    Integer soSanPhamBanRaNamNay();

    @Query("SELECT COUNT(hd) FROM HoaDon hd WHERE hd.trangThai = 1")
    Integer demSoHoaDonChoXacNhan();

    @Query("SELECT COUNT(hd) FROM HoaDon hd WHERE hd.trangThai = 2")
    Integer demSoHoaDonXacNhan();

    @Query("SELECT COUNT(hd) FROM HoaDon hd WHERE hd.trangThai = 3")
    Integer demSoHoaDonChoGiaoHang();

    @Query("SELECT COUNT(hd) FROM HoaDon hd WHERE hd.trangThai = 4")
    Integer demSoHoaDonDangGiao();

    @Query("SELECT COUNT(hd) FROM HoaDon hd WHERE hd.trangThai = 5")
    Integer demSoHoaDonThanhCong();

    @Query("SELECT COUNT(hd) FROM HoaDon hd WHERE hd.trangThai = 6")
    Integer demSoHoaDonDaHuy();

}
