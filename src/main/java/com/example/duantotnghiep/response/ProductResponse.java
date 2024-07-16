package com.example.duantotnghiep.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponse {

    private UUID id;

    private String image;

    private String maSanPham;

    private String tenSanPham;

    private BigDecimal giaBan;

    private Date ngayTao;

    private Integer trangThai;

    private BigDecimal giaGiam;
}
