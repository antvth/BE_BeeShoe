package com.example.duantotnghiep.request;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VoucherRequest {

    private String maVoucher;
    private String tenVoucher;
    private Date ngayBatDau;
    private Date ngayKetThuc;
    private Date ngayCapNhap;
    private Integer soLuongMa;
    private Long giaTriToiThieuDonhang;
    private Long giaTriGiam;
    private Integer hinhThucGiam;
    private Integer trangThai;
}
