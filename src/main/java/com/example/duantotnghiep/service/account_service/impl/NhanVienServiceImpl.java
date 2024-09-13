package com.example.duantotnghiep.service.account_service.impl;

import com.example.duantotnghiep.entity.DiaChi;
import com.example.duantotnghiep.entity.LoaiTaiKhoan;
import com.example.duantotnghiep.entity.TaiKhoan;
import com.example.duantotnghiep.enums.TypeAccountEnum;
import com.example.duantotnghiep.repository.DiaChiRepository;
import com.example.duantotnghiep.repository.KhachHangRepository;
import com.example.duantotnghiep.repository.LoaiTaiKhoanRepository;
import com.example.duantotnghiep.repository.TaiKhoanRepository;
import com.example.duantotnghiep.request.NhanVienDTORequest;
import com.example.duantotnghiep.response.HoaDonDTOResponse;
import com.example.duantotnghiep.response.MessageResponse;
import com.example.duantotnghiep.response.NhanVienDTOReponse;
import com.example.duantotnghiep.response.NhanVienOrderResponse;
import com.example.duantotnghiep.service.account_service.NhanVienCustomService;
import com.example.duantotnghiep.service.audi_log_service.AuditLogService;
import com.example.duantotnghiep.util.RemoveDiacritics;
import com.example.duantotnghiep.util.SendConfirmationEmail;
import com.opencsv.exceptions.CsvValidationException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Timestamp;
import java.text.Normalizer;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class NhanVienServiceImpl implements NhanVienCustomService {

    @Autowired
    private TaiKhoanRepository taiKhoanRepository;

    @Autowired
    private KhachHangRepository khachHangRepository;

    @Autowired
    private LoaiTaiKhoanRepository loaiTaiKhoanRepository;

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private DiaChiRepository diaChiRepository;
    @Autowired
    private AuditLogService auditLogService;

    @Autowired
    SendConfirmationEmail sendConfirmationEmail;


    @Override
    public List<NhanVienDTOReponse> getAllNhanVien(String maTaiKhoan, String name, String soDienThoai, Integer trangThai, Integer pageNumber, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        List<Integer> trangThaiList = Arrays.asList(1, 2, 5);
        Page<NhanVienDTOReponse> pageList = taiKhoanRepository.getAllNhanVien(trangThaiList, maTaiKhoan, name, soDienThoai, trangThai, pageable);
        return pageList.getContent();
    }

    @Override
    public TaiKhoan getById(UUID id) {
        return taiKhoanRepository.findById(id).orElse(null);
    }

    @Override
    public NhanVienDTOReponse getNhanVienById(UUID id) {
        return taiKhoanRepository.getNhanVienById(id);
    }

    @Override
    public MessageResponse create(MultipartFile file, NhanVienDTORequest request, boolean sendEmail,String username) throws IOException, CsvValidationException {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        TaiKhoan taiKhoanUser = taiKhoanRepository.findByUsername(username).orElse(null);
        String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
        List<NhanVienOrderResponse> taiKhoans = khachHangRepository.listNv();
        try {
            Files.copy(file.getInputStream(), Paths.get("D:\\DATN_BEESHOE\\assets\\ảnh giày", fileName), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String normalized = RemoveDiacritics.removeDiacritics(request.getFullName());

        String converted = normalized.toLowerCase().replaceAll("\\s", "");

        LoaiTaiKhoan loaiTaiKhoan = loaiTaiKhoanRepository.findByName(TypeAccountEnum.STAFF).get();
        TaiKhoan taiKhoan = new TaiKhoan();
        taiKhoan.setId(UUID.randomUUID());
        taiKhoan.setName(request.getFullName());
        taiKhoan.setEmail(request.getEmail());
        taiKhoan.setSoDienThoai(request.getSoDienThoai());
        taiKhoan.setImage(fileName);
        taiKhoan.setGioiTinh(request.getGioiTinh());
        taiKhoan.setNgaySinh(request.getNgaySinh());
        taiKhoan.setTrangThai(request.getTrangThai());
        taiKhoan.setMaTaiKhoan(converted + taiKhoans.size() + 1);
        taiKhoan.setUsername(converted + taiKhoans.size() + 1);
        taiKhoan.setMatKhau(passwordEncoder.encode(converted));
        taiKhoan.setLoaiTaiKhoan(loaiTaiKhoan);
        taiKhoan.setNgayTao(timestamp);
        taiKhoanRepository.save(taiKhoan);

        DiaChi diaChi = new DiaChi();
        diaChi.setId(UUID.randomUUID());
        diaChi.setDiaChi(request.getDiaChi());
        diaChi.setTaiKhoan(taiKhoan);
        diaChi.setTrangThai(1);
        diaChiRepository.save(diaChi);
        if (sendEmail) {
            sendConfirmationEmail.sendConfirmationEmailStatic(taiKhoan.getEmail(), taiKhoan.getUsername(), converted);
            System.out.println("gửi mail");
        }
        return MessageResponse.builder().message("Thêm Thành Công").build();
    }

    @Override
    public MessageResponse update(MultipartFile file, UUID id, NhanVienDTORequest request,String username) throws IOException, CsvValidationException {
        String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
        Optional<TaiKhoan> optionalTaiKhoan = khachHangRepository.findById(id);
        TaiKhoan taiKhoanUser = taiKhoanRepository.findByUsername(username).orElse(null);
        try {
            Files.copy(file.getInputStream(), Paths.get("D:\\DATN_BEESHOE\\assets\\ảnh giày", fileName), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        if (optionalTaiKhoan.isPresent()) {
            TaiKhoan taiKhoan = optionalTaiKhoan.get();

            taiKhoan.setName(request.getFullName());
            taiKhoan.setEmail(request.getEmail());
            taiKhoan.setSoDienThoai(request.getSoDienThoai());
            taiKhoan.setGioiTinh(request.getGioiTinh());
            taiKhoan.setNgaySinh(request.getNgaySinh());
            taiKhoan.setTrangThai(request.getTrangThai());
            taiKhoan.setImage(fileName);

            DiaChi diaChi = diaChiRepository.findByDiaChi(taiKhoan.getId());
            diaChi.setDiaChi(request.getDiaChi());

            diaChiRepository.save(diaChi);
            khachHangRepository.save(taiKhoan);
            return MessageResponse.builder().message("Cập Nhật Thành Công").build();
        } else {
            return MessageResponse.builder().message("Không Tìm Thấy Khách Hàng").build();
        }
    }

    @Override
    public MessageResponse delete(UUID id) {
        Optional<TaiKhoan> taiKhoanOptional = taiKhoanRepository.findById(id);
        if (taiKhoanOptional.isPresent()) {
            taiKhoanOptional.get().setTrangThai(2);
            return MessageResponse.builder().message("Delete thành công").build();
        } else {
            return MessageResponse.builder().message("Không tìm thấy thương hiệu với ID: " + id).build();
        }
    }

}
