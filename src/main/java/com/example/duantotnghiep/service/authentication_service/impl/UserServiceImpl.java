package com.example.duantotnghiep.service.authentication_service.impl;

import com.example.duantotnghiep.entity.LoaiTaiKhoan;
import com.example.duantotnghiep.entity.RefreshToken;
import com.example.duantotnghiep.entity.TaiKhoan;
import com.example.duantotnghiep.enums.TypeAccountEnum;
import com.example.duantotnghiep.jwt.JwtService;
import com.example.duantotnghiep.model.UserCustomDetails;
import com.example.duantotnghiep.repository.LoaiTaiKhoanRepository;
import com.example.duantotnghiep.repository.RefreshTokenRepository;
import com.example.duantotnghiep.repository.TaiKhoanRepository;
import com.example.duantotnghiep.request.LoginRequest;
import com.example.duantotnghiep.request.RegisterRequest;
import com.example.duantotnghiep.response.MessageResponse;
import com.example.duantotnghiep.response.TokenResponse;
import com.example.duantotnghiep.service.authentication_service.UserService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final LoaiTaiKhoanRepository loaiTaiKhoanRepository;

    private final TaiKhoanRepository taiKhoanRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    private final RefreshTokenRepository refreshTokenRepository;

    private final JavaMailSender javaMailSender;

    private Map<String, String> codeMap = new HashMap<>();

    /**
     * Đăng nhập
     * @param loginRequest
     * @return
     */
    @Override
    public TokenResponse login(LoginRequest loginRequest) {
        // Xác thực người dùng
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );
        // Kiểm tra xem tài khoản đã tồn tại hay không
        Optional<TaiKhoan> optionalPhatTu = taiKhoanRepository.findByUsername(loginRequest.getUsername());
        if (optionalPhatTu.isPresent()) {
            // Tài khoản tồn tại, tạo mới hoặc cập nhật token
            RefreshToken refreshToken = createToken(loginRequest.getUsername());
            String jwtToken = jwtService.generateToken(new UserCustomDetails(optionalPhatTu.get()));

            return TokenResponse.builder()
                    .accessToken(jwtToken)
                    .token(refreshToken.getToken())
                    .role(optionalPhatTu.get().getLoaiTaiKhoan().getName().name())
                    .username(optionalPhatTu.get().getUsername())
                    .message("Login thành công")
                    .build();
        } else {
            return TokenResponse.builder()
                    .message("Sai username hoặc password")
                    .build();
        }
    }

    /**
     * Đăng ký tài khoản
     * @param registerRequest
     * @return
     */
    @Override
    public MessageResponse register(RegisterRequest registerRequest) {
        Optional<TaiKhoan> optionalPhatTu = taiKhoanRepository.findByUsername(registerRequest.getUsername());

        if (optionalPhatTu.isPresent()) {
            return MessageResponse.builder().message("Tài khoản đã tồn tại").build();
        }

        Optional<LoaiTaiKhoan> quyenHan = loaiTaiKhoanRepository.findByName(TypeAccountEnum.USER);

        if (quyenHan.isEmpty()) {
            return MessageResponse.builder().message("Quyền hạn không hợp lệ").build();
        }

        TaiKhoan user = TaiKhoan.builder()
                .id(UUID.randomUUID())
                .username(registerRequest.getUsername())
                .matKhau(passwordEncoder.encode(registerRequest.getPassword()))
                .email(registerRequest.getEmail())
                .loaiTaiKhoan(quyenHan.get())
                .build();

        try {
            taiKhoanRepository.save(user);
            sendConfirmationEmail(registerRequest.getEmail(), registerRequest.getUsername(), registerRequest.getPassword());
            return MessageResponse.builder().message("Registered Successfully").build();
        } catch (Exception e) {
            return MessageResponse.builder().message("Lỗi trong quá trình đăng ký").build();
        }
    }

    /**
     * Qyên mật khẩu
     * @param email
     * @return
     */
    @Override
    public MessageResponse forgotPassword(String email) {
        Optional<TaiKhoan> optionalAccount = taiKhoanRepository.findByEmail(email);
        if (optionalAccount.isEmpty()) {
            return MessageResponse.builder().message("Email không tồn tại").build();
        }
        sendConfirmEmailForgotPassWord(email, optionalAccount.get().getId());
        return MessageResponse.builder().message("Thay đổi mật khẩu thành công").build();
    }

    @Override
    public MessageResponse datLaiMatKhau(UUID id, String password) {
        Optional<TaiKhoan> optionalAccount = taiKhoanRepository.findById(id);
        optionalAccount.get().setMatKhau(passwordEncoder.encode(password));
        taiKhoanRepository.save(optionalAccount.get());
        return MessageResponse.builder().message("OK").build();
    }

    /**
     * Send mail khi đăng ký
     * @param email
     * @param username
     * @param password
     */
    private void sendConfirmationEmail(String email, String username, String password) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
        try {
            helper.setTo(email);
            helper.setSubject("Chào mừng bạn đến với Nice Shoe");

            String htmlMsg = "<h1>Chào mừng bạn đến với <span style='color: #ff9900;'>NICE SHOE</span> của chúng tôi!</h1>\n" +
                    "<p>Xin chân thành cảm ơn bạn đã đăng ký nhận <span style='color: #ff9900;'>NICE SHOE</span> của chúng tôi. Chúng tôi sẽ cung cấp cho bạn thông tin cập\n" +
                    "    nhật về tin tức và ưu đãi mới nhất.</p>\n" +
                    "<h3>Ưu điểm của <span style='color: #ff9900;'>NICE SHOE</span>:</h3>\n" +
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

    /**
     * Send mail khi quên mật khẩu
     * @param email
     * @return
     */
    @Override
    public MessageResponse sendConfirmEmailForgotPassWord(String email, UUID id) {
        String confirmationCode = confirmationCodeForgotPassWord();
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(email);
        simpleMailMessage.setSubject("Vui lòng click vào link dưới đây để đặt lại mật khẩu:");
        simpleMailMessage.setText("http://127.0.0.1:5505/src/customer/index-customer.html#/dat-lai-password/" + id);
        javaMailSender.send(simpleMailMessage);
        codeMap.put(confirmationCode, email);
        return MessageResponse.builder().message("Send mã thành công").build();
    }

    /**
     * Tạo token
     * @param username
     * @return
     */
    @Override
    public RefreshToken createToken(String username) {
        RefreshToken refreshToken = RefreshToken
                .builder()
                .taiKhoan(taiKhoanRepository.findByUsername(username).get())
                .token(UUID.randomUUID().toString())
                .thoiGianHetHan(LocalDate.from(LocalDateTime.now().plusMinutes(10)))
                .build();
        return refreshTokenRepository.save(refreshToken);
    }

    /**
     * kiểm tra xem token hết hạn chưa
     * @param token
     * @return
     */
    @Override
    public RefreshToken verifyExpiration(RefreshToken token) {
        // trả về thời gian hết hạn của token < Thời gian hiện tại tức token hết hạn
        if (token.getThoiGianHetHan().compareTo(ChronoLocalDate.from(Instant.now())) < 0) {
            refreshTokenRepository.delete(token); // Xóa token
            throw new RuntimeException(token.getToken() +
                    " Refresh token was expired. Please make a new signin request");
        }
        return token;
    }

    @Override
    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }

    /**
     * Check mã xem có đúng mã không
     * @param code
     * @param email
     * @return
     */
    public boolean checkCode(String code, String email) {
        return codeMap.containsKey(code) && codeMap.get(code).equals(email);
    }

    /**
     * Gen code khi đăng ký account gồm 6 số
     * @return
     */
    @Override
    public String confirmationCodeRegister() {
        return CodeGenerator.generateRandomCode(6);
    }

    /**
     * Gen code khi quên mật khẩu gồm 15 số
     * @return
     */
    @Override
    public String confirmationCodeForgotPassWord() {
        return CodeGenerator.generateRandomCode(15);
    }
}
