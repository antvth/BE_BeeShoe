package com.example.duantotnghiep.repository;

import com.example.duantotnghiep.entity.SanPhamChiTiet;
import com.example.duantotnghiep.response.SanPhamChiTietResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Repository
public interface ChiTietSanPhamRepository extends JpaRepository<SanPhamChiTiet, UUID> {

    // TODO: Tính ra giá giảm cuối cùng false
    @Query("SELECT s.giaBan - COALESCE(SUM(gg.mucGiam), 0) - COALESCE(SUM(gg.donGiaKhiGiam), 0)AS GiaGocMinusGiaCuoiCuong " +
            "FROM SanPham s " +
            "LEFT JOIN s.spGiamGiaList gg " +
            "WHERE s.id = :id AND gg.trangThai = 1 " +
            "GROUP BY s.giaBan")
    BigDecimal calculatePriceDifference(@Param("id") UUID id);

    // TODO: Load sản phẩm ở quầy
    @Query("SELECT sp.id, spct.id, i.tenImage, sp.tenSanPham, sp.giaBan, spct.soLuong, ms.tenMauSac, s.size, cl.tenChatLieu, th.id " +
            "FROM SanPham sp " +
            "JOIN sp.listImage i " +
            "JOIN sp.kieuDe kd " +
            "JOIN sp.thuongHieu th " +
            "JOIN sp.danhMuc dm " +
            "JOIN sp.xuatXu xx " +
            "JOIN sp.listSanPhamChiTiet spct " +
            "JOIN spct.size s " +
            "JOIN spct.chatLieu cl " +
            "JOIN spct.mauSac ms " +
            "WHERE i.isDefault = TRUE " +
            "AND kd.trangThai = 1 " +
            "AND sp.trangThai = 1 " +
            "AND th.trangThai = 1 " +
            "AND dm.trangThai = 1 " +
            "AND xx.trangThai = 1 " +
            "AND spct.trangThai = 1 " +
            "AND s.trangThai = 1 " +
            "AND cl.trangThai = 1 " +
            "AND ms.trangThai = 1 ORDER BY sp.ngayTao DESC")
    Page<Object[]> getAll(Pageable pageable);

    // TODO: Load sản phẩm ở quầy
    @Query("SELECT sp.id, i.tenImage, sp.tenSanPham, sp.giaBan, th.id " +
            "FROM SanPham sp " +
            "JOIN sp.listImage i " +
            "JOIN sp.kieuDe kd " +
            "JOIN sp.thuongHieu th " +
            "JOIN sp.danhMuc dm " +
            "JOIN sp.xuatXu xx " +
            "WHERE i.isDefault = TRUE " +
            "AND kd.trangThai = 1 " +
            "AND sp.trangThai = 1 " +
            "AND th.trangThai = 1 " +
            "AND dm.trangThai = 1 " +
            "AND xx.trangThai = 1 " +
            "ORDER BY sp.ngayTao DESC")
    Page<Object[]> getAllShop(Pageable pageable);

    @Query("SELECT sp.id, spct.id, i.tenImage, sp.tenSanPham, sp.giaBan, spct.soLuong, ms.tenMauSac, s.size, cl.tenChatLieu, th.id " +
            "FROM SanPham sp " +
            "JOIN sp.listImage i " +
            "JOIN sp.kieuDe kd " +
            "JOIN sp.thuongHieu th " +
            "JOIN sp.danhMuc dm " +
            "JOIN sp.xuatXu xx " +
            "JOIN sp.listSanPhamChiTiet spct " +
            "JOIN spct.size s " +
            "JOIN spct.chatLieu cl " +
            "JOIN spct.mauSac ms " +
            "WHERE i.isDefault = TRUE " +
            "AND kd.trangThai = 1 " +
            "AND sp.trangThai = 1 " +
            "AND th.trangThai = 1 " +
            "AND dm.trangThai = 1 " +
            "AND xx.trangThai = 1 " +
            "AND spct.trangThai = 1 " +
            "AND s.trangThai = 1 " +
            "AND cl.trangThai = 1 " +
            "AND ms.trangThai = 1 AND th.id = :idthuonghieu")
    Page<Object[]> getAll(@Param("idthuonghieu") UUID idthuonghieu, Pageable pageable);

