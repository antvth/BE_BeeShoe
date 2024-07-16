package com.example.duantotnghiep.entity;

import com.example.duantotnghiep.enums.TypeAccountEnum;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "loaitaikhoan")
public class LoaiTaiKhoan {

    @Id
    @Column(name = "id")
    private UUID id;

    @Column(name = "tenloaitaikhoan")
    @Enumerated(EnumType.STRING)
    private TypeAccountEnum name;

    @Column(name = "trangthai")
    private Integer trangThai;

    @Column(name = "ngaytao")
    private Date ngayTao;

    @Column(name = "ngaycapnhat")
    private Date ngayCapNhat;

    @OneToMany(mappedBy = "loaiTaiKhoan", fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<TaiKhoan> taiKhoanList;

}
