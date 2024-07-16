package com.example.duantotnghiep.service.ban_tai_quay_service.impl;

import com.example.duantotnghiep.entity.*;
import com.example.duantotnghiep.repository.*;
import com.example.duantotnghiep.response.*;
import com.example.duantotnghiep.service.audi_log_service.AuditLogService;
import com.example.duantotnghiep.service.ban_tai_quay_service.OrderCounterService;
import com.opencsv.exceptions.CsvValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

@Service
public class OrderCounterServiceImpl implements OrderCounterService {



    @Autowired
    private GioHangChiTietRepository gioHangChiTietRepository;



    @Autowired
    private GioHangRepository gioHangRepository;



    @Autowired
    private JavaMailSender javaMailSender;


    @Override
    @Transactional
    // TODO Thêm hóa đơn tại quầy
    public OrderCounterCResponse taoHoaDon(String name) throws IOException, CsvValidationException {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        Optional<TaiKhoan> findByNhanVien = taiKhoanRepository.findByUsername(name);

        Optional<LoaiDon> findByLoaiDon = loaiDonRepository.findByTrangThai(TypeOrderEnums.TAI_QUAY.getValue());

        TaiKhoan taiKhoan = new TaiKhoan();
        taiKhoan.setId(UUID.randomUUID());
        taiKhoan.setName("Khách lẻ");
        taiKhoan.setTrangThai(1);
        taiKhoanRepository.save(taiKhoan);

        Random rand = new Random();
        int randomNumber = rand.nextInt(100000);
        String maHd = String.format("HD%03d", randomNumber);
        HoaDon hoaDon = new HoaDon();
        hoaDon.setId(UUID.randomUUID());
        hoaDon.setMa(maHd);
        hoaDon.setTenNguoiNhan("Khách lẻ");
        hoaDon.setNgayTao(timestamp);
        hoaDon.setTienGiamGia(BigDecimal.ZERO);
        hoaDon.setTrangThai(StatusOrderEnums.CHO_XAC_NHAN.getValue());
        hoaDon.setTaiKhoanNhanVien(findByNhanVien.get());
        hoaDon.setTaiKhoanKhachHang(taiKhoan);
        hoaDon.setLoaiDon(findByLoaiDon.get());
        hoaDonRepository.save(hoaDon);

        GioHang gioHang = new GioHang();
        gioHang.setId(UUID.randomUUID());
        gioHang.setTaiKhoan(taiKhoan);
        gioHang.setNgayTao(timestamp);
        gioHang.setTrangThai(StatusCartEnums.CHUA_CO_SAN_PHAM.getValue());
        gioHangRepository.save(gioHang);

        TrangThaiHoaDon trangThaiHoaDon = new TrangThaiHoaDon();
        trangThaiHoaDon.setId(UUID.randomUUID());
        trangThaiHoaDon.setTrangThai(StatusOrderDetailEnums.CHO_XAC_NHAN.getValue());
        trangThaiHoaDon.setThoiGian(timestamp);
        trangThaiHoaDon.setGhiChu("Nhân viên tạo đơn cho khách");
        trangThaiHoaDon.setHoaDon(hoaDon);
        trangThaiHoaDon.setUsername(findByNhanVien.get().getUsername());
        trangThaiHoaDonRepository.save(trangThaiHoaDon);
        auditLogService.writeAuditLogHoadon(findByNhanVien.get().getMaTaiKhoan(), hoaDon.getMa(), "Nhân viên tạo hóa đơn", hoaDon.getMa(), "", "", "", "");
        return OrderCounterCResponse.builder().id(hoaDon.getId()).idKhach(taiKhoan.getId()).build();
    }

    @Override
    public List<HoaDonResponse> viewHoaDonTaiQuay(Integer pageNumber, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<HoaDonResponse> hoaDonResponses = hoaDonRepository.viewHoaDonTaiQuay(pageable);
        return hoaDonResponses.getContent();
    }

    @Override
    public List<HoaDonResponse> findByCodeOrder(String ma) {
        return hoaDonRepository.findByCodeOrder(ma);
    }



