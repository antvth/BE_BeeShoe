package com.example.duantotnghiep.repository;

import com.example.duantotnghiep.entity.HoaDon;
import com.example.duantotnghiep.entity.HoaDonChiTiet;
import com.example.duantotnghiep.response.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface HoaDonChiTietRepository extends JpaRepository<HoaDonChiTiet, UUID> {

    List<HoaDonChiTiet> findByHoaDon_Id(UUID id);

    List<HoaDonChiTiet> findByHoaDon(HoaDon hoaDon);

    HoaDonChiTiet findByHoaDonAndSanPhamChiTiet_Id(HoaDon hoaDon, UUID idSpCt);

    List<HoaDonChiTiet> findAllByHoaDon(HoaDon hoaDon);

    @Query("SELECT new com.example.duantotnghiep.response.ThongTinDonHang" +
            "(hd.ma, hd.trangThai,ld.tenLoaiDon, hd.diaChi, hd.tenNguoiNhan, hd.sdtNguoiNhan, hd.tenNguoiShip, hd.sdtNguoiShip, nv.id, hd.email) " +
            "FROM HoaDon hd JOIN " +
            "hd.loaiDon ld LEFT JOIN hd.taiKhoanNhanVien nv " +
            "WHERE hd.id = :idHoaDon")
    ThongTinDonHang getThongTinDonHang(@Param("idHoaDon") UUID idHoaDon);

    @Query("SELECT new com.example.duantotnghiep.response.SanPhamHoaDonChiTietResponse" +
            "(hdct.id, i.tenImage, sp.tenSanPham, hdct.donGia, hdct.donGiaSauGiam, hdct.soLuong, hdct.trangThai) " +
            "FROM HoaDon hd JOIN " +
            "hd.hoaDonChiTietList hdct " +
            "JOIN hdct.sanPhamChiTiet spct " +
            "JOIN spct.sanPham sp " +
            "JOIN sp.listImage i " +
            "WHERE i.isDefault = true AND hd.id = :idHoaDon")
    Page<SanPhamHoaDonChiTietResponse> getSanPhamHDCT(Pageable pageable, @Param("idHoaDon") UUID idHoaDon);

    @Query("SELECT new com.example.duantotnghiep.response.SanPhamHoaDonChiTietResponse" +
            "(hdct.id, i.tenImage, sp.tenSanPham, hdct.donGia, hdct.donGiaSauGiam, hdct.soLuong, hdct.trangThai) " +
            "FROM HoaDon hd JOIN " +
            "hd.hoaDonChiTietList hdct " +
            "JOIN hdct.sanPhamChiTiet spct " +
            "JOIN spct.sanPham sp " +
            "JOIN sp.listImage i " +
            "WHERE i.isDefault = true AND hd.id = :idHoaDon")
    List<SanPhamHoaDonChiTietResponse> getSanPhamHDCT(@Param("idHoaDon") UUID idHoaDon);


    @Query("SELECT new com.example.duantotnghiep.response.HinhThucThanhToanResponse(lhttt.tenLoai, httt.codeTransaction, httt.tongSoTien, httt.ngayThanhToan, httt.phuongThucThanhToan,httt.ghiChu, nv.maTaiKhoan) FROM HoaDon hd " +
            "JOIN hd.hinhThucThanhToanList httt " +
            "JOIN httt.loaiHinhThucThanhToan lhttt " +
            "LEFT JOIN hd.taiKhoanNhanVien nv WHERE hd.id = :idHoaDon ORDER BY httt.ngayThanhToan DESC ")
    List<HinhThucThanhToanResponse> getLichSuThanhToan(UUID idHoaDon);

    @Query("SELECT new com.example.duantotnghiep.response.TrangThaiHoaDonResponse(tthd.trangThai, tthd.thoiGian, tthd.username, tthd.ghiChu) " +
            "FROM HoaDon hd " +
            "JOIN hd.trangThaiHoaDonList tthd " +
            "LEFT JOIN hd.taiKhoanNhanVien tknv WHERE hd.id = :id ORDER BY tthd.thoiGian DESC ")
    List<TrangThaiHoaDonResponse> getAllTrangThaiHoaDon(@Param("id") UUID id);

    @Query("SELECT new com.example.duantotnghiep.response.MoneyResponse(hd.thanhTien, hd.tienShip, hd.tienGiamGia)" +
            "FROM HoaDon hd WHERE hd.id = :idHoaDon")
    MoneyResponse getAllMoneyByHoaDon(UUID idHoaDon);

    @Query("SELECT new com.example.duantotnghiep.response.ProductDetailDTOResponse(hdct.id, sp.tenSanPham, im.tenImage, hdct.donGia, hdct.donGiaSauGiam, s.size, cl.tenChatLieu, ms.tenMauSac, hdct.soLuong)" +
            "FROM HoaDonChiTiet hdct JOIN hdct.sanPhamChiTiet spct " +
            "JOIN spct.sanPham sp " +
            "JOIN spct.size s " +
            "JOIN spct.chatLieu cl " +
            "JOIN spct.mauSac ms " +
            "JOIN sp.listImage im " +
            "WHERE hdct.id = :idhdct AND im.isDefault = true")
    ProductDetailDTOResponse getDetailSanPham(@Param("idhdct") UUID idhdct);

}
