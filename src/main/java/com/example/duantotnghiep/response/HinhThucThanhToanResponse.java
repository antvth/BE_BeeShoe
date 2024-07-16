package com.example.duantotnghiep.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class HinhThucThanhToanResponse {

    String tenLoai;

    String ma;

    BigDecimal soTienTra;

    Date ngayTao;

    Integer phuongThucThanhToan;

    String ghiChu;

    String name;
}
