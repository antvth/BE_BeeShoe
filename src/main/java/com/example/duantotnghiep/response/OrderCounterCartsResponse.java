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
public class OrderCounterCartsResponse {

    private UUID idKhach;

    private String maHoaDon;

    private BigDecimal tienGiamGia;

    private String tenKhach;

    private Date ngayTao;

    private String diaChi;

    private String email;

    private String soDienThoai;

    private String tinh;

    private String huyen;

    private String phuong;

}
