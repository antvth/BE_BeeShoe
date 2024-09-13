package com.example.duantotnghiep.service.mua_hang_not_login_impl;

import com.example.duantotnghiep.entity.*;
import com.example.duantotnghiep.enums.StatusOrderEnums;
import com.example.duantotnghiep.enums.TypeOrderEnums;
import com.example.duantotnghiep.repository.*;
import com.example.duantotnghiep.repository.mua_hang_not_login_repo.*;
import com.example.duantotnghiep.request.not_login.CreateKhachRequest_not_login;
import com.example.duantotnghiep.response.MessageResponse;
import com.example.duantotnghiep.service.mua_hang_not_login_service.HoaDonService_not_login;
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
import java.security.Principal;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

@Service
public class HoaDonServiceImpl_not_login implements HoaDonService_not_login {

    @Autowired
    private HoaDonRepository_not_login hoaDonRepository;

    @Autowired
    private KhachHangRepository_not_login khachHangRepository_not_login;

    @Autowired
    private GioHangChiTietRepository_not_login gioHangChiTietRepository;

    @Autowired
    private HoaDonChiTietRepository_not_login hoaDonChiTietRepository;

    @Autowired
    private GioHangRepository_not_login gioHangRepository_not_login;

    @Autowired
    private LoaiDonRepository_not_login loaiDonRepository;

    @Autowired
    private DiaChiRepository_not_login diaChiRepository;

    @Autowired
    private TrangThaiHoaDonRepository trangThaiHoaDonRepository;

    @Autowired
    private SpGiamGiaRepository spGiamGiaRepository;

    @Autowired
    private VoucherRepository voucherRepository;

    @Autowired
    private LoaiHinhThucThanhToanRepository loaiHinhThucThanhToanRepository;

    @Autowired
    private HinhThucThanhToanRepository hinhThucThanhToanRepository;

    @Autowired
    private JavaMailSender javaMailSender;