    @Query("SELECT sp.id, spct.id, i.tenImage, sp.tenSanPham, sp.giaBan, spct.soLuong, ms.tenMauSac, s.size, cl.tenChatLieu, th.id " +
            "FROM SanPham sp " +
            "JOIN sp.listImage i " +
            "JOIN sp.kieuDe kd " +
            "JOIN sp.thuongHieu th " +
            "JOIN sp.danhMuc dm " +
            "JOIN sp.xuatXu xx " +
            "JOIN sp.listSanPhamChiTiet spct " +
            "JOIN spct.size s " +
            "JOIN spct.chatLieu cl " +
            "JOIN spct.mauSac ms " +
            "WHERE i.isDefault = TRUE " +
            "AND kd.trangThai = 1 " +
            "AND sp.trangThai = 1 " +
            "AND th.trangThai = 1 " +
            "AND dm.trangThai = 1 " +
            "AND xx.trangThai = 1 " +
            "AND spct.trangThai = 1 " +
            "AND s.trangThai = 1 " +
            "AND cl.trangThai = 1 " +
            "AND ms.trangThai = 1 AND sp.tenSanPham = :name")
    List<Object[]> getOne(@Param("name") String name);

    //TODO: Tìm kiếm sản phẩm theo tên
    @Query("SELECT sp.id, spct.id, i.tenImage, sp.tenSanPham, sp.giaBan, spct.soLuong, ms.tenMauSac, s.size, cl.tenChatLieu, th.id " +
            "FROM SanPham sp " +
            "JOIN sp.listImage i " +
            "JOIN sp.kieuDe kd " +
            "JOIN sp.thuongHieu th " +
            "JOIN sp.danhMuc dm " +
            "JOIN sp.xuatXu xx " +
            "JOIN sp.listSanPhamChiTiet spct " +
            "JOIN spct.size s " +
            "JOIN spct.chatLieu cl " +
            "JOIN spct.mauSac ms " +
            "WHERE i.isDefault = TRUE " +
            "AND kd.trangThai = 1 " +
            "AND sp.trangThai = 1 " +
            "AND th.trangThai = 1 " +
            "AND dm.trangThai = 1 " +
            "AND xx.trangThai = 1 " +
            "AND spct.trangThai = 1 " +
            "AND s.trangThai = 1 " +
            "AND cl.trangThai = 1 " +
            "AND ms.trangThai = 1 " +
            "AND sp.tenSanPham like %:name%")
    Page<Object[]> searchByName(Pageable pageable, @Param("name") String name);

    //TODO: Lọc theo tên thương hiệu
    @Query("SELECT sp.id, spct.id, i.tenImage, sp.tenSanPham, sp.giaBan, spct.soLuong, ms.tenMauSac, s.size, cl.tenChatLieu, th.id " +
            "FROM SanPham sp " +
            "JOIN sp.listImage i " +
            "JOIN sp.kieuDe kd " +
            "JOIN sp.thuongHieu th " +
            "JOIN sp.danhMuc dm " +
            "JOIN sp.xuatXu xx " +
            "JOIN sp.listSanPhamChiTiet spct " +
            "JOIN spct.size s " +
            "JOIN spct.chatLieu cl " +
            "JOIN spct.mauSac ms " +
            "WHERE i.isDefault = TRUE " +
            "AND kd.trangThai = 1 " +
            "AND sp.trangThai = 1 " +
            "AND th.trangThai = 1 " +
            "AND dm.trangThai = 1 " +
            "AND xx.trangThai = 1 " +
            "AND spct.trangThai = 1 " +
            "AND s.trangThai = 1 " +
            "AND cl.trangThai = 1 " +
            "AND ms.trangThai = 1 " +
            "AND (:tenThuongHieu is null OR th.tenThuongHieu = :tenThuongHieu) " +
            "AND (:tenXuatXu is null OR xx.tenXuatXu = :tenXuatXu) " +
            "AND (:tenDanhMuc is null OR dm.tenDanhMuc = :tenDanhMuc) " +
            "AND (:tenDe is null OR kd.tenDe = :tenDe) " +
            "AND (:tenMauSac is null OR ms.tenMauSac = :tenMauSac) " +
            "AND (:tenChatLieu is null OR cl.tenChatLieu = :tenChatLieu) " +
            "AND (:size is null OR s.size = :size) ")
    Page<Object[]> filterBrand(Pageable pageable,
                               @Param("tenThuongHieu") String tenThuongHieu,
                               @Param("tenXuatXu") String tenXuatXu,
                               @Param("tenDanhMuc") String tenDanhMuc,
                               @Param("tenDe") String tenDe,
                               @Param("tenChatLieu") String tenChatLieu,
                               @Param("tenMauSac") String tenMauSac,
                               @Param("size") Integer size);

