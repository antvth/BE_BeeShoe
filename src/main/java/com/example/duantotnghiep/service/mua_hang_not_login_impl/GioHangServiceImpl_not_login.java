package com.example.duantotnghiep.service.mua_hang_not_login_impl;

import com.example.duantotnghiep.entity.GioHang;
import com.example.duantotnghiep.entity.TaiKhoan;
import com.example.duantotnghiep.repository.TaiKhoanRepository;
import com.example.duantotnghiep.repository.mua_hang_not_login_repo.GioHangRepository_not_login;
import com.example.duantotnghiep.service.mua_hang_not_login_service.GioHangService_not_login;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.sql.Date;
import java.util.Optional;
import java.util.UUID;

@Service
public class GioHangServiceImpl_not_login implements GioHangService_not_login {

    @Autowired
    private GioHangRepository_not_login gioHangRepository;

    @Autowired
    private TaiKhoanRepository taiKhoanRepository;


    public UUID taoGioHang() {
        GioHang gioHang = new GioHang();
        gioHang.setId(UUID.randomUUID());
        gioHang.setNgayTao(new Date(System.currentTimeMillis()));
        gioHang.setTrangThai(1);

        GioHang gioHangMoi = gioHangRepository.save(gioHang);
        return gioHangMoi.getId();
    }

    public UUID taoGioHangLogin(String name) {
        GioHang gioHang = new GioHang();
        gioHang.setId(UUID.randomUUID());
        gioHang.setNgayTao(new Date(System.currentTimeMillis()));
        gioHang.setTrangThai(1);

        Optional<TaiKhoan> taiKhoan = taiKhoanRepository.findByUsername(name);

        gioHang.setTaiKhoan(taiKhoan.get());
        GioHang gioHangMoi = gioHangRepository.save(gioHang);
        return gioHangMoi.getId();
    }


    public UUID getGioHangLogin(Principal principal) {
            String username = principal.getName();

            UUID gioHangId = gioHangRepository.getGioHangLogin(username);

            // Kiểm tra xem giỏ hàng có tồn tại không
            if (gioHangId != null) {
                return gioHangId;
            } else {
                return null;
            }
    }
}