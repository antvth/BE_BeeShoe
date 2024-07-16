package com.example.duantotnghiep.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductShopResponse {

    private UUID id;

    private String imgage;

    private String tenSanPham;

    private BigDecimal giaBan;

    private BigDecimal giaGiamGia;

}
