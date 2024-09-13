package com.example.duantotnghiep.repository;

import com.example.duantotnghiep.entity.SanPham;
import com.example.duantotnghiep.mapper.ChiTietSanPhamCustom;
import com.example.duantotnghiep.mapper.GioHangCustom;
import com.example.duantotnghiep.mapper.SoLuongGioHangCustom;
import com.example.duantotnghiep.mapper.TongTienCustom;
import com.example.duantotnghiep.response.SanPhamResponse;
import com.example.duantotnghiep.response.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Repository
public interface SanPhamRepository extends JpaRepository<SanPham, UUID> {

    @Query(value = "SELECT TOP 8 sp.id, sp.tensanpham, im.tenimage, sp.giaban FROM sanpham sp JOIN sanphamchitiet spct ON sp.id = spct.idsanpham JOIN \n" +
            "            image im ON sp.id = im.idsanpham WHERE im.isdefault = 1 AND sp.trangthai = 1 ORDER BY sp.ngaytao DESC", nativeQuery = true)
    List<SanPhamResponse> getNewProduct();

    @Query(value = "SELECT TOP 8 sp.id, sp.tensanpham, im.tenimage, sp.giaban " +
            "FROM sanpham sp " +
            "JOIN sanphamchitiet spct ON sp.id = spct.idsanpham " +
            "JOIN image im ON sp.id = im.idsanpham " +
            "LEFT JOIN ThuongHieu th ON sp.idthuonghieu = th.id " + // Assuming the correct join condition
            "WHERE im.isdefault = 1 AND sp.trangthai = 1 AND th.id = :id " +
            "ORDER BY sp.ngaytao DESC", nativeQuery = true)
    List<SanPhamResponse> getNewProductbyId(@Param("id") UUID id);

    @Query(value = "SELECT  sp.tensanpham " +
            "FROM sanpham sp  " +
            "WHERE sp.id = :id " , nativeQuery = true)
    SanPhamResponse getNewProductbyName(@Param("id") UUID id);


    @Query(value = "SELECT TOP 8 sp.id, sp.tensanpham, im.tenimage, sp.giaban, spgg.donGiaKhiGiam " +
            "FROM sanpham sp " +
            "JOIN sanphamchitiet spct ON sp.id = spct.idsanpham " +
            "JOIN image im ON sp.id = im.idsanpham " +
            "LEFT JOIN thuonghieu th ON sp.idthuonghieu = th.id " +
            "LEFT JOIN spgiamgia spgg ON sp.id = spgg.idsanpham " +
            "WHERE im.isdefault = 1 AND sp.trangthai = 1 " +
            "AND (sp.giaban BETWEEN :key1 AND :key2 OR spgg.donGiaKhiGiam BETWEEN :key1 AND :key2) " +
            "ORDER BY sp.ngaytao DESC", nativeQuery = true)
    List<SanPhamResponse> getNewProductbyMoney(@Param("key1") BigDecimal key1, @Param("key2") BigDecimal key2);

    @Query("SELECT sp.id, i.tenImage, sp.maSanPham, sp.tenSanPham, sp.giaBan, sp.ngayTao, sp.trangThai " +
            "FROM SanPham sp " +
            "JOIN sp.thuongHieu th " +
            "JOIN sp.kieuDe kd " +
            "JOIN sp.danhMuc dm " +
            "JOIN sp.xuatXu xx " +
            "JOIN sp.listImage i " +
            "WHERE i.isDefault = true " +
            "AND sp.trangThai = 1 " +
            "AND th.trangThai = 1 " +
            "AND kd.trangThai = 1 " +
            "AND dm.trangThai = 1 " +
            "AND xx.trangThai = 1 ORDER BY sp.ngayTao DESC")
    Page<Object[]> getAllSanPham(Pageable pageable);

