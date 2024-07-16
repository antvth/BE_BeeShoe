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
public class MoneyResponse {

    private BigDecimal tongTien;

    private BigDecimal tienShip;

    private BigDecimal tienGiamGia;

}
