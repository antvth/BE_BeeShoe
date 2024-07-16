package com.example.duantotnghiep.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@NoArgsConstructor
@Getter
@Setter
public class SanPhamBanChayResponse {

    private String image;

    private String productName;

    private BigDecimal price;

    private BigDecimal giaGiam;

    private Long soLuongDaBan;

    private BigDecimal doanhSo;

    public SanPhamBanChayResponse(String image, String productName, BigDecimal price, BigDecimal giaGiam, Long soLuongDaBan) {
        this.image = image;
        this.productName = productName;
        this.price = price;
        this.giaGiam = giaGiam;
        this.soLuongDaBan = soLuongDaBan;
    }

    public SanPhamBanChayResponse(String image, String productName, BigDecimal price, BigDecimal giaGiam, Long soLuongDaBan, BigDecimal doanhSo) {
        this.image = image;
        this.productName = productName;
        this.price = price;
        this.giaGiam = giaGiam;
        this.soLuongDaBan = soLuongDaBan;
        this.doanhSo = doanhSo;
    }
}
