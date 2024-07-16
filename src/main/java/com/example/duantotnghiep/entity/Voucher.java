package com.example.duantotnghiep.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "voucher")
public class Voucher {

    @Id
    @Column(name = "id")
    private UUID id;

    @Column(name = "mavoucher")
    private String maVoucher;

    @Column(name = "tenvoucher")
    private String tenVoucher;

    @Column(name = "ngaybatdau")
    private Date ngayBatDau;

    @Column(name = "ngayketthuc")
    private Date ngayKetThuc;

    @Column(name = "ngaycapnhap")
    private Date ngayCapNhap;

    @Column(name = "soluongma")
    private Integer soLuongMa;

    @Column(name = "soluongdung")
    private Integer soLuongDung;

    @Column(name = "giatritoithieudonhang")
    private Long giaTriToiThieuDonhang;

    @Column(name = "giatrigiam")
    private Long giaTriGiam;

    @Column(name = "hinhthucgiam")
    private Integer hinhThucGiam;

    @Column(name = "trangthai")
    private Integer trangThai;

    @Column(name = "ngaytao")
    private Date ngayTao;


    @OneToMany(mappedBy = "voucher", fetch = FetchType.LAZY)
    private List<HoaDon> hoaDonList;
}
