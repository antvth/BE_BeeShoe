package com.example.duantotnghiep.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "xuatxu")
public class XuatXu {

    @Id
    @Column(name = "id")
    private UUID id;

    @Column(name = "tenxuatxu")
    private String tenXuatXu;

    @Column(name = "trangthai")
    private Integer trangThai;

    @Column(name = "ngaytao")
    private Date ngayTao;

    @Column(name = "ngaycapnhat")
    private Date ngayCapNhat;

    @OneToMany(mappedBy = "xuatXu",fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<SanPham> sanPhamList;

    public XuatXu(UUID id, String tenXuatXu, Integer trangThai, Date ngayTao, Date ngayCapNhat) {
        this.id = id;
        this.tenXuatXu = tenXuatXu;
        this.trangThai = trangThai;
        this.ngayTao = ngayTao;
        this.ngayCapNhat = ngayCapNhat;
    }
}
