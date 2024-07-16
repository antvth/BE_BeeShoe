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
public class ProductUpdateResponse {

    private String maSanPham;

    private String productName;

    private String describe;

    private BigDecimal price;

    private String tenXuatXu;

    private String tenDanhMuc;

    private String tenThuongHieu;

    private String tenKieuDe;
}
