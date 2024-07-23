package com.example.duantotnghiep.util;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class SendConfirmationEmail {

    private final JavaMailSender javaMailSender;

    public SendConfirmationEmail(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Async("taskExecutor")
    public void sendConfirmationEmailStatic(String email, String username, String password) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");

        try {
            helper.setTo(email);
            helper.setSubject("Chào mừng bạn đến với BEE Shoe");

            String htmlMsg = "<h1>Chào mừng bạn đến với <span style='color: #ff9900;'>BEE SHOE</span> của chúng tôi!</h1>\n" +
                    "<p>Xin chân thành cảm ơn bạn đã đăng ký nhận <span style='color: #ff9900;'>BEE SHOE</span> của chúng tôi. Chúng tôi sẽ cung cấp cho bạn thông tin cập\n" +
                    "    nhật về tin tức và ưu đãi mới nhất.</p>\n" +
                    "<h3>Ưu điểm của <span style='color: #ff9900;'>BEE SHOE</span>:</h3>\n" +
                    "<ul>\n" +
                    "    <li>Thông tin mới nhất về sản phẩm và dịch vụ của chúng tôi</li>\n" +
                    "    <li>Ưu đãi đặc biệt và khuyến mãi hấp dẫn</li>\n" +
                    "</ul>\n" +
                    "<p><strong>Đừng bỏ lỡ!</strong> Để nhận các thông tin và ưu đãi đặc biệt từ chúng tôi, hãy nhấp vào nút bên dưới để\n" +
                    "    mua ngay sản phẩm:</p>\n" +
                    "<a href='LINK_DEN_TRANG_DANG_KY'\n" +
                    "    style='padding: 10px 20px; background-color: #ff9900; color: #ffffff; text-decoration: none; border-radius: 5px;'>Trang web</a>" +
                    "<p><strong>Thông tin đăng nhập:</strong></p>" +
                    "<p>Username: <strong>" + username + "</strong></p>" +
                    "<p>Password: <strong>" + password + "</strong></p>";

            helper.setText(htmlMsg, true);
            javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {
            // Xử lý ngoại lệ nếu gửi email thất bại
            e.printStackTrace();
        }
    }
}