    @Query("SELECT sp.id, spct.id, i.tenImage, sp.tenSanPham, sp.giaBan, spct.soLuong, ms.tenMauSac, s.size, cl.tenChatLieu, th.id " +
            "FROM SanPham sp " +
            "JOIN sp.listImage i " +
            "JOIN sp.kieuDe kd " +
            "JOIN sp.thuongHieu th " +
            "JOIN sp.danhMuc dm " +
            "JOIN sp.xuatXu xx " +
            "JOIN sp.listSanPhamChiTiet spct " +
            "JOIN spct.size s " +
            "JOIN spct.chatLieu cl " +
            "JOIN spct.mauSac ms " +
            "WHERE i.isDefault = TRUE " +
            "AND kd.trangThai = 1 " +
            "AND sp.trangThai = 1 " +
            "AND th.trangThai = 1 " +
            "AND dm.trangThai = 1 " +
            "AND xx.trangThai = 1 " +
            "AND spct.trangThai = 1 " +
            "AND s.trangThai = 1 " +
            "AND cl.trangThai = 1 " +
            "AND ms.trangThai = 1 " +
            "AND th.id = :id ")
    Page<Object[]> filterBrandId(Pageable pageable, @Param("id") UUID id);

    //TODO: Lọc theo tên danh mục
    @Query("SELECT sp.id, spct.id,i.tenImage, sp.tenSanPham, sp.giaBan,spct.soLuong, ms.tenMauSac, s.size, cl.tenChatLieu, th.id " +
            "FROM SanPham sp " +
            "JOIN sp.listImage i " +
            "JOIN sp.kieuDe kd " +
            "JOIN sp.thuongHieu th " +
            "JOIN sp.danhMuc dm " +
            "JOIN sp.xuatXu xx " +
            "JOIN sp.listSanPhamChiTiet spct " +
            "JOIN spct.size s " +
            "JOIN spct.chatLieu cl " +
            "JOIN spct.mauSac ms " +
            "WHERE i.isDefault = TRUE " +
            "AND kd.trangThai = 1 " +
            "AND sp.trangThai = 1 " +
            "AND th.trangThai = 1 " +
            "AND dm.trangThai = 1 " +
            "AND xx.trangThai = 1 " +
            "AND spct.trangThai = 1 " +
            "AND s.trangThai = 1 " +
            "AND cl.trangThai = 1 " +
            "AND ms.trangThai = 1 " +
            "AND dm.tenDanhMuc = :name")
    Page<Object[]> filterCategory(Pageable pageable, @Param("name") String name);

    @Query("SELECT sp.id, spct.id,i.tenImage, sp.tenSanPham, sp.giaBan,spct.soLuong, ms.tenMauSac, s.size, cl.tenChatLieu, th.id " +
            "FROM SanPham sp " +
            "JOIN sp.listImage i " +
            "JOIN sp.kieuDe kd " +
            "JOIN sp.thuongHieu th " +
            "JOIN sp.danhMuc dm " +
            "JOIN sp.xuatXu xx " +
            "JOIN sp.listSanPhamChiTiet spct " +
            "JOIN spct.size s " +
            "JOIN spct.chatLieu cl " +
            "JOIN spct.mauSac ms " +
            "WHERE i.isDefault = TRUE " +
            "AND kd.trangThai = 1 " +
            "AND sp.trangThai = 1 " +
            "AND th.trangThai = 1 " +
            "AND dm.trangThai = 1 " +
            "AND xx.trangThai = 1 " +
            "AND spct.trangThai = 1 " +
            "AND s.trangThai = 1 " +
            "AND cl.trangThai = 1 " +
            "AND ms.trangThai = 1 " +
            "AND dm.id = :id")
    Page<Object[]> filterCategoryId(Pageable pageable, @Param("id") UUID id);

