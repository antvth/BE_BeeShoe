package com.example.duantotnghiep.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SanPhamDTOResponse {
    private UUID id;

    private String maSanPham;

    private String tenSanPham;

    private String image;

    private BigDecimal gia;

    private Date ngayTao;

    private Date ngayCapNhat;


}
