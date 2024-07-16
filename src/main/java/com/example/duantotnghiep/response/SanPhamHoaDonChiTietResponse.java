package com.example.duantotnghiep.response;

import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SanPhamHoaDonChiTietResponse {

    private UUID idHoaDonChiTiet;

    private String tenImage;

    private String tenSanPham;

    private BigDecimal giaBan;

    private BigDecimal donGiaSauGiam;

    private Integer soLuong;

    private Integer trangThai;

}
