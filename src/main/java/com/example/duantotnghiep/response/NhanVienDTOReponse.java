package com.example.duantotnghiep.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class NhanVienDTOReponse {

    private UUID id;

    private String maNhanVien;

    private String fullName;

    private String soDienThoai;

    private Boolean gioiTinh;

    private Date ngaySinh;

    private Integer trangThai;

    private String email;

    private String image;

    private String diaChi;

}