    //TODO: Lọc theo tên đế
    @Query("SELECT sp.id, spct.id,i.tenImage, sp.tenSanPham, sp.giaBan,spct.soLuong, ms.tenMauSac, s.size, cl.tenChatLieu, th.id " +
            "FROM SanPham sp " +
            "JOIN sp.listImage i " +
            "JOIN sp.kieuDe kd " +
            "JOIN sp.thuongHieu th " +
            "JOIN sp.danhMuc dm " +
            "JOIN sp.xuatXu xx " +
            "JOIN sp.listSanPhamChiTiet spct " +
            "JOIN spct.size s " +
            "JOIN spct.chatLieu cl " +
            "JOIN spct.mauSac ms " +
            "WHERE i.isDefault = TRUE " +
            "AND kd.trangThai = 1 " +
            "AND sp.trangThai = 1 " +
            "AND th.trangThai = 1 " +
            "AND dm.trangThai = 1 " +
            "AND xx.trangThai = 1 " +
            "AND spct.trangThai = 1 " +
            "AND s.trangThai = 1 " +
            "AND cl.trangThai = 1 " +
            "AND ms.trangThai = 1 " +
            "AND kd.tenDe = :name")
    Page<Object[]> filterSole(Pageable pageable, @Param("name") String name);

    @Query("SELECT sp.id, spct.id,i.tenImage, sp.tenSanPham, sp.giaBan,spct.soLuong, ms.tenMauSac, s.size, cl.tenChatLieu, th.id " +
            "FROM SanPham sp " +
            "JOIN sp.listImage i " +
            "JOIN sp.kieuDe kd " +
            "JOIN sp.thuongHieu th " +
            "JOIN sp.danhMuc dm " +
            "JOIN sp.xuatXu xx " +
            "JOIN sp.listSanPhamChiTiet spct " +
            "JOIN spct.size s " +
            "JOIN spct.chatLieu cl " +
            "JOIN spct.mauSac ms " +
            "WHERE i.isDefault = TRUE " +
            "AND kd.trangThai = 1 " +
            "AND sp.trangThai = 1 " +
            "AND th.trangThai = 1 " +
            "AND dm.trangThai = 1 " +
            "AND xx.trangThai = 1 " +
            "AND spct.trangThai = 1 " +
            "AND s.trangThai = 1 " +
            "AND cl.trangThai = 1 " +
            "AND ms.trangThai = 1 " +
            "AND kd.id = :id")
    Page<Object[]> filterSoleId(Pageable pageable, @Param("id") UUID id);

    //TODO: Lọc theo tên xuất xứ
    @Query("SELECT sp.id, spct.id,i.tenImage, sp.tenSanPham, sp.giaBan,spct.soLuong, ms.tenMauSac, s.size, cl.tenChatLieu, th.id " +
            "FROM SanPham sp " +
            "JOIN sp.listImage i " +
            "JOIN sp.kieuDe kd " +
            "JOIN sp.thuongHieu th " +
            "JOIN sp.danhMuc dm " +
            "JOIN sp.xuatXu xx " +
            "JOIN sp.listSanPhamChiTiet spct " +
            "JOIN spct.size s " +
            "JOIN spct.chatLieu cl " +
            "JOIN spct.mauSac ms " +
            "WHERE i.isDefault = TRUE " +
            "AND kd.trangThai = 1 " +
            "AND sp.trangThai = 1 " +
            "AND th.trangThai = 1 " +
            "AND dm.trangThai = 1 " +
            "AND xx.trangThai = 1 " +
            "AND spct.trangThai = 1 " +
            "AND s.trangThai = 1 " +
            "AND cl.trangThai = 1 " +
            "AND ms.trangThai = 1 " +
            "AND xx.tenXuatXu = :name")
    Page<Object[]> filterOrigin(Pageable pageable, @Param("name") String name);

