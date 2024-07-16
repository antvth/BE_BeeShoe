package com.example.duantotnghiep.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

public interface DoanhThuResponse {

    Date getNgay();

    BigDecimal getDoanhThu();

}