    @Query("SELECT sp.id, i.tenImage, sp.maSanPham, sp.tenSanPham, sp.giaBan, sp.ngayTao, sp.trangThai " +
            "FROM SanPham sp " +
            "JOIN sp.thuongHieu th " +
            "JOIN sp.kieuDe kd " +
            "JOIN sp.danhMuc dm " +
            "JOIN sp.xuatXu xx " +
            "JOIN sp.listImage i " +
            "WHERE i.isDefault = true " +
            "AND sp.trangThai = 1 " +
            "AND th.trangThai = 1 " +
            "AND kd.trangThai = 1 " +
            "AND xx.trangThai = 1 " +
            "AND dm.trangThai = 1 " +
            "AND th.tenThuongHieu = :value")
    Page<Object[]>  findByThuongHieu(Pageable pageable,@Param("value") String value);

    @Query("SELECT sp.id, i.tenImage, sp.maSanPham, sp.tenSanPham, sp.giaBan, sp.ngayTao, sp.trangThai " +
            "FROM SanPham sp " +
            "JOIN sp.thuongHieu th " +
            "JOIN sp.kieuDe kd " +
            "JOIN sp.danhMuc dm " +
            "JOIN sp.xuatXu xx " +
            "JOIN sp.listImage i " +
            "WHERE i.isDefault = true " +
            "AND sp.trangThai = 1 " +
            "AND th.trangThai = 1 " +
            "AND kd.trangThai = 1 " +
            "AND xx.trangThai = 1 " +
            "AND kd.tenDe = :value")
    Page<Object[]>  findByKieuDe(Pageable pageable,@Param("value") String value);

    @Query("SELECT sp.id, i.tenImage, sp.maSanPham, sp.tenSanPham, sp.giaBan, sp.ngayTao, sp.trangThai " +
            "FROM SanPham sp " +
            "JOIN sp.thuongHieu th " +
            "JOIN sp.kieuDe kd " +
            "JOIN sp.danhMuc dm " +
            "JOIN sp.xuatXu xx " +
            "JOIN sp.listImage i " +
            "WHERE i.isDefault = true " +
            "AND sp.trangThai = 1 " +
            "AND th.trangThai = 1 " +
            "AND kd.trangThai = 1 " +
            "AND xx.trangThai = 1 " +
            "AND dm.trangThai = 1 " +
            "AND xx.tenXuatXu = :value")
    Page<Object[]> findByXuatXu(Pageable pageable,@Param("value") String value);

    @Query("SELECT sp.id, i.tenImage, sp.maSanPham, sp.tenSanPham, sp.giaBan, sp.ngayTao, sp.trangThai " +
            "FROM SanPham sp " +
            "JOIN sp.thuongHieu th " +
            "JOIN sp.kieuDe kd " +
            "JOIN sp.danhMuc dm " +
            "JOIN sp.xuatXu xx " +
            "JOIN sp.listImage i " +
            "WHERE i.isDefault = true " +
            "AND sp.trangThai = 1 " +
            "AND th.trangThai = 1 " +
            "AND kd.trangThai = 1 " +
            "AND xx.trangThai = 1 " +
            "AND dm.trangThai = 1 " +
            "AND dm.tenDanhMuc = :value")
    Page<Object[]>  findByDanhMuc(Pageable pageable,@Param("value") String value);

    @Query("SELECT sp.id, i.tenImage, sp.maSanPham, sp.tenSanPham, sp.giaBan, sp.ngayTao, sp.trangThai " +
            "FROM SanPham sp " +
            "JOIN sp.thuongHieu th " +
            "JOIN sp.kieuDe kd " +
            "JOIN sp.danhMuc dm " +
            "JOIN sp.xuatXu xx " +
            "JOIN sp.listImage i " +
            "WHERE i.isDefault = true " +
            "AND sp.trangThai = 1 " +
            "AND th.trangThai = 1 " +
            "AND kd.trangThai = 1 " +
            "AND xx.trangThai = 1 " +
            "AND dm.trangThai = 1 " +
            "AND sp.tenSanPham = :value OR sp.maSanPham = : value")
    Page<Object[]> findByNameOrCode(Pageable pageable,@Param("value") String value);


