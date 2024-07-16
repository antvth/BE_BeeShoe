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
public class OrderDetailUpdate {

    private String hoVatenClient;

    private String sdtClient;

    private String email;

    private String tenNguoiGiao;

    private String soDienThoaiGiao;

    private BigDecimal tienShip;

    private String diaChi;
}
