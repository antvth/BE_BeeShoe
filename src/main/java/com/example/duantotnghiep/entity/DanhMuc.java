package com.example.duantotnghiep.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
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
@Table(name = "danhmuc")
public class DanhMuc {

    @Id
    @Column(name = "id")
    private UUID id;

    @Column(name = "tendanhmuc")
    private String tenDanhMuc;

    @Column(name = "trangthai")
    private Integer trangThai;

    @Column(name = "ngaytao")
    private Date ngayTao;

    @Column(name = "ngaycapnhat")
    private Date ngayCapNhat;

    @OneToMany(mappedBy = "danhMuc",fetch = FetchType.LAZY)
    @JsonManagedReference
    List<SanPham> sanPhamList;

    public DanhMuc(UUID id, String tenDanhMuc, Integer trangThai, Date ngayTao, Date ngayCapNhat) {
        this.id = id;
        this.tenDanhMuc = tenDanhMuc;
        this.trangThai = trangThai;
        this.ngayTao = ngayTao;
        this.ngayCapNhat = ngayCapNhat;
    }
}
