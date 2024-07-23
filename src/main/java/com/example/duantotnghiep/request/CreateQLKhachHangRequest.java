package com.example.duantotnghiep.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CreateQLKhachHangRequest {

    private String ten;

    private String email;

    private String soDienThoai;

    private Boolean gioiTinh;

    private Date ngaySinh;

    private Integer trangThai;

    private String diaChi;

}
