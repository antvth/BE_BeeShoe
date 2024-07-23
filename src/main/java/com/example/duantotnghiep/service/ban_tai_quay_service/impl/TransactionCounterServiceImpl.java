package com.example.duantotnghiep.service.ban_tai_quay_service.impl;

import com.example.duantotnghiep.entity.HinhThucThanhToan;
import com.example.duantotnghiep.entity.HoaDon;
import com.example.duantotnghiep.entity.LoaiHinhThucThanhToan;
import com.example.duantotnghiep.entity.TaiKhoan;
import com.example.duantotnghiep.repository.*;
import com.example.duantotnghiep.request.TransactionRequest;
import com.example.duantotnghiep.response.MessageResponse;
import com.example.duantotnghiep.response.TransactionResponse;
import com.example.duantotnghiep.service.audi_log_service.AuditLogService;
import com.example.duantotnghiep.service.ban_tai_quay_service.TransactionCounterService;
import com.opencsv.exceptions.CsvValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.*;

@Service
public class TransactionCounterServiceImpl implements TransactionCounterService {

    @Autowired
    private HinhThucThanhToanRepository hinhThucThanhToanRepository;

    @Autowired
    private KhachHangRepository khachHangRepository;

    @Autowired
    private HoaDonRepository hoaDonRepository;

    @Autowired
    private AuditLogService auditLogService;

    @Autowired
    private TaiKhoanRepository taiKhoanRepository;

    @Autowired
    private LoaiHinhThucThanhToanRepository loaiHinhThucThanhToanRepository;

    @Override
    public MessageResponse createTransaction(UUID idHoaDon, UUID id, TransactionRequest transactionRequest, String username) throws IOException, CsvValidationException {
        Optional<TaiKhoan> nhanVien = taiKhoanRepository.findByUsername(username);
        Optional<TaiKhoan> taiKhoan = khachHangRepository.findById(id);
        Optional<HoaDon> hoaDon = hoaDonRepository.findById(idHoaDon);

        LoaiHinhThucThanhToan loaiHinhThucThanhToan = new LoaiHinhThucThanhToan();
        loaiHinhThucThanhToan.setId(UUID.randomUUID());
        loaiHinhThucThanhToan.setNgayTao(new Date(System.currentTimeMillis()));
        loaiHinhThucThanhToan.setTenLoai("Khách thanh toán");
        loaiHinhThucThanhToanRepository.save(loaiHinhThucThanhToan);

        HinhThucThanhToan hinhThucThanhToan = new HinhThucThanhToan();
        hinhThucThanhToan.setId(UUID.randomUUID());
        hinhThucThanhToan.setNgayThanhToan(new Date(System.currentTimeMillis()));
        hinhThucThanhToan.setTaiKhoan(taiKhoan.get());
        hinhThucThanhToan.setTongSoTien(transactionRequest.getSoTien());
        hinhThucThanhToan.setPhuongThucThanhToan(1);
        hinhThucThanhToan.setCodeTransaction(VnPayConfigTaiQuay.getRandomNumber(8));
        hinhThucThanhToan.setHoaDon(hoaDon.get());
        hinhThucThanhToan.setTrangThai(1);
        hinhThucThanhToan.setLoaiHinhThucThanhToan(loaiHinhThucThanhToan);
        hinhThucThanhToanRepository.save(hinhThucThanhToan);
        auditLogService.writeAuditLogHoadon( nhanVien.get().getMaTaiKhoan(), hoaDon.get().getMa(), "Xác nhận thanh toán", hoaDon.get().getMa(),  "Số tiền: " + FormatNumber.formatBigDecimal(transactionRequest.getSoTien()) + "đ", "Thanh toán: Tiền mặt" , "", "");

        return MessageResponse.builder().message("Thanh toán thành công").build();
    }

    @Override
    public List<TransactionResponse> findAllTran(UUID id) {
        return hinhThucThanhToanRepository.findAllTran(id);
    }

    @Override
    public MessageResponse cashVnPay(UUID idHoaDon, UUID id, BigDecimal vnpAmount, String maGiaoDinh, String username) throws IOException, CsvValidationException {
        Optional<TaiKhoan> nhanVien = taiKhoanRepository.findByUsername(username);
        Optional<TaiKhoan> taiKhoan = khachHangRepository.findById(id);
        Optional<HoaDon> hoaDon = hoaDonRepository.findById(idHoaDon);

        LoaiHinhThucThanhToan loaiHinhThucThanhToan = new LoaiHinhThucThanhToan();
        loaiHinhThucThanhToan.setId(UUID.randomUUID());
        loaiHinhThucThanhToan.setNgayTao(new Date(System.currentTimeMillis()));
        loaiHinhThucThanhToan.setTenLoai("Khách thanh toán");
        loaiHinhThucThanhToanRepository.save(loaiHinhThucThanhToan);

        HinhThucThanhToan hinhThucThanhToan = new HinhThucThanhToan();
        hinhThucThanhToan.setId(UUID.randomUUID());
        hinhThucThanhToan.setNgayThanhToan(new Date(System.currentTimeMillis()));
        hinhThucThanhToan.setTaiKhoan(taiKhoan.get());
        hinhThucThanhToan.setTongSoTien( vnpAmount);
        hinhThucThanhToan.setPhuongThucThanhToan(2);
        hinhThucThanhToan.setCodeTransaction(maGiaoDinh);
        hinhThucThanhToan.setHoaDon(hoaDon.get());
        hinhThucThanhToan.setTrangThai(2);
        hinhThucThanhToan.setLoaiHinhThucThanhToan(loaiHinhThucThanhToan);
        hinhThucThanhToanRepository.save(hinhThucThanhToan);
        auditLogService.writeAuditLogHoadon( nhanVien.get().getMaTaiKhoan(), hoaDon.get().getMa(), "Xác nhận thanh toán", hoaDon.get().getMa(),  "Số tiền: " + FormatNumber.formatBigDecimal(vnpAmount) + "đ", "Thanh toán: Chuyển khoản" , "", "");
        return MessageResponse.builder().message("Thanh toán thành công").build();
    }

}
