package com.example.duantotnghiep.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductDetailDTOResponse {

    private UUID id;

    private String tenSanPham;

    private String tenImage;

    private BigDecimal donGia;

    private BigDecimal donGiaSauGiam;

    private Integer size;

    private String tenChatLieu;

    private String tenMauSac;

    private Integer soLuong;

}
