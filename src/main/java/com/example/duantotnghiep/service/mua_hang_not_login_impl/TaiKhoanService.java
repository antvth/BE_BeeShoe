package com.example.duantotnghiep.service.mua_hang_not_login_impl;

import com.example.duantotnghiep.entity.TaiKhoan;
import com.example.duantotnghiep.mapper.not_login.TaiKhoanDto;
import com.example.duantotnghiep.mapper.not_login.loadDiaChi_not_login;
import com.example.duantotnghiep.repository.TaiKhoanRepository;
import com.example.duantotnghiep.repository.mua_hang_not_login_repo.DiaChiRepository_not_login;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

@Service
public class TaiKhoanService {

    private final TaiKhoanRepository taiKhoanRepository;

    @Autowired
    public TaiKhoanService(TaiKhoanRepository taiKhoanRepository) {
        this.taiKhoanRepository = taiKhoanRepository;
    }

    @Autowired
    DiaChiRepository_not_login diaChiRepository_not_login;

    public TaiKhoanDto getProfileByUsername(String username) {
        Optional<TaiKhoan> taiKhoanOptional = taiKhoanRepository.findByUsername(username);

        if (taiKhoanOptional.isPresent()) {
            TaiKhoan taiKhoan = taiKhoanOptional.get();
            TaiKhoanDto taiKhoanDto = new TaiKhoanDto();
            taiKhoanDto.setUsername(taiKhoan.getUsername());
            taiKhoanDto.setHoTen(taiKhoan.getName());
            taiKhoanDto.setSoDienThoai(taiKhoan.getSoDienThoai());
            taiKhoanDto.setEmail(taiKhoan.getEmail());
            taiKhoanDto.setNgaySinh(taiKhoan.getNgaySinh());
            taiKhoanDto.setGioiTinh(taiKhoan.getGioiTinh());

            return taiKhoanDto;
        } else {
            return null;
        }
    }

    public List<loadDiaChi_not_login> loadDiaChi(String name){
        return diaChiRepository_not_login.loadDiaChi(name);
    }
    public ResponseEntity<String> updateProfile(String username, TaiKhoanDto updatedData) {
        Optional<TaiKhoan> taiKhoanOptional = taiKhoanRepository.findByUsername(username);

        if (taiKhoanOptional.isPresent()) {
            TaiKhoan taiKhoan = taiKhoanOptional.get();

            // Cập nhật thông tin tài khoản
            taiKhoan.setName(updatedData.getHoTen());
            taiKhoan.setSoDienThoai(updatedData.getSoDienThoai());
            taiKhoan.setEmail(updatedData.getEmail());
            taiKhoan.setNgaySinh(updatedData.getNgaySinh());
            taiKhoan.setGioiTinh(updatedData.getGioiTinh());

            // Lưu lại vào cơ sở dữ liệu
            taiKhoanRepository.save(taiKhoan);

            return new ResponseEntity<>("Đã cập nhật thông tin tài khoản thành công", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Không tìm thấy tài khoản để cập nhật", HttpStatus.NOT_FOUND);
        }
    }
}


