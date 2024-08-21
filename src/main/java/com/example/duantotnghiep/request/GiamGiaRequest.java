package com.example.duantotnghiep.request;

import com.example.duantotnghiep.entity.SanPhamChiTiet;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
// @AllArgsConstructor
@NoArgsConstructor
public class GiamGiaRequest {

    private String maGiamGia;
    private String tenGiamGia;
    private Date ngayBatDau;

    private Date ngayKetThuc;

    private Integer hinhThucGiam;
    private BigDecimal donGiaKhiGiam;
    private BigDecimal giaBan;
    private BigDecimal donGia;
    private Integer trangThai;
    private Long mucGiam;
    private List<UUID> idsanpham;
    private UUID idDanhMuc;
    private UUID idThuongHieu;
    private UUID idChatLieu;
    private UUID idMauSac;
    private UUID idKieuDe;
    private UUID idSize;
    private UUID idXuatXu;

    public GiamGiaRequest(String maGiamGia, String tenGiamGia, Date ngayBatDau, Date ngayKetThuc, Integer hinhThucGiam,
            BigDecimal donGiaKhiGiam, BigDecimal giaBan, BigDecimal donGia, Integer trangThai, Long mucGiam,
            List<UUID> idsanpham, UUID idDanhMuc, UUID idThuongHieu, UUID idChatLieu, UUID idMauSac, UUID idKieuDe,
            UUID idSize, UUID idXuatXu) {
        this.maGiamGia = maGiamGia;
        this.tenGiamGia = tenGiamGia;
        this.ngayBatDau = ngayBatDau;
        this.ngayKetThuc = ngayKetThuc;
        this.hinhThucGiam = hinhThucGiam;
        this.donGiaKhiGiam = donGiaKhiGiam;
        this.giaBan = giaBan;
        this.donGia = donGia;
        this.trangThai = trangThai;
        this.mucGiam = mucGiam;
        this.idsanpham = idsanpham;
        this.idDanhMuc = idDanhMuc;
        this.idThuongHieu = idThuongHieu;
        this.idChatLieu = idChatLieu;
        this.idMauSac = idMauSac;
        this.idKieuDe = idKieuDe;
        this.idSize = idSize;
        this.idXuatXu = idXuatXu;
    }
}