    @Override
    public MessageResponse thanhToanKhongDangNhap(CreateKhachRequest_not_login createKhachRequest_not_login) {
        List<TaiKhoan> khachHangList = khachHangRepository_not_login.getKhachHangByEmailOrSdt(createKhachRequest_not_login.getEmail(), createKhachRequest_not_login.getSoDienThoai());
        TaiKhoan khachHang;
        Optional<Voucher> voucher = voucherRepository.findById(createKhachRequest_not_login.getIdGiamGia());
        //Step1 : Xử lí khách hàng và địa chỉ
        if (!khachHangList.isEmpty()) {
            // Nếu tài khoản khách hàng đã tồn tại, sử dụng tài khoản đó.
            khachHang = khachHangList.get(0);
        } else {
            // Nếu không tìm thấy tài khoản, tạo tài khoản mới cho khách hàng.
            khachHang = new TaiKhoan();

            khachHang.setId(UUID.randomUUID());

            khachHang.setEmail(createKhachRequest_not_login.getEmail());

            khachHang.setName(createKhachRequest_not_login.getHoTen());

            khachHang.setSoDienThoai(createKhachRequest_not_login.getSoDienThoai());

            khachHangRepository_not_login.save(khachHang);

            //Tạo địa chỉ cho khách luôn

            DiaChi diaChi = new DiaChi();

            diaChi.setId(UUID.randomUUID());

            diaChi.setDiaChi(createKhachRequest_not_login.getDiaChi());

            diaChi.setQuocGia("VietNam");

            diaChi.setTaiKhoan(khachHang);

            diaChiRepository.save(diaChi);

        }//End step 1

        //Step2 : Xử lí hóa đơn

        // Tạo hóa đơn mới và gán thông tin tài khoản khách hàng.
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        Random rand = new Random();
        int randomNumber = rand.nextInt(100000);
        String maHd = String.format("HD%03d", randomNumber);

        HoaDon hoaDon = new HoaDon();

        hoaDon.setId(UUID.randomUUID());

        hoaDon.setMa(maHd);

        hoaDon.setNgayTao(timestamp);

        hoaDon.setTrangThai(StatusOrderEnums.CHO_XAC_NHAN.getValue());
        hoaDon.setDiaChi(createKhachRequest_not_login.getDiaChi());
        hoaDon.setSdtNguoiNhan(createKhachRequest_not_login.getSoDienThoai());
        hoaDon.setTenNguoiNhan(createKhachRequest_not_login.getHoTen());
        hoaDon.setTaiKhoanKhachHang(khachHang);
        hoaDon.setEmail(createKhachRequest_not_login.getEmail());


        hoaDon.setLoaiDon(loaiDonRepository.findByTrangThai(TypeOrderEnums.ONLINE.getValue()).get());

        hoaDon.setThanhTien(createKhachRequest_not_login.getTongTien());

        hoaDon.setTienKhachTra(createKhachRequest_not_login.getTienKhachTra());

//        hoaDon.setTienGiamGia(BigDecimal.ZERO);

        hoaDon.setTienGiamGia(createKhachRequest_not_login.getTienGiamGia());

        //Nếu null sẽ không lưu
        if (voucher.isPresent()) {
            hoaDon.setVoucher(voucher.get());
            // Tăng giá trị soluongdung lên 1
            voucher.get().setSoLuongDung(voucher.get().getSoLuongDung() + 1);
            // Lưu lại vào cơ sở dữ liệu
            voucherRepository.save(voucher.get());
        }

        hoaDonRepository.save(hoaDon);
        //End step 2

        TrangThaiHoaDon tthd = new TrangThaiHoaDon();
        tthd.setId(UUID.randomUUID());
        tthd.setGhiChu("Người mua tạo đơn hàng");
        tthd.setTrangThai(1);
        tthd.setThoiGian(timestamp);
        tthd.setHoaDon(hoaDon);

        trangThaiHoaDonRepository.save(tthd);

        String productList = "";
        int stt = 1; // Biến để đánh số thứ tự
        //Step3 : Xử lí hóa đơn chi tiết
        for (UUID idGioHangChiTiet : createKhachRequest_not_login.getGioHangChiTietList()) {
            // Tạo chi tiết hóa đơn cho mỗi sản phẩm trong giỏ hàng.

            Optional<GioHangChiTiet> gioHangChiTiet = gioHangChiTietRepository.findById(idGioHangChiTiet);
            BigDecimal tienKhachMua = gioHangChiTiet.get().getSanPhamChiTiet().getSanPham().getGiaBan().subtract(new BigDecimal(getGiaGiamCuoiCung(gioHangChiTiet.get().getSanPhamChiTiet().getSanPham().getId())));
            productList += "<tr>\n" +
                    "    <td style=\"border: 1px solid #ddd; padding: 8px; text-align: left;\">" + stt + "</td>\n" +
                    "    <td style=\"border: 1px solid #ddd; padding: 8px; text-align: left;\">" + gioHangChiTiet.get().getSanPhamChiTiet().getSanPham().getTenSanPham() + "</td>\n" +
                    "    <td style=\"border: 1px solid #ddd; padding: 8px; text-align: left;\">" + gioHangChiTiet.get().getSoLuong() + "</td>\n" +
                    "    <td style=\"border: 1px solid #ddd; padding: 8px; text-align: left;\">" + tienKhachMua + "</td>\n" +
                    "    <td style=\"border: 1px solid #ddd; padding: 8px; text-align: left;\">" + tienKhachMua.multiply(new BigDecimal(gioHangChiTiet.get().getSoLuong())) + "</td>\n" +
                    "</tr>\n";
            stt++;
            if (gioHangChiTiet.isPresent()) {

                HoaDonChiTiet hoaDonChiTiet = new HoaDonChiTiet();

                hoaDonChiTiet.setId(UUID.randomUUID());

                hoaDonChiTiet.setHoaDon(hoaDon);

                hoaDonChiTiet.setSanPhamChiTiet(gioHangChiTiet.get().getSanPhamChiTiet());

                hoaDonChiTiet.setDonGia(gioHangChiTiet.get().getSanPhamChiTiet().getSanPham().getGiaBan());

                hoaDonChiTiet.setDonGiaSauGiam(gioHangChiTiet.get().getSanPhamChiTiet().getSanPham().getGiaBan().subtract(new BigDecimal(getGiaGiamCuoiCung(gioHangChiTiet.get().getSanPhamChiTiet().getSanPham().getId()))));

                hoaDonChiTiet.setSoLuong(gioHangChiTiet.get().getSoLuong());

                hoaDonChiTiet.setTrangThai(1);

                hoaDonChiTietRepository.save(hoaDonChiTiet);

            } else {
                System.out.println("giỏ hàng chi tiết không tồn tại !");
                return MessageResponse.builder().message("Thanh Toán thất bại").build();
            }
        }//End step 3

//        //Step 4 : Xử lí hóa đơn chi tiết cập nhật số lượng trong kho
//        for (UUID idGioHangChiTiet : createKhachRequest_not_login.getGioHangChiTietList()) {
//            Optional<GioHangChiTiet> gioHangChiTiet = gioHangChiTietRepository.findById(idGioHangChiTiet);
//
//            if (gioHangChiTiet.isPresent()) {
//                // Giảm số lượng sản phẩm trong kho đi số lượng đã bán trong chi tiết hóa đơn
//                gioHangChiTiet.get().getSanPhamChiTiet().setSoLuong(gioHangChiTiet.get().getSanPhamChiTiet().getSoLuong() - gioHangChiTiet.get().getSoLuong());
//                chiTietSanPhamRepository.save(gioHangChiTiet.get().getSanPhamChiTiet());
//            }
//        }//End step 4

        //Step 5 : Cập nhật trạng thái của giỏ hàng thành 2 sau khi thanh toán
        for (UUID idGioHangChiTiet : createKhachRequest_not_login.getGioHangChiTietList()) {

            Optional<GioHangChiTiet> gioHangChiTiet = gioHangChiTietRepository.findById(idGioHangChiTiet);

            if (gioHangChiTiet.isPresent()) {
                System.out.println(gioHangChiTiet.get().getGioHang().getTrangThai());
                gioHangChiTiet.get().getGioHang().setTrangThai(2);
                gioHangRepository_not_login.save(gioHangChiTiet.get().getGioHang());
            }

        }//End step 5

        //Step 6 : Đặt số lượng tất cả các sản phẩm trong giỏ hàng về 0
        for (UUID idGioHangChiTiet : createKhachRequest_not_login.getGioHangChiTietList()) {

            Optional<GioHangChiTiet> gioHangChiTiet = gioHangChiTietRepository.findById(idGioHangChiTiet);

            if (gioHangChiTiet.isPresent()) {
                gioHangChiTiet.get().setSoLuong(0);
                gioHangChiTietRepository.save(gioHangChiTiet.get());
            }

        }//End step 6
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
                    "                <h6 style=\"color: black; font-size: 13px;\">Tình trạng: " + "Chờ xác nhận" + "</h6>\n" +
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
                    "        <td><strong>" + "0" + "</strong></td>\n" +
                    "    </tr>\n" +
                    "    <tr>\n" +
                    "        <td colspan=\"4\" style=\"text-align: right;\"><strong>Tiền giảm giá:</strong></td>\n" +
                    "        <td><strong>" + hoaDon.getTienGiamGia() + "</strong></td>\n" +
                    "    </tr>\n" +
                    "    <tr>\n" +
                    "        <td colspan=\"4\" style=\"text-align: right;\"><strong>Tổng tiền hàng:</strong></td>\n" +
                    "        <td><strong>" + hoaDon.getThanhTien() + "</strong></td>\n" +
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
        return MessageResponse.builder().message("Thanh Toán Thành Công").build();

    }

