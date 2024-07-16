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
public class VoucherCounterResponse {

    private UUID id;

    private String voucherCode;

    private String nameVoucher;

    private Integer style;

    private Integer soLuong;

    private Integer soLuongDung;

    private Long price;

    private Long priceOrder;

    private Date dateStart;

    private Date dateEnd;
}