    @Override
    public MessageResponse updateHoaDon(UUID idHoaDon, HoaDonThanhToanRequest hoaDonThanhToanRequest, String username) throws IOException, CsvValidationException {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        Optional<HoaDon> hoaDon = hoaDonRepository.findById(idHoaDon);
        Optional<TaiKhoan> findByNhanVien = taiKhoanRepository.findByUsername(username);
        hoaDon.get().setNgayNhan(timestamp);
        hoaDon.get().setNgayCapNhap(timestamp);
        hoaDon.get().setTienKhachTra(hoaDonThanhToanRequest.getTienKhachTra());
        hoaDon.get().setTienThua(hoaDonThanhToanRequest.getTienThua());
        hoaDon.get().setThanhTien(hoaDonThanhToanRequest.getTongTien());
        hoaDon.get().setTenNguoiNhan(hoaDonThanhToanRequest.getHoTen());
        hoaDon.get().setSdtNguoiNhan(hoaDonThanhToanRequest.getSoDienThoai());
        hoaDon.get().setDiaChi(hoaDonThanhToanRequest.getDiaChi());
        hoaDon.get().setTrangThai(5);
        if (hoaDon.get().getVoucher() != null) {
            Voucher voucher = voucherRepository.findById(hoaDon.get().getVoucher().getId()).get();
            if (voucher != null) {
                voucher.setSoLuongDung(voucher.getSoLuongDung() + 1);
                voucherRepository.save(voucher);
            }
        }
        hoaDonRepository.save(hoaDon.get());

        auditLogService.writeAuditLogHoadon(findByNhanVien.get().getMaTaiKhoan(), hoaDon.get().getMa(), "Xác nhận thanh toán hóa đơn tại quầy", hoaDon.get().getMa(),
                "Tên người nhận: " + hoaDonThanhToanRequest.getHoTen(),
                hoaDonThanhToanRequest.getSoDienThoai() == null ? "SĐT: Không có dữ liệu" : "SĐT: " + hoaDonThanhToanRequest.getSoDienThoai(),
                hoaDonThanhToanRequest.getDiaChi() == null ? "Địa chỉ: Không có dữ liệu" : "Địa chỉ: " + hoaDonThanhToanRequest.getDiaChi(), "Tổng tiền: " + FormatNumber.formatBigDecimal(hoaDonThanhToanRequest.getTongTien()) + "đ");

        for (UUID idGioHangChiTiet : hoaDonThanhToanRequest.getGioHangChiTietList()) {
            Optional<GioHangChiTiet> gioHangChiTiet = gioHangChiTietRepository.findById(idGioHangChiTiet);
            gioHangChiTiet.get().setTrangThai(StatusCartDetailEnums.DA_THANH_TOAN.getValue());
            gioHangChiTietRepository.save(gioHangChiTiet.get());
            Optional<GioHang> gioHang = gioHangRepository.findById(gioHangChiTiet.get().getGioHang().getId());
            gioHang.get().setTrangThai(StatusCartEnums.DA_THANH_TOAN.getValue());
            gioHangRepository.save(gioHang.get());
            if (gioHangChiTiet.isPresent()) {
                HoaDonChiTiet hoaDonChiTiet = new HoaDonChiTiet();
                hoaDonChiTiet.setId(UUID.randomUUID());
                hoaDonChiTiet.setHoaDon(hoaDon.get());
                hoaDonChiTiet.setSanPhamChiTiet(gioHangChiTiet.get().getSanPhamChiTiet());
                hoaDonChiTiet.setDonGia(gioHangChiTiet.get().getSanPhamChiTiet().getSanPham().getGiaBan());
                hoaDonChiTiet.setDonGiaSauGiam(gioHangChiTiet.get().getSanPhamChiTiet().getSanPham().getGiaBan().subtract(new BigDecimal(getGiaGiamCuoiCung(gioHangChiTiet.get().getSanPhamChiTiet().getSanPham().getId()))));
                hoaDonChiTiet.setSoLuong(gioHangChiTiet.get().getSoLuong());
                hoaDonChiTiet.setTrangThai(5);
                hoaDonChiTietRepository.save(hoaDonChiTiet);

                SanPhamChiTiet sanPhamChiTiet = chiTietSanPhamRepository.findById(gioHangChiTiet.get().getSanPhamChiTiet().getId()).get();
                sanPhamChiTiet.setSoLuong(sanPhamChiTiet.getSoLuong() - gioHangChiTiet.get().getSoLuong());
                chiTietSanPhamRepository.save(sanPhamChiTiet);
            }
        }

        TrangThaiHoaDon trangThaiHoaDon = new TrangThaiHoaDon();
        trangThaiHoaDon.setId(UUID.randomUUID());
        trangThaiHoaDon.setTrangThai(StatusOrderDetailEnums.HOAN_THANH.getValue());
        trangThaiHoaDon.setThoiGian(timestamp);
        trangThaiHoaDon.setUsername(hoaDon.get().getTaiKhoanNhanVien().getMaTaiKhoan());
        trangThaiHoaDon.setGhiChu("Nhân viên xác nhận đơn cho khách");
        trangThaiHoaDon.setHoaDon(hoaDon.get());
        trangThaiHoaDonRepository.save(trangThaiHoaDon);
        return MessageResponse.builder().message("Thanh Toán Thành Công").build();
    }



}
