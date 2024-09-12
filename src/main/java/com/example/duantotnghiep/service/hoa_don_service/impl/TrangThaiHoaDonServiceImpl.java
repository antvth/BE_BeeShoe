package com.example.duantotnghiep.service.hoa_don_service.impl;

import com.example.duantotnghiep.config.VnPayConfigTaiQuay;
import com.example.duantotnghiep.entity.*;
import com.example.duantotnghiep.enums.StatusOrderEnums;
import com.example.duantotnghiep.repository.*;
import com.example.duantotnghiep.request.ConfirmOrderClientRequest;
import com.example.duantotnghiep.request.ConfirmOrderDeliver;
import com.example.duantotnghiep.request.TrangThaiHoaDonRequest;
import com.example.duantotnghiep.response.MessageResponse;
import com.example.duantotnghiep.service.audi_log_service.AuditLogService;
import com.example.duantotnghiep.service.hoa_don_service.TrangThaiHoaDonService;
import com.example.duantotnghiep.util.FormatNumber;
import com.example.duantotnghiep.util.SendEmailOrder;
import com.opencsv.exceptions.CsvValidationException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TrangThaiHoaDonServiceImpl implements TrangThaiHoaDonService {

    @Autowired
    private HoaDonRepository hoaDonRepository;

    @Autowired
    private AuditLogService auditLogService;

    @Autowired
    private TrangThaiHoaDonRepository trangThaiHoaDonRepository;

    @Autowired
    private TaiKhoanRepository taiKhoanRepository;

    @Autowired
    private HoaDonChiTietRepository hoaDonChiTietRepository;

    @Autowired
    private HinhThucThanhToanRepository hinhThucThanhToanRepository;

    @Autowired
    private LoaiHinhThucThanhToanRepository loaiHinhThucThanhToanRepository;

    @Autowired
    private JavaMailSender javaMailSender;

    @Override
    public MessageResponse confirmOrder(UUID hoadonId, TrangThaiHoaDonRequest request, String name, boolean sendEmail) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        Optional<HoaDon> hoaDonOptional = hoaDonRepository.findById(hoadonId);
        TaiKhoan taiKhoan = taiKhoanRepository.findByUsername(name).get();
        List<HoaDonChiTiet> hoaDonChiTietList = hoaDonChiTietRepository.findAllByHoaDon(hoaDonOptional.get());

        if (hoaDonOptional.isPresent()) {
            HoaDon hoaDon = hoaDonOptional.get();

            // Tạo một bản ghi TrangThaiHoaDon mới
            TrangThaiHoaDon trangThaiHoaDon = new TrangThaiHoaDon();
            trangThaiHoaDon.setId(UUID.randomUUID());
            trangThaiHoaDon.setHoaDon(hoaDon);
            trangThaiHoaDon.setThoiGian(timestamp);
            trangThaiHoaDon.setTrangThai(request.getNewTrangThai());
            trangThaiHoaDon.setUsername(taiKhoan.getMaTaiKhoan());
            trangThaiHoaDon.setGhiChu(request.getGhiChu());

            // Cập nhật trạng thái của Hóa Đơn
            hoaDon.setTrangThai(request.getNewTrangThai());
            hoaDon.setTaiKhoanNhanVien(taiKhoan);
            hoaDon.setNgayCapNhap(timestamp);

            hoaDonChiTietList.stream()
                    .filter(hoaDonChiTiet -> hoaDonChiTiet.getTrangThai() != 7)
                    .forEach(hoaDonChiTiet -> {
                        hoaDonChiTiet.setTrangThai(request.getNewTrangThai());
                        hoaDonChiTietRepository.save(hoaDonChiTiet);
                    });

            // Lưu cả hai bảng
            hoaDonRepository.save(hoaDon);
            trangThaiHoaDonRepository.save(trangThaiHoaDon);
            if (sendEmail) {
                SendEmailOrder.sendEmailOrder(hoaDon, javaMailSender);
            }
            if (hoaDonOptional.get().getTrangThai() == 5) {
                System.out.println("Có chạy vào đây khoong");
                if (hoaDonOptional.get().getHinhThucThanhToanList().isEmpty()) {
                    System.out.println("Có chạy vào đây khoong 1");
                    LoaiHinhThucThanhToan loaiHinhThucThanhToan = new LoaiHinhThucThanhToan();
                    loaiHinhThucThanhToan.setId(UUID.randomUUID());
                    loaiHinhThucThanhToan.setNgayTao(new java.sql.Date(System.currentTimeMillis()));
                    loaiHinhThucThanhToan.setTenLoai("Khách thanh toán");
                    loaiHinhThucThanhToanRepository.save(loaiHinhThucThanhToan);

                    HinhThucThanhToan hinhThucThanhToan = new HinhThucThanhToan();
                    hinhThucThanhToan.setId(UUID.randomUUID());
                    hinhThucThanhToan.setNgayThanhToan(new Date(System.currentTimeMillis()));
                    hinhThucThanhToan.setTaiKhoan(hoaDon.getTaiKhoanKhachHang());
                    hinhThucThanhToan.setTongSoTien(hoaDon.getThanhTien());
                    hinhThucThanhToan.setGhiChu("");
                    hinhThucThanhToan.setPhuongThucThanhToan(1);
                    hinhThucThanhToan.setCodeTransaction(VnPayConfigTaiQuay.getRandomNumber(8));
                    hinhThucThanhToan.setHoaDon(hoaDon);
                    hinhThucThanhToan.setTrangThai(1);
                    hinhThucThanhToan.setLoaiHinhThucThanhToan(loaiHinhThucThanhToan);
                    hinhThucThanhToanRepository.save(hinhThucThanhToan);
                } else {
                    for (HinhThucThanhToan x : hoaDon.getHinhThucThanhToanList()) {
                        LoaiHinhThucThanhToan loaiHinhThucThanhToan = new LoaiHinhThucThanhToan();
                        loaiHinhThucThanhToan.setId(UUID.randomUUID());
                        loaiHinhThucThanhToan.setNgayTao(new java.sql.Date(System.currentTimeMillis()));
                        loaiHinhThucThanhToan.setTenLoai("Khách thanh toán");
                        loaiHinhThucThanhToanRepository.save(loaiHinhThucThanhToan);

                        HinhThucThanhToan hinhThucThanhToan = new HinhThucThanhToan();
                        hinhThucThanhToan.setId(UUID.randomUUID());
                        hinhThucThanhToan.setNgayThanhToan(new Date(System.currentTimeMillis()));
                        hinhThucThanhToan.setTaiKhoan(hoaDon.getTaiKhoanKhachHang());
                        hinhThucThanhToan.setTongSoTien(hoaDon.getThanhTien().subtract(x.getTongSoTien()));
                        hinhThucThanhToan.setGhiChu("");
                        hinhThucThanhToan.setPhuongThucThanhToan(1);
                        hinhThucThanhToan.setCodeTransaction(VnPayConfigTaiQuay.getRandomNumber(8));
                        hinhThucThanhToan.setHoaDon(hoaDon);
                        hinhThucThanhToan.setTrangThai(1);
                        hinhThucThanhToan.setLoaiHinhThucThanhToan(loaiHinhThucThanhToan);
                        hinhThucThanhToanRepository.save(hinhThucThanhToan);
                    }
                }
            }
        }
        return MessageResponse.builder().message("Thêm thành công").build();
    }

    @Override
    public MessageResponse confirmOrderClient(UUID hoadonId, ConfirmOrderClientRequest request, String username) throws IOException, CsvValidationException {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        TaiKhoan taiKhoan = taiKhoanRepository.findByUsername(username).orElse(null);
        Optional<HoaDon> hoaDon = hoaDonRepository.findById(hoadonId);

        if (hoaDon.isPresent()) {
            hoaDon.get().setDiaChi(request.getDiaChi());
            hoaDon.get().setSdtNguoiNhan(request.getSdtClient());
            hoaDon.get().setTenNguoiNhan(request.getHoVatenClient());
            hoaDon.get().setEmail(request.getEmail());
            hoaDon.get().setNgayCapNhap(timestamp);

//            BigDecimal tongTien = BigDecimal.ZERO;
//            for (HoaDonChiTiet hdct : hoaDon.get().getHoaDonChiTietList()) {
//                BigDecimal donGiaSauGiam = hdct.getDonGiaSauGiam() != null ? hdct.getDonGiaSauGiam() : BigDecimal.ZERO;
//                Integer soLuong = hdct.getSoLuong() != null ? hdct.getSoLuong() : 0;
//                tongTien = tongTien.add(donGiaSauGiam.multiply(new BigDecimal(soLuong))).subtract(hoaDon.get().getTienGiamGia());
//            }

            if (hoaDon.get().getTienShip() == null) {
                hoaDon.get().setThanhTien(hoaDon.get().getThanhTien().add(request.getTienShip()));
            }
            if (hoaDon.get().getTienShip() != null) {
                BigDecimal tongTien = hoaDon.get().getThanhTien().subtract(hoaDon.get().getTienShip());
                hoaDon.get().setThanhTien(tongTien.add(request.getTienShip()));
            }
            hoaDon.get().setTienShip(request.getTienShip());
            hoaDonRepository.save(hoaDon.get());

            TrangThaiHoaDon trangThaiHoaDon = new TrangThaiHoaDon();
            trangThaiHoaDon.setId(UUID.randomUUID());
            trangThaiHoaDon.setHoaDon(hoaDon.get());
            trangThaiHoaDon.setTrangThai(StatusOrderEnums.CHINH_SUA_DON_HANG.getValue());
            trangThaiHoaDon.setThoiGian(timestamp);
            trangThaiHoaDon.setUsername(taiKhoan.getUsername());
            trangThaiHoaDon.setGhiChu("Nhân viên sửa đơn cho khách");
            trangThaiHoaDonRepository.save(trangThaiHoaDon);

            auditLogService.writeAuditLogHoadon(username, taiKhoan.getEmail(), "Cập nhật địa chỉ",
                    hoaDon.get().getMa(), "Tên khách: " + request.getHoVatenClient(), "SĐT: " + request.getSdtClient(),
                    "Tiền ship: " + request.getTienShip(), "Địa chỉ: " + request.getDiaChi());
            return MessageResponse.builder().message("Cập nhập thành công").build();
        } else {
            return MessageResponse.builder().message("Không tìm thấy hóa đơn").build();
        }
    }

    @Override
    public MessageResponse confirmOrderDeliver(UUID hoadonId, ConfirmOrderDeliver request) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        Optional<HoaDon> hoaDon = hoaDonRepository.findById(hoadonId);

        if (hoaDon.isPresent()) {
            hoaDon.get().setSdtNguoiShip(request.getSoDienThoaiGiao());
            hoaDon.get().setTenNguoiShip(request.getTenNguoiGiao());
            hoaDon.get().setNgayCapNhap(timestamp);
            hoaDonRepository.save(hoaDon.get());

            TrangThaiHoaDon trangThaiHoaDon = new TrangThaiHoaDon();
            trangThaiHoaDon.setId(UUID.randomUUID());
            trangThaiHoaDon.setHoaDon(hoaDon.get());
            trangThaiHoaDon.setTrangThai(StatusOrderEnums.CHINH_SUA_DON_HANG.getValue());
            trangThaiHoaDon.setThoiGian(timestamp);
            trangThaiHoaDon.setUsername(hoaDon.get().getTaiKhoanNhanVien().getMaTaiKhoan());
            trangThaiHoaDon.setGhiChu("Nhân viên sửa đơn cho khách");
            trangThaiHoaDonRepository.save(trangThaiHoaDon);
            return MessageResponse.builder().message("Cập nhập thành công").build();
        } else {
            return MessageResponse.builder().message("Không tìm thấy hóa đơn").build();
        }
    }

    private void sendEmailOrder(HoaDon hoaDon) {
        String productList = ""; // Chuỗi để lưu thông tin về sản phẩm

        List<HoaDonChiTiet> danhSachSanPham = hoaDon.getHoaDonChiTietList();// Giả sử danhSachSanPham lưu danh sách sản phẩm
        int stt = 1; // Biến để đánh số thứ tự

        for (HoaDonChiTiet sanPham : danhSachSanPham) {
            productList += "<tr>\n" +
                    "    <td style=\"border: 1px solid #ddd; padding: 8px; text-align: left;\">" + stt + "</td>\n" +
                    "    <td style=\"border: 1px solid #ddd; padding: 8px; text-align: left;\">" + sanPham.getSanPhamChiTiet().getSanPham().getTenSanPham() + "</td>\n" +
                    "    <td style=\"border: 1px solid #ddd; padding: 8px; text-align: left;\">" + sanPham.getSoLuong() + "</td>\n" +
                    "    <td style=\"border: 1px solid #ddd; padding: 8px; text-align: left;\">" + FormatNumber.formatBigDecimal(sanPham.getDonGiaSauGiam()) + "đ</td>\n" +
                    "    <td style=\"border: 1px solid #ddd; padding: 8px; text-align: left;\">" + FormatNumber.formatBigDecimal(sanPham.getDonGiaSauGiam().multiply(new BigDecimal(sanPham.getSoLuong()))) + "đ</td>\n" +
                    "</tr>\n";
            stt++;
        }
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");

        try {
            helper.setTo(hoaDon.getEmail());
            helper.setSubject("");

            String htmlMsg = "<body style=\"font-family: Arial, sans-serif;\">\n" +
                    "    <h1 style=\"color: #ff9900;\">Kính gửi Quý khách hàng: " + hoaDon.getTenNguoiNhan() + "</h1>\n" +
                    "    <h6 style=\"color: black; font-size: 15px;\">Chúng tôi xin chân thành cảm ơn Quý khách đã đặt hàng tại <strong  style=\"color: #ff9900;\">BEE SHOE</strong>.</h6>\n" +
                    "    <h6 style=\"color: black; font-size: 15px;\">Thông tin chi tiết về đơn hàng của Quý khách như sau:</h6>\n" +
                    "    <table style=\"width: 100%; border-collapse: collapse; margin-bottom: 20px;\">\n" +
                    "        <tr>\n" +
                    "            <td style=\"border: 1px solid #ddd; padding: 8px; text-align: left;\">\n" +
                    "                <h6 style=\"color: black; font-size: 13px;\">Mã đơn hàng: " + hoaDon.getMa() + "</h6>\n" +
                    "            </td>\n" +
                    "            <td style=\"border: 1px solid #ddd; padding: 8px; text-align: left;\">\n" +
                    "                <h6 style=\"color: black; font-size: 13px;\">Ngày đặt hàng: " + hoaDon.getNgayTao() + "</h6>\n" +
                    "            </td>\n" +
                    "            <td style=\"border: 1px solid #ddd; padding: 8px; text-align: left;\">\n" +
                    "                <h6 style=\"color: black; font-size: 13px;\">Khách hàng: " + hoaDon.getTenNguoiNhan() + "</h6>\n" +
                    "            </td>\n" +
                    "        </tr>\n" +
                    "        <tr>\n" +
                    "            <td style=\"border: 1px solid #ddd; padding: 8px; text-align: left;\">\n" +
                    "                <h6 style=\"color: black; font-size: 13px;\">Email: " + hoaDon.getEmail() + "</h6>\n" +
                    "            </td>\n" +
                    "            <td style=\"border: 1px solid #ddd; padding: 8px; text-align: left;\">\n" +
                    "                <h6 style=\"color: black; font-size: 13px;\">Số điện thoại: " + hoaDon.getSdtNguoiNhan() + "</h6>\n" +
                    "            </td>\n" +
                    "            <td style=\"border: 1px solid #ddd; padding: 8px; text-align: left;\">\n" +
                    "                <h6 style=\"color: black; font-size: 13px;\">Tình trạng: " + hoaDon.getTrangThai() + "</h6>\n" +
                    "            </td>\n" +
                    "        </tr>\n" +
                    "    </table>\n" +
                    "    <h6 style=\"color: black; font-size: 15px;\">Thông tin chi tiết về sản phẩm của Quý khách như sau:</h6>\n" +
                    "    <table style=\"width: 100%; border-collapse: collapse; margin-bottom: 20px;\">\n" +
                    "        <thead>\n" +
                    "            <tr>\n" +
                    "                <th style=\"border: 1px solid #ddd; padding: 8px; text-align: left;\">STT</th>\n" +
                    "                <th style=\"border: 1px solid #ddd; padding: 8px; text-align: left;\">Tên sản phẩm</th>\n" +
                    "                <th style=\"border: 1px solid #ddd; padding: 8px; text-align: left;\">Số lượng</th>\n" +
                    "                <th style=\"border: 1px solid #ddd; padding: 8px; text-align: left;\">Giá bán</th>\n" +
                    "                <th style=\"border: 1px solid #ddd; padding: 8px; text-align: left;\">Tổng cộng</th>\n" +
                    "            </tr>\n" +
                    "        </thead>\n" +
                    "    <tbody>\n" +
                    productList + // Thêm danh sách sản phẩm vào đây
                    "    </tbody>\n" +
                    "<tfoot>\n" +
                    "    <tr>\n" +
                    "        <td colspan=\"4\" style=\"text-align: right;\"><strong>Tiền ship:</strong></td>\n" +
                    "        <td><strong>" + FormatNumber.formatBigDecimal(hoaDon.getTienShip()) + "đ</strong></td>\n" +
                    "    </tr>\n" +
                    "    <tr>\n" +
                    "        <td colspan=\"4\" style=\"text-align: right;\"><strong>Tiền giảm giá:</strong></td>\n" +
                    "        <td><strong>" + FormatNumber.formatBigDecimal(hoaDon.getTienGiamGia()) + "đ</strong></td>\n" +
                    "    </tr>\n" +
                    "    <tr>\n" +
                    "        <td colspan=\"4\" style=\"text-align: right;\"><strong>Tổng tiền hàng:</strong></td>\n" +
                    "        <td><strong>" + FormatNumber.formatBigDecimal(hoaDon.getThanhTien()) + "đ</strong></td>\n" +
                    "    </tr>\n" +
                    "</tfoot>\n" +
                    "    </table>\n" +
                    "\n" +
                    "    <p>Chúng tôi sẽ xử lý đơn hàng của Quý khách sớm nhất có thể và thông báo cho Quý khách khi đơn hàng đã được giao\n" +
                    "        cho dịch vụ vận chuyển.</p>\n" +
                    "    <p>Nếu có bất kỳ thắc mắc hoặc yêu cầu nào, vui lòng liên hệ với chúng tôi qua thông tin sau:</p>\n" +
                    "    <p>- Điện thoại: 0983280968\n" +
                    "        - Email: beeshoepoly@gmail.com</p>\n" +
                    "    <p>Xin chân thành cảm ơn Quý khách hàng đã tin tưởng và lựa chọn sản phẩm của chúng tôi.</p>\n" +
                    "    <footer style=\"margin-top: 20px;\">Trân trọng, BEESHOE</footer>\n" +
                    "</body>";

            helper.setText(htmlMsg, true);
            javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {
            // Xử lý ngoại lệ nếu gửi email thất bại
            e.printStackTrace();
        }
    }

}
