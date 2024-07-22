package com.example.duantotnghiep.service.ban_tai_quay_service.impl;

import com.example.duantotnghiep.entity.*;
import com.example.duantotnghiep.enums.TypeAccountEnum;
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
    private HoaDonRepository hoaDonRepository;

    @Autowired
    private TaiKhoanRepository taiKhoanRepository;

    @Autowired
    private GioHangChiTietRepository gioHangChiTietRepository;

    @Autowired
    private HoaDonChiTietRepository hoaDonChiTietRepository;

    @Autowired
    private AuditLogService auditLogService;

    @Autowired
    private LoaiDonRepository loaiDonRepository;

    @Autowired
    private TrangThaiHoaDonRepository trangThaiHoaDonRepository;

    @Autowired
    private GioHangRepository gioHangRepository;

    @Autowired
    private ChiTietSanPhamRepository chiTietSanPhamRepository;

    @Autowired
    private SpGiamGiaRepository spGiamGiaRepository;

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private LoaiTaiKhoanRepository loaiTaiKhoanRepository;

    @Autowired
    private DiaChiRepository diaChiRepository;

    @Autowired
    private KhachHangRepository khachHangRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private VoucherRepository voucherRepository;

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

    public Long getGiaGiamCuoiCung(UUID id) {
        long tongTienGiam = 0L;
        List<SpGiamGia> spGiamGiaList = spGiamGiaRepository.findBySanPham_Id(id);

        for (SpGiamGia spGiamGia : spGiamGiaList) {
            // Cập nhật tổng tiền giảm đúng cách, không khai báo lại biến tongTienGiam
            if (spGiamGia.getGiaGiam() == null) {
                return tongTienGiam;
            }
            if (spGiamGia.getGiaGiam() != null) {
                tongTienGiam += spGiamGia.getGiaGiam().longValue();
            }

        }

        return tongTienGiam;
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

    @Override
    public MessageResponse updateHoaDonGiaoTaiQuay(UUID idHoaDon, HoaDonGiaoThanhToanRequest hoaDonGiaoThanhToanRequest, String username, boolean sendEmail) throws IOException, CsvValidationException {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        Optional<HoaDon> hoaDon = hoaDonRepository.findById(idHoaDon);
        Optional<TaiKhoan> findByNhanVien = taiKhoanRepository.findByUsername(username);

        hoaDon.get().setNgayNhan(timestamp);
        hoaDon.get().setNgayCapNhap(timestamp);
        hoaDon.get().setTienKhachTra(hoaDonGiaoThanhToanRequest.getTienKhachTra());
        hoaDon.get().setTienThua(hoaDonGiaoThanhToanRequest.getTienThua());
        hoaDon.get().setThanhTien(hoaDonGiaoThanhToanRequest.getTongTien());
        hoaDon.get().setTenNguoiNhan(hoaDonGiaoThanhToanRequest.getTenKhach());
        hoaDon.get().setSdtNguoiNhan(hoaDonGiaoThanhToanRequest.getSoDienThoai());
        hoaDon.get().setDiaChi(hoaDonGiaoThanhToanRequest.getDiaChi());
        hoaDon.get().setTienShip(hoaDonGiaoThanhToanRequest.getTienGiao());
        hoaDon.get().setEmail(hoaDonGiaoThanhToanRequest.getEmail());
        hoaDon.get().setTrangThai(StatusOrderDetailEnums.XAC_NHAN.getValue());
        auditLogService.writeAuditLogHoadon(findByNhanVien.get().getMaTaiKhoan(), hoaDon.get().getMa(), "Xác nhận thanh toán hóa đơn giao", hoaDon.get().getMa(),
                "Tên người nhận: " + hoaDonGiaoThanhToanRequest.getTenKhach(),
                "SĐT: " + hoaDonGiaoThanhToanRequest.getSoDienThoai(),
                "Địa chỉ: " + hoaDonGiaoThanhToanRequest.getDiaChi(), "Phí vận chuyển: " + FormatNumber.formatBigDecimal(hoaDonGiaoThanhToanRequest.getTienGiao()) + "đ - Tổng tiền: " + FormatNumber.formatBigDecimal(hoaDonGiaoThanhToanRequest.getTongTien()) + "đ");
        if (hoaDon.get().getVoucher() != null) {
            Voucher voucher = voucherRepository.findById(hoaDon.get().getVoucher().getId()).get();
            if (voucher != null) {
                voucher.setSoLuongDung(voucher.getSoLuongDung() + 1);
                voucherRepository.save(voucher);
            }
        }
        hoaDonRepository.save(hoaDon.get());

        for (UUID idGioHangChiTiet : hoaDonGiaoThanhToanRequest.getGioHangChiTietList()) {
            Optional<GioHangChiTiet> gioHangChiTiet = gioHangChiTietRepository.findById(idGioHangChiTiet);
            gioHangChiTiet.get().setTrangThai(StatusCartDetailEnums.DA_THANH_TOAN.getValue());
            gioHangChiTietRepository.save(gioHangChiTiet.get());
            Optional<GioHang> gioHang = gioHangRepository.findById(gioHangChiTiet.get().getGioHang().getId());
            gioHang.get().setNgayCapNhat(timestamp);
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
                hoaDonChiTiet.setTrangThai(StatusOrderDetailEnums.XAC_NHAN.getValue());
                hoaDonChiTietRepository.save(hoaDonChiTiet);

                SanPhamChiTiet sanPhamChiTiet = chiTietSanPhamRepository.findById(gioHangChiTiet.get().getSanPhamChiTiet().getId()).get();
                System.out.println(gioHangChiTiet.get().getSanPhamChiTiet().getId());
                sanPhamChiTiet.setSoLuong(sanPhamChiTiet.getSoLuong() - gioHangChiTiet.get().getSoLuong());
                chiTietSanPhamRepository.save(sanPhamChiTiet);
            }
        }

        TrangThaiHoaDon trangThaiHoaDon = new TrangThaiHoaDon();
        trangThaiHoaDon.setId(UUID.randomUUID());
        trangThaiHoaDon.setTrangThai(StatusOrderDetailEnums.XAC_NHAN.getValue());
        trangThaiHoaDon.setThoiGian(timestamp);
        trangThaiHoaDon.setUsername(hoaDon.get().getTaiKhoanNhanVien().getMaTaiKhoan());
        trangThaiHoaDon.setGhiChu("Nhân viên xác nhận đơn cho khách");
        trangThaiHoaDon.setHoaDon(hoaDon.get());
        trangThaiHoaDonRepository.save(trangThaiHoaDon);
//        if (sendEmail) {
//            SendEmailOrder.sendEmailOrder(hoaDon.get(), javaMailSender);
//            System.out.println("gửi mail");
//        }
        String normalized = RemoveDiacritics.removeDiacritics(hoaDonGiaoThanhToanRequest.getTenKhach());

        String converted = normalized.toLowerCase().replaceAll("\\s", "");
        List<TaiKhoan> taiKhoans = khachHangRepository.listKhachHang();
        LoaiTaiKhoan loaiTaiKhoan = loaiTaiKhoanRepository.findByName(TypeAccountEnum.USER).get();
        TaiKhoan taiKhoan = new TaiKhoan();
        taiKhoan.setId(UUID.randomUUID());
        taiKhoan.setName(hoaDonGiaoThanhToanRequest.getTenKhach());
        taiKhoan.setEmail(hoaDonGiaoThanhToanRequest.getEmail());
        taiKhoan.setSoDienThoai(hoaDonGiaoThanhToanRequest.getSoDienThoai());
        taiKhoan.setTrangThai(1);
        taiKhoan.setMaTaiKhoan(converted + taiKhoans.size() + 1);
        taiKhoan.setUsername(converted + taiKhoans.size() + 1);
        taiKhoan.setMatKhau(passwordEncoder.encode(converted));
        taiKhoan.setLoaiTaiKhoan(loaiTaiKhoan);
        khachHangRepository.save(taiKhoan);

        DiaChi diaChi = new DiaChi();
        diaChi.setId(UUID.randomUUID());
        diaChi.setDiaChi(hoaDonGiaoThanhToanRequest.getDiaChi());
        diaChi.setTaiKhoan(taiKhoan);
        diaChi.setTrangThai(1);
        diaChiRepository.save(diaChi);
//        if (sendEmail) {
//            SendConfirmationEmail.sendConfirmationEmailStatic(taiKhoan.getEmail(), taiKhoan.getUsername(), converted, javaMailSender);
//            System.out.println("gửi mail");
//        }
        return MessageResponse.builder().message("Thanh Toán Thành Công").build();
    }

    public OrderCounterCartsResponse findByHoaDon(UUID id) {
        return hoaDonRepository.findByHoaDon(id);
    }

    @Override
    public IdGioHangResponse showIdGioHangCt(UUID id) {
        return hoaDonRepository.showIdGioHangCt(id);
    }

    @Override
    public MessageResponse removeOrder(UUID id, String username) throws IOException, CsvValidationException {
        HoaDon hoaDon = hoaDonRepository.findById(id).get();
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        Optional<TaiKhoan> findByNhanVien = taiKhoanRepository.findByUsername(username);

        TrangThaiHoaDon trangThaiHoaDon = new TrangThaiHoaDon();
        trangThaiHoaDon.setId(UUID.randomUUID());
        trangThaiHoaDon.setTrangThai(StatusOrderDetailEnums.DA_HUY.getValue());
        trangThaiHoaDon.setThoiGian(timestamp);
        trangThaiHoaDon.setUsername(hoaDon.getTaiKhoanNhanVien().getMaTaiKhoan());
        trangThaiHoaDon.setGhiChu("Nhân viên hủy đơn hàng");
        trangThaiHoaDon.setHoaDon(hoaDon);
        trangThaiHoaDonRepository.save(trangThaiHoaDon);

        auditLogService.writeAuditLogHoadon(findByNhanVien.get().getMaTaiKhoan(), hoaDon.getMa(), "Nhân viên hủy hóa đơn", hoaDon.getMa(), "", "", "", "");
        IdGioHangResponse idGioHangResponse = hoaDonRepository.showIdGioHangCt(id);
        if (idGioHangResponse == null) {
            return MessageResponse.builder().message("Null").build();
        } else {
            GioHang gioHang = gioHangRepository.findById(idGioHangResponse.getId()).get();
            gioHang.setTrangThai(2);
            gioHangRepository.save(gioHang);
            List<GioHangChiTiet> gioHangChiTiet = gioHangChiTietRepository.findByGioHang_Id(gioHang.getId());
            for (GioHangChiTiet ghct : gioHangChiTiet) {
                if (ghct != null) {
                    SanPhamChiTiet sanPhamChiTiet = chiTietSanPhamRepository.findById(ghct.getSanPhamChiTiet().getId()).get();
                    sanPhamChiTiet.setSoLuong(sanPhamChiTiet.getSoLuong() + ghct.getSoLuong());
                    chiTietSanPhamRepository.save(sanPhamChiTiet);
                }
            }
        }

        hoaDon.setTrangThai(6);
        hoaDonRepository.save(hoaDon);
        return MessageResponse.builder().message("Thành công").build();
    }
}
