package com.example.duantotnghiep.service.hoa_don_service;

import com.example.duantotnghiep.entity.HoaDon;
import com.example.duantotnghiep.entity.HoaDonChiTiet;
import com.example.duantotnghiep.entity.SanPhamChiTiet;
import com.example.duantotnghiep.entity.Voucher;
import com.example.duantotnghiep.request.TraHangRequest;
import com.example.duantotnghiep.request.TrangThaiHoaDonRequest;
import com.example.duantotnghiep.request.TransactionRequest;
import com.example.duantotnghiep.request.XacNhanThanhToanRequest;
import com.example.duantotnghiep.response.*;
import com.opencsv.exceptions.CsvValidationException;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public interface HoaDonChiTietService {
    ThongTinDonHang getThongTinDonHang(UUID idHoaDon);

    List<SanPhamHoaDonChiTietResponse> getSanPhamHDCT(Integer pageNumber, Integer pageSize,UUID idHoaDon);

    List<HinhThucThanhToanResponse> getLichSuThanhToan(UUID idHoaDon);

    List<TrangThaiHoaDonResponse> getAllTrangThaiHoaDon(UUID idHoaDon);

    void confirmThanhToan(UUID hoadonId, XacNhanThanhToanRequest request);

    MoneyResponse getMoneyByHoaDon(UUID idHoaDon);

    MessageResponse themSanPhamVaoHoaDonChiTiet(UUID idHoaDon, UUID idSanPhamChiTiet, int soLuong, String username) throws IOException, CsvValidationException;

    void capNhatSoLuong(UUID idHoaDonChiTiet, int soLuongMoi, String username) throws IOException, CsvValidationException;

    HoaDon findByHoaDon(UUID id);

    ProductDetailDTOResponse getDetailSanPham(UUID idhdct);

    MessageResponse createOrUpdate(UUID id, UUID idhdct, TraHangRequest traHangResponse, String username) throws IOException, CsvValidationException;

    void deleteOrderDetail(UUID idHoaDon, UUID id, String username) throws IOException, CsvValidationException;

    boolean traHang(UUID id);

    OrderDetailUpdate orderDetailUpdate(UUID id);

    List<NhanVienOrderResponse> taiKhoanList();

    MessageResponse updateNhanVien(UUID idHoaDon, UUID idNhanVien, String username) throws CsvValidationException, IOException;

    BigDecimal tongTienHang(UUID id);

    MessageResponse createTransaction(UUID idHoaDon, UUID id, TransactionRequest transactionRequest, String username) throws CsvValidationException, IOException;

    MessageResponse comfirmStatusHuyDon(UUID idHoaDon, TrangThaiHoaDonRequest request, String username) throws IOException, CsvValidationException;

    MessageResponse rollBackOrder(UUID idHoaDon, TrangThaiHoaDonRequest request);

    void applyVoucherAndPayment(HoaDon hoaDon, Voucher voucher, BigDecimal ktVoucher);

    void prepareOrderDetails(HoaDon hoaDon, TraHangRequest traHangRequest, HoaDonChiTiet hoaDonChiTiet, SanPhamChiTiet sanPhamChiTiet, int count);
}
