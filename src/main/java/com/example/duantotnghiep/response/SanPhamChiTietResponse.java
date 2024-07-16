package com.example.duantotnghiep.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SanPhamChiTietResponse {

    private String tenSanPham;

    private Integer soLuong;

    private BigDecimal giaBan;

    private Integer size;

    private String mauSac;

    private String chatLieu;

    private Integer trangThai;
}