    @Query("SELECT sp.id, spct.id,i.tenImage, sp.tenSanPham, sp.giaBan,spct.soLuong, ms.tenMauSac, s.size, cl.tenChatLieu, th.id " +
            "FROM SanPham sp " +
            "JOIN sp.listImage i " +
            "JOIN sp.kieuDe kd " +
            "JOIN sp.thuongHieu th " +
            "JOIN sp.danhMuc dm " +
            "JOIN sp.xuatXu xx " +
            "JOIN sp.listSanPhamChiTiet spct " +
            "JOIN spct.size s " +
            "JOIN spct.chatLieu cl " +
            "JOIN spct.mauSac ms " +
            "WHERE i.isDefault = TRUE " +
            "AND kd.trangThai = 1 " +
            "AND sp.trangThai = 1 " +
            "AND th.trangThai = 1 " +
            "AND dm.trangThai = 1 " +
            "AND xx.trangThai = 1 " +
            "AND spct.trangThai = 1 " +
            "AND s.trangThai = 1 " +
            "AND cl.trangThai = 1 " +
            "AND ms.trangThai = 1 " +
            "AND xx.id = :id")
    Page<Object[]> filterOriginId(Pageable pageable, @Param("id") UUID id);

    //TODO: Lọc theo size
    @Query("SELECT sp.id, spct.id,i.tenImage, sp.tenSanPham, sp.giaBan,spct.soLuong, ms.tenMauSac, s.size, cl.tenChatLieu, th.id " +
            "FROM SanPham sp " +
            "JOIN sp.listImage i " +
            "JOIN sp.kieuDe kd " +
            "JOIN sp.thuongHieu th " +
            "JOIN sp.danhMuc dm " +
            "JOIN sp.xuatXu xx " +
            "JOIN sp.listSanPhamChiTiet spct " +
            "JOIN spct.size s " +
            "JOIN spct.chatLieu cl " +
            "JOIN spct.mauSac ms " +
            "WHERE i.isDefault = TRUE " +
            "AND kd.trangThai = 1 " +
            "AND sp.trangThai = 1 " +
            "AND th.trangThai = 1 " +
            "AND dm.trangThai = 1 " +
            "AND xx.trangThai = 1 " +
            "AND spct.trangThai = 1 " +
            "AND s.trangThai = 1 " +
            "AND cl.trangThai = 1 " +
            "AND ms.trangThai = 1 " +
            "AND s.size = :size")
    Page<Object[]> filterSize(Pageable pageable, @Param("size") Integer size);

    //TODO: Lọc theo tên chất liệu
    @Query("SELECT sp.id, spct.id,i.tenImage, sp.tenSanPham, sp.giaBan,spct.soLuong, ms.tenMauSac, s.size, cl.tenChatLieu, th.id " +
            "FROM SanPham sp " +
            "JOIN sp.listImage i " +
            "JOIN sp.kieuDe kd " +
            "JOIN sp.thuongHieu th " +
            "JOIN sp.danhMuc dm " +
            "JOIN sp.xuatXu xx " +
            "JOIN sp.listSanPhamChiTiet spct " +
            "JOIN spct.size s " +
            "JOIN spct.chatLieu cl " +
            "JOIN spct.mauSac ms " +
            "WHERE i.isDefault = TRUE " +
            "AND kd.trangThai = 1 " +
            "AND sp.trangThai = 1 " +
            "AND th.trangThai = 1 " +
            "AND dm.trangThai = 1 " +
            "AND xx.trangThai = 1 " +
            "AND spct.trangThai = 1 " +
            "AND s.trangThai = 1 " +
            "AND cl.trangThai = 1 " +
            "AND ms.trangThai = 1 " +
            "AND cl.tenChatLieu = :name")
    Page<Object[]> filterMaterial(Pageable pageable, @Param("name") String name);

    //TODO: Lọc theo tên màu sắc
    @Query("SELECT sp.id, spct.id,i.tenImage, sp.tenSanPham, sp.giaBan,spct.soLuong, ms.tenMauSac, s.size, cl.tenChatLieu, th.id " +
            "FROM SanPham sp " +
            "JOIN sp.listImage i " +
            "JOIN sp.kieuDe kd " +
            "JOIN sp.thuongHieu th " +
            "JOIN sp.danhMuc dm " +
            "JOIN sp.xuatXu xx " +
            "JOIN sp.listSanPhamChiTiet spct " +
            "JOIN spct.size s " +
            "JOIN spct.chatLieu cl " +
            "JOIN spct.mauSac ms " +
            "WHERE i.isDefault = TRUE " +
            "AND kd.trangThai = 1 " +
            "AND sp.trangThai = 1 " +
            "AND th.trangThai = 1 " +
            "AND dm.trangThai = 1 " +
            "AND xx.trangThai = 1 " +
            "AND spct.trangThai = 1 " +
            "AND s.trangThai = 1 " +
            "AND cl.trangThai = 1 " +
            "AND ms.trangThai = 1 " +
            "AND ms.tenMauSac = :name")
    Page<Object[]> filterColor(Pageable pageable, @Param("name") String name);

