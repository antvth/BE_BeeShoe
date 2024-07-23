package com.example.duantotnghiep.service.quan_ly_nhan_vien_khach_hang.impl;

import com.example.duantotnghiep.entity.DiaChi;
import com.example.duantotnghiep.entity.LoaiTaiKhoan;
import com.example.duantotnghiep.entity.TaiKhoan;
import com.example.duantotnghiep.enums.TypeAccountEnum;
import com.example.duantotnghiep.repository.DiaChiRepository;
import com.example.duantotnghiep.repository.KhachHangRepository;
import com.example.duantotnghiep.repository.LoaiTaiKhoanRepository;
import com.example.duantotnghiep.repository.TaiKhoanRepository;
import com.example.duantotnghiep.request.CreateQLKhachHangRequest;
import com.example.duantotnghiep.response.MessageResponse;
import com.example.duantotnghiep.response.QLKhachHangResponse;
import com.example.duantotnghiep.service.audi_log_service.AuditLogService;
import com.example.duantotnghiep.service.quan_ly_nhan_vien_khach_hang.QLKhachHangService;
import com.example.duantotnghiep.util.SendConfirmationEmail;
import com.opencsv.exceptions.CsvValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class QLKhachHangImpl implements QLKhachHangService {

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
    private TaiKhoanRepository taiKhoanRepository;

    @Autowired
    SendConfirmationEmail sendConfirmationEmail;

    @Override
    public List<QLKhachHangResponse> getQLKhachHang(Integer trangThai, String name, String soDienThoai, String maTaiKhoan, Integer pageNumber, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<QLKhachHangResponse> pageList = khachHangRepository.findlistQLKhachHang(trangThai, name, soDienThoai, maTaiKhoan, pageable);
        return pageList.getContent();
    }

    @Override
    public MessageResponse createKhachHang(MultipartFile file, CreateQLKhachHangRequest createQLKhachHangRequest, boolean sendEmail,String username) throws IOException, CsvValidationException {
        TaiKhoan taiKhoanUser = taiKhoanRepository.findByUsername(username).orElse(null);
        String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
        List<TaiKhoan> taiKhoans = khachHangRepository.listKhachHang();
        try {
            Files.copy(file.getInputStream(), Paths.get("D:\\DATN_BEESHOE\\assets\\ảnh giày", fileName), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String normalized = RemoveDiacritics.removeDiacritics(createQLKhachHangRequest.getTen());

        String converted = normalized.toLowerCase().replaceAll("\\s", "");

        LoaiTaiKhoan loaiTaiKhoan = loaiTaiKhoanRepository.findByName(TypeAccountEnum.USER).get();
        TaiKhoan taiKhoan = new TaiKhoan();
        taiKhoan.setId(UUID.randomUUID());
        taiKhoan.setName(createQLKhachHangRequest.getTen());
        taiKhoan.setEmail(createQLKhachHangRequest.getEmail());
        taiKhoan.setSoDienThoai(createQLKhachHangRequest.getSoDienThoai());
        taiKhoan.setImage(fileName);
        taiKhoan.setGioiTinh(createQLKhachHangRequest.getGioiTinh());
        taiKhoan.setNgaySinh(createQLKhachHangRequest.getNgaySinh());
        taiKhoan.setTrangThai(createQLKhachHangRequest.getTrangThai());
        taiKhoan.setMaTaiKhoan(converted + taiKhoans.size() + 1);
        taiKhoan.setUsername(converted + taiKhoans.size() + 1);
        taiKhoan.setMatKhau(passwordEncoder.encode(converted));
        taiKhoan.setLoaiTaiKhoan(loaiTaiKhoan);
        khachHangRepository.save(taiKhoan);

        DiaChi diaChi = new DiaChi();
        diaChi.setId(UUID.randomUUID());
        diaChi.setDiaChi(createQLKhachHangRequest.getDiaChi());
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
    public QLKhachHangResponse detailKhachHang(UUID id) {
        return khachHangRepository.detailQLKhachHang(id);
    }

    @Override
    public MessageResponse updateKhachHang(MultipartFile file, UUID khachHangId, CreateQLKhachHangRequest createQLKhachHangRequest,String username) throws IOException, CsvValidationException {
        String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
        TaiKhoan taiKhoanUser = taiKhoanRepository.findByUsername(username).orElse(null);
        Optional<TaiKhoan> optionalTaiKhoan = khachHangRepository.findById(khachHangId);
        try {
            Files.copy(file.getInputStream(), Paths.get("D:\\DATN_BEESHOE\\assets\\ảnh giày", fileName), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        if (optionalTaiKhoan.isPresent()) {
            TaiKhoan taiKhoan = optionalTaiKhoan.get();

            taiKhoan.setName(createQLKhachHangRequest.getTen());
            taiKhoan.setEmail(createQLKhachHangRequest.getEmail());
            taiKhoan.setSoDienThoai(createQLKhachHangRequest.getSoDienThoai());
            taiKhoan.setGioiTinh(createQLKhachHangRequest.getGioiTinh());
            taiKhoan.setNgaySinh(createQLKhachHangRequest.getNgaySinh());
            taiKhoan.setTrangThai(createQLKhachHangRequest.getTrangThai());
            taiKhoan.setImage(fileName);

            DiaChi diaChi = diaChiRepository.findByDiaChi(taiKhoan.getId());
            diaChi.setDiaChi(createQLKhachHangRequest.getDiaChi());
            diaChiRepository.save(diaChi);
            khachHangRepository.save(taiKhoan);

            return MessageResponse.builder().message("Cập Nhật Thành Công").build();
        } else {
            return MessageResponse.builder().message("Không Tìm Thấy Khách Hàng").build();
        }
    }

    // Thêm method tìm khách hàng theo số điện thoại
    public List<TaiKhoan> findKhachHangBySoDienThoai(String soDienThoai) {
        return khachHangRepository.findBySoDienThoai(soDienThoai);
    }

    // Thêm method tìm khách hàng theo email
    public List<TaiKhoan> findKhachHangByEmail(String email) {
        return khachHangRepository.findByEmail(email);
    }

}