    @Override
    public MessageResponse yeuCauTraHang(UUID idHoaDon, UUID idHoaDonChiTiet, String lyDo) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        HoaDon hoaDon = hoaDonRepository.findById(idHoaDon).get();
        HoaDonChiTiet hoaDonChiTiet = hoaDonChiTietRepository.findById(idHoaDonChiTiet).get();
        hoaDonChiTiet.setTrangThai(9);
        TrangThaiHoaDon trangThaiHoaDon = new TrangThaiHoaDon();
        trangThaiHoaDon.setId(UUID.randomUUID());
        trangThaiHoaDon.setTrangThai(9);
        trangThaiHoaDon.setThoiGian(timestamp);
        trangThaiHoaDon.setHoaDon(hoaDon);
        trangThaiHoaDon.setGhiChu(lyDo);
        trangThaiHoaDonRepository.save(trangThaiHoaDon);
        return MessageResponse.builder().message("Thành công").build();
    }

    public UUID thanhToanLogin(CreateKhachRequest_not_login createKhachRequest_not_login, Principal principal) {

        Optional<Voucher> voucher = voucherRepository.findById(createKhachRequest_not_login.getIdGiamGia());

        //Step1 : Xử lí khách hàng và địa chỉ
        TaiKhoan khachHang = khachHangRepository_not_login.findByUsername(principal.getName());
        //End step 1

        //Step2 : Xử lí hóa đơn

        // Tạo hóa đơn mới và gán thông tin tài khoản khách hàng.
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        Random rand = new Random();
        int randomNumber = rand.nextInt(100000);
        String maHd = String.format("HD%03d", randomNumber);

        HoaDon hoaDon = new HoaDon();

        hoaDon.setId(UUID.randomUUID());

        hoaDon.setMa(maHd);

        hoaDon.setNgayTao(timestamp);

        hoaDon.setTrangThai(StatusOrderEnums.CHO_XAC_NHAN.getValue());
        hoaDon.setDiaChi(createKhachRequest_not_login.getDiaChi());
        hoaDon.setSdtNguoiNhan(createKhachRequest_not_login.getSoDienThoai());
        hoaDon.setTenNguoiNhan(createKhachRequest_not_login.getHoTen());
        hoaDon.setTaiKhoanKhachHang(khachHang);
        hoaDon.setEmail(createKhachRequest_not_login.getEmail());

        hoaDon.setLoaiDon(loaiDonRepository.findByTrangThai(TypeOrderEnums.ONLINE.getValue()).get());

        hoaDon.setThanhTien(createKhachRequest_not_login.getTongTien());

        hoaDon.setTienKhachTra(createKhachRequest_not_login.getTienKhachTra());

        hoaDon.setTienGiamGia(createKhachRequest_not_login.getTienGiamGia());

        //Nếu null sẽ không lưu
        if (voucher.isPresent()) {
            hoaDon.setVoucher(voucher.get());
            // Tăng giá trị soluongdung lên 1
            voucher.get().setSoLuongDung(voucher.get().getSoLuongDung() + 1);
            // Lưu lại vào cơ sở dữ liệu
            voucherRepository.save(voucher.get());
        }

        hoaDonRepository.save(hoaDon);
        //End step 2

        TrangThaiHoaDon tthd = new TrangThaiHoaDon();
        tthd.setId(UUID.randomUUID());
        tthd.setGhiChu("Người mua tạo đơn hàng");
        tthd.setTrangThai(1);
        tthd.setThoiGian(timestamp);
        tthd.setHoaDon(hoaDon);

        trangThaiHoaDonRepository.save(tthd);

        String productList = "";
        int stt = 1; // Biến để đánh số thứ tự
        //Step3 : Xử lí hóa đơn chi tiết
        for (UUID idGioHangChiTiet : createKhachRequest_not_login.getGioHangChiTietList()) {
            // Tạo chi tiết hóa đơn cho mỗi sản phẩm trong giỏ hàng.

            Optional<GioHangChiTiet> gioHangChiTiet = gioHangChiTietRepository.findById(idGioHangChiTiet);
            BigDecimal tienKhachMua = gioHangChiTiet.get().getSanPhamChiTiet().getSanPham().getGiaBan().subtract(new BigDecimal(getGiaGiamCuoiCung(gioHangChiTiet.get().getSanPhamChiTiet().getSanPham().getId())));
            productList += "<tr>\n" +
                    "    <td style=\"border: 1px solid #ddd; padding: 8px; text-align: left;\">" + stt + "</td>\n" +
                    "    <td style=\"border: 1px solid #ddd; padding: 8px; text-align: left;\">" + gioHangChiTiet.get().getSanPhamChiTiet().getSanPham().getTenSanPham() + "</td>\n" +
                    "    <td style=\"border: 1px solid #ddd; padding: 8px; text-align: left;\">" + gioHangChiTiet.get().getSoLuong() + "</td>\n" +
                    "    <td style=\"border: 1px solid #ddd; padding: 8px; text-align: left;\">" + tienKhachMua + "</td>\n" +
                    "    <td style=\"border: 1px solid #ddd; padding: 8px; text-align: left;\">" + tienKhachMua.multiply(new BigDecimal(gioHangChiTiet.get().getSoLuong())) + "</td>\n" +
                    "</tr>\n";
            stt++;
            if (gioHangChiTiet.isPresent()) {

                HoaDonChiTiet hoaDonChiTiet = new HoaDonChiTiet();

                hoaDonChiTiet.setId(UUID.randomUUID());

                hoaDonChiTiet.setHoaDon(hoaDon);

                hoaDonChiTiet.setSanPhamChiTiet(gioHangChiTiet.get().getSanPhamChiTiet());

                hoaDonChiTiet.setDonGia(gioHangChiTiet.get().getSanPhamChiTiet().getSanPham().getGiaBan());

                hoaDonChiTiet.setDonGiaSauGiam(gioHangChiTiet.get().getSanPhamChiTiet().getSanPham().getGiaBan().subtract(new BigDecimal(getGiaGiamCuoiCung(gioHangChiTiet.get().getSanPhamChiTiet().getSanPham().getId()))));

                hoaDonChiTiet.setSoLuong(gioHangChiTiet.get().getSoLuong());

                hoaDonChiTiet.setTrangThai(1);

                hoaDonChiTietRepository.save(hoaDonChiTiet);

            } else {
                System.out.println("giỏ hàng chi tiết không tồn tại !");
                return null;
            }
        }//End step 3

//        //Step 4 : Xử lí hóa đơn chi tiết cập nhật số lượng trong kho
//        for (UUID idGioHangChiTiet : createKhachRequest_not_login.getGioHangChiTietList()) {
//            Optional<GioHangChiTiet> gioHangChiTiet = gioHangChiTietRepository.findById(idGioHangChiTiet);
//
//            if (gioHangChiTiet.isPresent()) {
//                // Giảm số lượng sản phẩm trong kho đi số lượng đã bán trong chi tiết hóa đơn
//                gioHangChiTiet.get().getSanPhamChiTiet().setSoLuong(gioHangChiTiet.get().getSanPhamChiTiet().getSoLuong() - gioHangChiTiet.get().getSoLuong());
//                chiTietSanPhamRepository.save(gioHangChiTiet.get().getSanPhamChiTiet());
//            }
//        }//End step 4

        //Step 5 : Cập nhật trạng thái của giỏ hàng thành 2 sau khi thanh toán
        for (UUID idGioHangChiTiet : createKhachRequest_not_login.getGioHangChiTietList()) {

            Optional<GioHangChiTiet> gioHangChiTiet = gioHangChiTietRepository.findById(idGioHangChiTiet);

            if (gioHangChiTiet.isPresent()) {
                System.out.println(gioHangChiTiet.get().getGioHang().getTrangThai());
                gioHangChiTiet.get().getGioHang().setTrangThai(2);
                gioHangRepository_not_login.save(gioHangChiTiet.get().getGioHang());
            }

        }//End step 5

        //Step 6 : Đặt số lượng tất cả các sản phẩm trong giỏ hàng về 0
        for (UUID idGioHangChiTiet : createKhachRequest_not_login.getGioHangChiTietList()) {

            Optional<GioHangChiTiet> gioHangChiTiet = gioHangChiTietRepository.findById(idGioHangChiTiet);

            if (gioHangChiTiet.isPresent()) {
                gioHangChiTiet.get().setSoLuong(0);
                gioHangChiTietRepository.save(gioHangChiTiet.get());
            }

        }//End step 6

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
                    "                <h6 style=\"color: black; font-size: 13px;\">Tình trạng: " + "Chờ xác nhận" + "</h6>\n" +
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
                    "        <td><strong>" + "0" + "</strong></td>\n" +
                    "    </tr>\n" +
                    "    <tr>\n" +
                    "        <td colspan=\"4\" style=\"text-align: right;\"><strong>Tiền giảm giá:</strong></td>\n" +
                    "        <td><strong>" + hoaDon.getTienGiamGia() + "</strong></td>\n" +
                    "    </tr>\n" +
                    "    <tr>\n" +
                    "        <td colspan=\"4\" style=\"text-align: right;\"><strong>Tổng tiền hàng:</strong></td>\n" +
                    "        <td><strong>" + hoaDon.getThanhTien() + "</strong></td>\n" +
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
        return hoaDon.getId();
    }

    public Long getGiaGiamCuoiCung(UUID id) {
        long sumPriceTien = 0L;
        long sumPricePhanTram = 0L;
        List<SpGiamGia> spGiamGiaList = spGiamGiaRepository.findBySanPham_Id(id);

        for (SpGiamGia spGiamGia : spGiamGiaList) {
            long mucGiam = spGiamGia.getMucGiam();
            if (spGiamGia.getGiamGia().getHinhThucGiam() == 1) {
                sumPriceTien += mucGiam;
            }
            if (spGiamGia.getGiamGia().getHinhThucGiam() == 2) {
                long donGiaAsLong = spGiamGia.getDonGia().longValue();
                double giamGia = (double) mucGiam / 100;
                long giaTienSauGiamGia = (long) (donGiaAsLong * giamGia);
                sumPricePhanTram += giaTienSauGiamGia;
            }
        }
        return sumPriceTien + sumPricePhanTram;
    }

    public MessageResponse cashVnPay(UUID id, BigDecimal vnpAmount, String maGiaoDinh) {
        HoaDon hoaDon = hoaDonRepository.findById(id).get();
        LoaiHinhThucThanhToan loaiHinhThucThanhToan = new LoaiHinhThucThanhToan();
        loaiHinhThucThanhToan.setId(UUID.randomUUID());
        loaiHinhThucThanhToan.setNgayTao(new Date(System.currentTimeMillis()));
        loaiHinhThucThanhToan.setTenLoai("Khách thanh toán");
        loaiHinhThucThanhToanRepository.save(loaiHinhThucThanhToan);

        HinhThucThanhToan hinhThucThanhToan = new HinhThucThanhToan();
        hinhThucThanhToan.setId(UUID.randomUUID());
        hinhThucThanhToan.setNgayThanhToan(new Date(System.currentTimeMillis()));
        hinhThucThanhToan.setTongSoTien(vnpAmount);
        hinhThucThanhToan.setPhuongThucThanhToan(2);
        hinhThucThanhToan.setCodeTransaction(maGiaoDinh);
        hinhThucThanhToan.setHoaDon(hoaDon);
        hinhThucThanhToan.setTrangThai(2);
        hinhThucThanhToan.setLoaiHinhThucThanhToan(loaiHinhThucThanhToan);
        hinhThucThanhToanRepository.save(hinhThucThanhToan);
        return MessageResponse.builder().message("Thanh toán thành công").build();
    }
}