    SanPhamChiTiet findByQrcode(String qrCode);

    @Query("SELECT new com.example.duantotnghiep.response.SanPhamChiTietResponse" +
            "(sp.tenSanPham, spct.soLuong, sp.giaBan, s.size, ms.tenMauSac, cl.tenChatLieu, spct.trangThai) " +
            "FROM SanPhamChiTiet spct " +
            "JOIN spct.sanPham sp " +
            "JOIN spct.size s " +
            "JOIN spct.chatLieu cl " +
            "JOIN spct.mauSac ms " +
            "WHERE sp.id = :id AND spct.trangThai = 1 AND sp.trangThai = 1 AND cl.trangThai = 1 AND ms.trangThai = 1 AND s.trangThai = 1")
    Page<SanPhamChiTietResponse> getAllSanPhamChiTiet(@Param("id") UUID id, Pageable pageable);

    @Query("SELECT new com.example.duantotnghiep.response.SanPhamChiTietResponse" +
            "(sp.tenSanPham, spct.soLuong, sp.giaBan, s.size, ms.tenMauSac, cl.tenChatLieu, spct.trangThai) " +
            "FROM SanPhamChiTiet spct " +
            "JOIN spct.sanPham sp " +
            "JOIN spct.size s " +
            "JOIN spct.chatLieu cl " +
            "JOIN spct.mauSac ms WHERE sp.id = :id AND spct.trangThai = :trangThai AND sp.trangThai = 1")
    Page<SanPhamChiTietResponse> finByTrangThai(@Param("id") UUID id, @Param("trangThai") Integer trangThai, Pageable pageable);

    @Query("SELECT new com.example.duantotnghiep.response.SanPhamChiTietResponse" +
            "(sp.tenSanPham, spct.soLuong, sp.giaBan, s.size, ms.tenMauSac, cl.tenChatLieu, spct.trangThai) " +
            "FROM SanPhamChiTiet spct " +
            "JOIN spct.sanPham sp " +
            "JOIN spct.size s " +
            "JOIN spct.chatLieu cl " +
            "JOIN spct.mauSac ms WHERE " +
            "sp.id = :id " +
            "AND spct.trangThai = 1 " +
            "AND sp.trangThai = 1 " +
            "AND cl.tenChatLieu = :key OR ms.tenMauSac = :key")
    Page<SanPhamChiTietResponse> finByKey(@Param("id") UUID id, @Param("key") String key, Pageable pageable);

    @Query("SELECT new com.example.duantotnghiep.response.SanPhamChiTietResponse" +
            "(sp.tenSanPham, spct.soLuong, sp.giaBan, s.size, ms.tenMauSac, cl.tenChatLieu, spct.trangThai) " +
            "FROM SanPhamChiTiet spct " +
            "JOIN spct.sanPham sp " +
            "JOIN spct.size s " +
            "JOIN spct.chatLieu cl " +
            "JOIN spct.mauSac ms " +
            "WHERE sp.id = :id " +
            "AND spct.trangThai = 1 " +
            "AND sp.trangThai = 1 " +
            "AND s.size = :size")
    Page<SanPhamChiTietResponse> finBySize(@Param("id") UUID id, @Param("size") Integer size, Pageable pageable);

    @Query("SELECT spct FROM SanPhamChiTiet spct JOIN spct.sanPham sp JOIN spct.chatLieu cl JOIN spct.mauSac ms JOIN spct.size s"  +
            " WHERE s.id = :idSize AND cl.id = :idChatLieu AND ms.id = :idMauSac AND sp.id =:idSanPham")
    SanPhamChiTiet findBySpct(@Param("idSize") UUID idSize, @Param("idChatLieu") UUID idChatLieu, @Param("idMauSac") UUID idMauSac, @Param("idSanPham") UUID idSanPham);
}
