package com.example.duantotnghiep.service.thongke.impl;

import com.example.duantotnghiep.repository.ThongKeRepository;
import com.example.duantotnghiep.response.DoanhThuResponse;
import com.example.duantotnghiep.response.SanPhamBanChayResponse;
import com.example.duantotnghiep.service.thongke.ThongKeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ThongKeServiceImpl implements ThongKeService {

    @Autowired
    private ThongKeRepository thongKeRepository;

    @Override
    public List<DoanhThuResponse> doanhThu(Date ngayBd, Date ngayKt) {
        return thongKeRepository.doanhThu(ngayBd, ngayKt);
    }

    @Override
    public List<SanPhamBanChayResponse> listSanPhamBanChay() {
        List<SanPhamBanChayResponse> sanPhamBanChayResponseList = thongKeRepository.sanPhamBanChay();
        List<SanPhamBanChayResponse> listSanPhamBanChay = new ArrayList<>();

        for (SanPhamBanChayResponse sp : sanPhamBanChayResponseList) {
            SanPhamBanChayResponse sanPhamBanChayResponse = new SanPhamBanChayResponse(
                    sp.getImage(),
                    sp.getProductName(),
                    sp.getPrice(),
                    sp.getGiaGiam(),
                    sp.getSoLuongDaBan(),
                    sp.getGiaGiam().multiply(new BigDecimal(sp.getSoLuongDaBan())));
            listSanPhamBanChay.add(sanPhamBanChayResponse);
        }
        return listSanPhamBanChay;
    }

    @Override
    public BigDecimal tongTienDonHomNay() {
        return thongKeRepository.tongTienDonHomNay();
    }

    @Override
    public BigDecimal tongTienDonTuanNay() {
        return thongKeRepository.tongTienDonTuanNay();
    }

    @Override
    public BigDecimal tongTienDonThangNay() {
        return thongKeRepository.tongTienDonThangNay();
    }

    @Override
    public BigDecimal tongTienDonNamNay() {
        return thongKeRepository.tongTienDonNamNay();
    }

    @Override
    public Integer soDonHomNay() {
        return thongKeRepository.soDonHomNay();
    }

    @Override
    public Integer soDonTuanNay() {
        return thongKeRepository.soDonTuanNay();
    }

    @Override
    public Integer soDonThangNay() {
        return thongKeRepository.soDonThangNay();
    }

    @Override
    public Integer soDonNamNay() {
        return thongKeRepository.soDonNamNay();
    }

    @Override
    public Integer soDonHuyHomNay() {
        return thongKeRepository.soDonHuyHomNay();
    }

    @Override
    public Integer soDonHuyTuanNay() {
        return thongKeRepository.soDonHuyTuanNay();
    }

    @Override
    public Integer soDonHuyThangNay() {
        return thongKeRepository.soDonHuyThangNay();
    }

    @Override
    public Integer soDonHuyNamNay() {
        return thongKeRepository.soDonHuyNamNay();
    }

    @Override
    public Integer soSanPhamBanRaHomNay() {
        return thongKeRepository.soSanPhamBanRaHomNay();
    }

    @Override
    public Integer soSanPhamBanRaTuanNay() {
        return thongKeRepository.soSanPhamBanRaTuanNay();
    }

    @Override
    public Integer soSanPhamBanRaThangNay() {
        return thongKeRepository.soSanPhamBanRaThangNay();
    }

    @Override
    public Integer soSanPhamBanNamNay() {
        return thongKeRepository.soSanPhamBanRaNamNay();
    }

    @Override
    public Integer demSoHoaDonChoXacNhan() {
        return thongKeRepository.demSoHoaDonChoXacNhan();
    }

    @Override
    public Integer demSoHoaDonXacNhan() {
        return thongKeRepository.demSoHoaDonXacNhan();
    }

    @Override
    public Integer demSoHoaDonChoGiaoHang() {
        return thongKeRepository.demSoHoaDonChoGiaoHang();
    }

    @Override
    public Integer demSoHoaDonDangGiao() {
        return thongKeRepository.demSoHoaDonDangGiao();
    }

    @Override
    public Integer demSoHoaDonThanhCong() {
        return thongKeRepository.demSoHoaDonThanhCong();
    }

    @Override
    public Integer demSoHoaDonDaHuy() {
        return thongKeRepository.demSoHoaDonDaHuy();
    }
}