    @Query("SELECT sp.id, i.tenImage, sp.maSanPham, sp.tenSanPham, sp.giaBan, sp.ngayTao, sp.trangThai " +
            "FROM SanPham sp " +
            "JOIN sp.thuongHieu th " +
            "JOIN sp.kieuDe kd " +
            "JOIN sp.danhMuc dm " +
            "JOIN sp.xuatXu xx " +
            "JOIN sp.listImage i " +
            "WHERE i.isDefault = true " +
            "AND sp.trangThai = 1 " +
            "AND th.trangThai = 1 " +
            "AND kd.trangThai = 1 " +
            "AND xx.trangThai = 1 " +
            "AND sp.trangThai = :status")
    Page<Object[]>  findByStatus(Pageable pageable, @Param("status") Integer status);

    SanPham findByTenSanPham(String name);

    @Query(value = "SELECT TOP 8 sp.id, sp.tensanpham, im.tenimage, sp.giaban " +
            "FROM sanpham sp " +
            "JOIN sanphamchitiet spct ON sp.id = spct.idsanpham " +
            "JOIN hoadonchitiet hdct ON spct.id = hdct.idsanphamchitiet " +
            "JOIN hoadon hd ON hdct.idhoadon = hd.id " +
            "JOIN image im ON sp.id = im.idsanpham " +
            "WHERE hdct.trangthai = 1 " +
            "GROUP BY sp.id, sp.tensanpham, im.tenimage, sp.giaban " +
            "ORDER BY SUM(hdct.soluong) DESC", nativeQuery = true)
    List<SanPhamResponse> getBestSellingProducts();

    @Query(value = "SELECT TOP 8 sp.id, sp.tensanpham, im.tenimage, sp.giaban " +
            "FROM sanpham sp " +
            "LEFT JOIN ThuongHieu th ON sp.idthuonghieu = th.id " +
            "JOIN sanphamchitiet spct ON sp.id = spct.idsanpham " +
            "JOIN hoadonchitiet hdct ON spct.id = hdct.idsanphamchitiet " +
            "JOIN hoadon hd ON hdct.idhoadon = hd.id " +
            "JOIN image im ON sp.id = im.idsanpham " +
            "WHERE hdct.trangthai = 1  AND th.id = :id " +
            "GROUP BY sp.id, sp.tensanpham, im.tenimage, sp.giaban " +
            "ORDER BY SUM(hdct.soluong) DESC", nativeQuery = true)
    List<SanPhamResponse> getBestSellingProductsbyId(@Param("id") UUID id);

    @Query(value = "SELECT new com.example.duantotnghiep.response.ProductUpdateResponse" +
            "(sp.maSanPham, sp.tenSanPham,sp.moTa, sp.giaBan, xx.tenXuatXu, dm.tenDanhMuc, th.tenThuongHieu, kd.tenDe) " +
            "FROM SanPham sp " +
            "JOIN sp.thuongHieu th " +
            "JOIN sp.kieuDe kd " +
            "JOIN sp.danhMuc dm " +
            "JOIN sp.xuatXu xx " +
            "WHERE sp.id = :id")
    ProductUpdateResponse findByProduct(@Param("id") UUID id);

    @Query(value = "SELECT new com.example.duantotnghiep.response.ProductDetailUpdateReponse" +
            "(spct.id, ms.tenMauSac, cl.tenChatLieu, s.size, spct.soLuong, spct.qrcode, spct.trangThai) " +
            "FROM SanPhamChiTiet spct " +
            "JOIN spct.size s " +
            "JOIN spct.chatLieu cl " +
            "JOIN spct.mauSac ms " +
            "WHERE spct.sanPham.id = :id")
    List<ProductDetailUpdateReponse> findByProductDetail(@Param("id") UUID id);
}
