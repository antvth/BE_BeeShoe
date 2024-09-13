package com.example.duantotnghiep.util;

import com.example.duantotnghiep.entity.HoaDon;
import com.example.duantotnghiep.entity.HoaDonChiTiet;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import java.math.BigDecimal;
import java.util.List;

public class SendEmailOrder {

    public static void sendEmailOrder(HoaDon hoaDon, JavaMailSender javaMailSender) {
        String productList = ""; // Chuỗi để lưu thông tin về sản phẩm

        String trangThai = hoaDon.getTrangThai() == 1 ? "Chờ Xác Nhận" :
                           hoaDon.getTrangThai() == 2 ? "Xác nhận" :
                           hoaDon.getTrangThai() == 3 ? "Chờ giao hàng" :
                           hoaDon.getTrangThai() == 4 ? "Đang giao hàng" : "Thành công";

        List<HoaDonChiTiet> danhSachSanPham = hoaDon.getHoaDonChiTietList();// Giả sử danhSachSanPham lưu danh sách sản phẩm
        int stt = 1; // Biến để đánh số thứ tự

        for (HoaDonChiTiet sanPham : danhSachSanPham) {
            productList += "<tr>\n" +
                    "    <td style=\"border: 1px solid #ddd; padding: 8px; text-align: left;\">" + stt + "</td>\n" +
                    "    <td style=\"border: 1px solid #ddd; padding: 8px; text-align: left;\">" + sanPham.getSanPhamChiTiet().getSanPham().getTenSanPham() + "</td>\n" +
                    "    <td style=\"border: 1px solid #ddd; padding: 8px; text-align: left;\">" + sanPham.getSoLuong() + "</td>\n" +
                    "    <td style=\"border: 1px solid #ddd; padding: 8px; text-align: left;\">" + FormatNumber.formatBigDecimal(sanPham.getDonGiaSauGiam()) + "</td>\n" +
                    "    <td style=\"border: 1px solid #ddd; padding: 8px; text-align: left;\">" + FormatNumber.formatBigDecimal(sanPham.getDonGiaSauGiam().multiply(new BigDecimal(sanPham.getSoLuong()))) + "</td>\n" +
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
                    "                <h6 style=\"color: black; font-size: 13px;\">Tình trạng: " + trangThai + "</h6>\n" +
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
                    "        <td><strong>" + FormatNumber.formatBigDecimal(hoaDon.getTienShip()) + "</strong></td>\n" +
                    "    </tr>\n" +
                    "    <tr>\n" +
                    "        <td colspan=\"4\" style=\"text-align: right;\"><strong>Tiền giảm giá:</strong></td>\n" +
                    "        <td><strong>" + FormatNumber.formatBigDecimal(hoaDon.getTienGiamGia()) + "</strong></td>\n" +
                    "    </tr>\n" +
                    "    <tr>\n" +
                    "        <td colspan=\"4\" style=\"text-align: right;\"><strong>Tổng tiền hàng:</strong></td>\n" +
                    "        <td><strong>" + FormatNumber.formatBigDecimal(hoaDon.getThanhTien()) + "</strong></td>\n" +
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
