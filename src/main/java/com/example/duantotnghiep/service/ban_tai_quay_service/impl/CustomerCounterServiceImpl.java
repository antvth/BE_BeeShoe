package com.example.duantotnghiep.service.ban_tai_quay_service.impl;

import com.example.duantotnghiep.entity.*;
import com.example.duantotnghiep.enums.TypeAccountEnum;
import com.example.duantotnghiep.repository.*;
import com.example.duantotnghiep.request.CreateKhachRequest;
import com.example.duantotnghiep.response.KhachHangResponse;
import com.example.duantotnghiep.response.MessageResponse;
import com.example.duantotnghiep.service.audi_log_service.AuditLogService;
import com.example.duantotnghiep.service.ban_tai_quay_service.CustomerCounterService;
import com.example.duantotnghiep.util.RemoveDiacritics;
import com.example.duantotnghiep.util.SendConfirmationEmail;
import com.opencsv.exceptions.CsvValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.Normalizer;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.regex.Pattern;

@Service
public class CustomerCounterServiceImpl implements CustomerCounterService {

    @Autowired
    private KhachHangRepository khachHangRepository;

    @Autowired
    private DiaChiRepository diaChiRepository;

    @Autowired
    private HoaDonRepository hoaDonRepository;

    @Autowired
    private AuditLogService auditLogService;

    @Autowired
    private GioHangRepository gioHangRepository;

    @Autowired
    private LoaiTaiKhoanRepository loaiTaiKhoanRepository;

    @Autowired
    private TaiKhoanRepository taiKhoanRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    SendConfirmationEmail sendConfirmationEmail;

    @Override
    public List<KhachHangResponse> getKhachHang(Integer pageNumber, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<KhachHangResponse> khachHangResponses = khachHangRepository.findlistKhachHang(pageable);
        return khachHangResponses.getContent();
    }

    @Override
    public List<KhachHangResponse> findByKeyToKhachHang(String key) {
        return khachHangRepository.findByKeyToKhachHang(key);
    }

    @Override
    public MessageResponse createKhachHang(CreateKhachRequest createKhachRequest, boolean sendEmail){
        List<TaiKhoan> taiKhoans = khachHangRepository.listKhachHang();
        for (TaiKhoan taiKhoan: khachHangRepository.findAll()) {
            if (taiKhoan.getEmail() != null && taiKhoan.getEmail().equalsIgnoreCase(createKhachRequest.getEmail())) {
                return MessageResponse.builder().message("Email đã tồn tại").build();
            }
        }
        String normalized = RemoveDiacritics.removeDiacritics(createKhachRequest.getHoTen());

        String converted = normalized.toLowerCase().replaceAll("\\s", "");

        LoaiTaiKhoan loaiTaiKhoan = loaiTaiKhoanRepository.findByName(TypeAccountEnum.USER).get();
        TaiKhoan taiKhoan = new TaiKhoan();
        taiKhoan.setId(UUID.randomUUID());
        taiKhoan.setName(createKhachRequest.getHoTen());
        taiKhoan.setSoDienThoai(createKhachRequest.getSoDienThoai());
        taiKhoan.setEmail(createKhachRequest.getEmail());
        taiKhoan.setNgaySinh(createKhachRequest.getNgaySinh());
        taiKhoan.setLoaiTaiKhoan(loaiTaiKhoan);
        taiKhoan.setTrangThai(1);
        taiKhoan.setUsername(converted + taiKhoans.size() + 1);
        taiKhoan.setMatKhau(passwordEncoder.encode(converted));
        taiKhoan.setMaTaiKhoan(converted + taiKhoans.size() + 1);
        khachHangRepository.save(taiKhoan);
        DiaChi diaChi = new DiaChi();
        diaChi.setId(UUID.randomUUID());
        diaChi.setDiaChi(createKhachRequest.getDiaChi());
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
    public MessageResponse updateHoaDon(UUID id, UUID idHoaDon, UUID idGioHang, String username) throws IOException, CsvValidationException {

        Optional<TaiKhoan> findByNhanVien = taiKhoanRepository.findByUsername(username);

        Optional<HoaDon> hoaDon = hoaDonRepository.findById(idHoaDon);
        if (hoaDon.isEmpty()) {
            return MessageResponse.builder().message("Hóa Đơn Null").build();
        }
        Optional<TaiKhoan> khachHang = khachHangRepository.findById(id);
        if (khachHang.isEmpty()) {
            return MessageResponse.builder().message("Khách Hàng Null").build();
        }
        hoaDon.get().setTaiKhoanKhachHang(khachHang.get());
        hoaDonRepository.save(hoaDon.get());

        GioHang gioHang = gioHangRepository.findById(idGioHang).get();
        gioHang.setTaiKhoan(khachHang.get());
        gioHangRepository.save(gioHang);

        auditLogService.writeAuditLogHoadon(findByNhanVien.get().getMaTaiKhoan(), hoaDon.get().getMa(), "Cập nhật khách hàng", hoaDon.get().getMa(),  "Mã khách hàng: " +khachHang.get().getMaTaiKhoan() , "Tên khách hàng: "+ khachHang.get().getName(),  "", "");

        return MessageResponse.builder().message("Update Thành Công").build();
    }

    @Override
    public KhachHangResponse detailKhachHang(UUID id) {
        return khachHangRepository.detailKhachHang(id);
    }

}
