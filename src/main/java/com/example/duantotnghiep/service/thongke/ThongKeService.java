package com.example.duantotnghiep.service.thongke;

import com.example.duantotnghiep.response.DoanhThuResponse;
import com.example.duantotnghiep.response.SanPhamBanChayResponse;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public interface ThongKeService {

    List<DoanhThuResponse> doanhThu(Date ngayBd, Date ngayKt);

    List<SanPhamBanChayResponse> listSanPhamBanChay();

    BigDecimal tongTienDonHomNay();

    BigDecimal tongTienDonTuanNay();

    BigDecimal tongTienDonThangNay();

    BigDecimal tongTienDonNamNay();

    Integer soDonHomNay();

    Integer soDonTuanNay();

    Integer soDonThangNay();

    Integer soDonNamNay();


    Integer soDonHuyHomNay();

    Integer soDonHuyTuanNay();

    Integer soDonHuyThangNay();

    Integer soDonHuyNamNay();

    Integer soSanPhamBanRaHomNay();

    Integer soSanPhamBanRaTuanNay();

    Integer soSanPhamBanRaThangNay();

    Integer soSanPhamBanNamNay();

    Integer demSoHoaDonChoXacNhan();

    Integer demSoHoaDonXacNhan();

    Integer demSoHoaDonDangGiao();

    Integer demSoHoaDonChoGiaoHang();

    Integer demSoHoaDonThanhCong();

    Integer demSoHoaDonDaHuy();

}
