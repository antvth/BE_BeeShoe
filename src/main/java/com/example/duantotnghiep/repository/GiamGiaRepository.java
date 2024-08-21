package com.example.duantotnghiep.repository;

import com.example.duantotnghiep.entity.GiamGia;
import com.example.duantotnghiep.entity.Size;
import com.example.duantotnghiep.response.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface GiamGiaRepository extends JpaRepository<GiamGia, UUID> {
        @Query("SELECT DISTINCT new com.example.duantotnghiep.response.GiamGiaResponse(gg.id, gg.maGiamGia, gg.tenGiamGia, gg.ngayBatDau, gg.ngayKetThuc, gg.hinhThucGiam, gg.trangThai, spgg.mucGiam) " +
                "FROM GiamGia gg " +
                "LEFT JOIN gg.spGiamGiaList spgg " +
                "LEFT JOIN spgg.sanPham sp " +
                "WHERE (:maGiamGia is null or gg.maGiamGia LIKE %:maGiamGia%) " +
                "AND (:tenGiamGia is null or gg.tenGiamGia LIKE %:tenGiamGia%) " +
                "AND (:tenSanPham is null or sp.tenSanPham LIKE %:tenSanPham%) " +
                "AND (:trangThai is null or gg.trangThai = :trangThai) " +
                "AND (:startDate is null or gg.ngayBatDau = :startDate) " +
                "ORDER BY gg.trangThai ASC , gg.ngayBatDau DESC")
        Page<GiamGiaResponse> listGiamGia(
                @Param("maGiamGia") String maGiamGia,
                @Param("tenGiamGia") String tenGiamGia,
                @Param("tenSanPham") String tenSanPham,
                @Param("trangThai") Integer trangThai,
                @Param("startDate") Date startDate,
                Pageable pageable);

        @Query("SELECT DISTINCT new com.example.duantotnghiep.response.GiamGiaResponse(gg.id,gg.maGiamGia,gg.tenGiamGia, gg.ngayBatDau, gg.ngayKetThuc, gg.hinhThucGiam, gg.trangThai, spgg.mucGiam) "
                        +
                        "FROM GiamGia gg " +
                        "JOIN gg.spGiamGiaList spgg " +
                        "JOIN spgg.sanPham sp " +
                        "WHERE gg.trangThai = 1 " +
                        "ORDER BY gg.ngayBatDau DESC")
        Page<GiamGiaResponse> listGiamGias(Pageable pageable);

        @Query("SELECT  new com.example.duantotnghiep.response.GiamGiaResponse(gg.id, gg.maGiamGia,gg.tenGiamGia, gg.ngayBatDau, gg.ngayKetThuc, gg.hinhThucGiam, gg.trangThai, spgg.mucGiam,sp.tenSanPham,sp.giaBan,spgg.donGiaKhiGiam,sp.id) "
                        +
                        "FROM GiamGia gg " +
                        "JOIN gg.spGiamGiaList spgg " +
                        "JOIN spgg.sanPham sp " +
                        "WHERE gg.trangThai = 1 and  gg.id = :id ")
        List<GiamGiaResponse> listGiamGiaDetail(@Param("id") UUID id);

        @Query("SELECT new com.example.duantotnghiep.response.GiamGiaResponse(gg.id,gg.maGiamGia,gg.tenGiamGia,gg.ngayBatDau,gg.ngayKetThuc,gg.hinhThucGiam,gg.trangThai,spgg.mucGiam) "
                        +
                        "FROM GiamGia gg " +
                        "JOIN gg.spGiamGiaList spgg " +
                        " JOIN spgg.sanPham sp " +
                        "WHERE gg.maGiamGia LIKE :key OR gg.tenGiamGia LIKE :key ")
        List<GiamGiaResponse> findbyValueString(@Param("key") String key);

        @Query("SELECT new com.example.duantotnghiep.response.GiamGiaResponse(gg.id,gg.maGiamGia,gg.tenGiamGia,gg.ngayBatDau,gg.ngayKetThuc,gg.hinhThucGiam,gg.trangThai,spgg.mucGiam) "
                        +
                        "FROM GiamGia gg " +
                        "JOIN gg.spGiamGiaList spgg " +
                        " JOIN spgg.sanPham sp " +
                        "WHERE  gg.ngayBatDau =  :key1 ")
        List<GiamGiaResponse> findbyValueDate(@Param("key1") Date key1);

        @Query("SELECT new com.example.duantotnghiep.response.GiamGiaResponse(gg.id,gg.maGiamGia,gg.tenGiamGia,gg.ngayBatDau,gg.ngayKetThuc,gg.hinhThucGiam,gg.trangThai,spgg.mucGiam) "
                        +
                        "FROM GiamGia gg " +
                        "JOIN gg.spGiamGiaList spgg " +
                        " JOIN spgg.sanPham sp " +
                        "WHERE gg.trangThai = :key")
        List<GiamGiaResponse> findbyValueStatus(@Param("key") Integer key);
        //

        @Query("SELECT sp.id, i.tenImage, sp.tenSanPham, sp.giaBan FROM SanPham sp JOIN sp.listImage i WHERE i.isDefault = TRUE AND sp.tenSanPham LIKE :key ")
        List<Object[]> ProductDetailResponse(@Param("key") String key);

//        @Query("SELECT sp.id, i.tenImage as ten, sp.tenSanPham, sp.giaBan FROM SanPham sp JOIN sp.listImage i WHERE i.isDefault = TRUE AND (:tenSanPham is null or sp.tenSanPham LIKE %:tenSanPham%) ")

        @Query("SELECT sp.id, i.tenImage, sp.tenSanPham, sp.giaBan FROM SanPham sp " +
                "JOIN sp.listImage i " +
                "JOIN sp.kieuDe kd " +
                "JOIN sp.thuongHieu th " +
                "JOIN sp.danhMuc dm " +
                "JOIN sp.xuatXu xx  " +
                "WHERE i.isDefault = TRUE AND kd.id = :id or th.id = :id or dm.id = :id or xx.id = :id")
        List<Object[]> ListSearchDanhMuc(@Param("id") UUID id);

        @Query("SELECT sp.id, i.tenImage, sp.tenSanPham, sp.giaBan " +
                "FROM SanPham sp " +
                "JOIN sp.thuongHieu th " +
                "JOIN sp.kieuDe kd " +
                "JOIN sp.danhMuc dm " +
                "JOIN sp.xuatXu xx " +
                "JOIN sp.listImage i " +
                "WHERE i.isDefault = true " +
                "AND sp.trangThai = 1 " +
                "AND (:tenSanPham is null or sp.tenSanPham LIKE %:tenSanPham%) "+
                "AND th.trangThai = 1 " +
                "AND kd.trangThai = 1 " +
                "AND dm.trangThai = 1 " +
                "AND xx.trangThai = 1 ORDER BY sp.ngayTao DESC")
        Page<Object[]> listProductResponse(
                @Param("tenSanPham") String tenSanPham,
                Pageable pageable);

//        @Query("SELECT sp.id, i.tenImage , sp.tenSanPham, sp.giaBan FROM SanPham sp LEFT JOIN sp.listImage i WHERE i.isDefault = TRUE AND (:tenSanPham is null or sp.tenSanPham LIKE %:tenSanPham%) AND sp.trangThai = 1")
//        Page<Object[]> listProductResponse(
//                @Param("tenSanPham") String tenSanPham,
//                Pageable pageable);

        @Query("SELECT COUNT(spgg.id) FROM GiamGia gg JOIN gg.spGiamGiaList spgg WHERE spgg.sanPham.id = :productId")
        int countRecordsByProductId(@Param("productId") UUID productId);

        @Query("SELECT gg.tenGiamGia FROM SpGiamGia spgg JOIN spgg.giamGia gg WHERE gg.tenGiamGia = :tenGiamGia")
        boolean existsByGiamGia_TenGiamGia(@Param("tenGiamGia") String tenGiamGia);

        @Query("SELECT COUNT(spgg) " +
                        "FROM SpGiamGia spgg " +
                        "JOIN spgg.giamGia gg " +
                        "WHERE spgg.sanPham.id = :productId ")
        int countByProductId(@Param("productId") UUID productId);

        @Query("SELECT sp.id FROM SanPham sp" +
                        " JOIN sp.listSanPhamChiTiet spct " +
                        " WHERE sp.danhMuc.id = :danhMucId or sp.thuongHieu.id = :danhMucId or sp.xuatXu.id = :danhMucId or spct.chatLieu.id  = :danhMucId or sp.kieuDe.id =:danhMucId or spct.mauSac.id =:danhMucId or spct.size.id =:danhMucId ")
        List<UUID> findProductIdsByDanhMucId(@Param("danhMucId") UUID danhMucId);

        @Modifying
        @Transactional
        @Query("UPDATE GiamGia gg SET gg.tenGiamGia = :tenGiamGia, gg.ngayBatDau = :ngayBatDau, gg.ngayKetThuc = :ngayKetThuc, gg.hinhThucGiam = :hinhThucGiam, gg.trangThai = :trangThai WHERE gg.id = :giamGiaId")
        void updateGiamGia(@Param("giamGiaId") UUID giamGiaId,
                        @Param("tenGiamGia") String tenGiamGia,
                        @Param("ngayBatDau") Date ngayBatDau,
                        @Param("ngayKetThuc") Date ngayKetThuc,
                        @Param("hinhThucGiam") Integer hinhThucGiam,
                        @Param("trangThai") Integer trangThai);
        boolean existsByTenGiamGia(String tenGiamGia);
        // @Query("SELECT new com.example.duantotnghiep.response.GiamGiaResponse(gg.id,
        // gg.maGiamGia,gg.tenGiamGia, gg.ngayBatDau, gg.ngayKetThuc, gg.hinhThucGiam,
        // gg.trangThai, spgg.mucGiam, sp.tenSanPham, sp.giaBan, spgg.donGiaKhiGiam) " +
        // "FROM GiamGia gg " +
        // "JOIN gg.spGiamGiaList spgg " +
        // "JOIN spgg.sanPham sp " +
        // "WHERE gg.trangThai = 1 and gg.id = :id")
        // List<GiamGiaResponse> listGiamGiaDetaill(@Param("id") UUID id);

}
