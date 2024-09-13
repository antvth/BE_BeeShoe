package com.example.duantotnghiep.controller.mua_hang_not_login_controller;

import com.example.duantotnghiep.service.mua_hang_not_login_impl.GioHangServiceImpl_not_login;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.UUID;

@RestController
@RequestMapping("/api/gio-hang-not-login")
public class GioHangController_not_login {

    @Autowired
    GioHangServiceImpl_not_login gioHangService;

    //TODO tạo mới 1 giỏ hàng
    @PostMapping("/tao-gio-hang")
    public ResponseEntity<UUID> taoGioHang() {
        try {
            UUID gioHangId = gioHangService.taoGioHang();
            return ResponseEntity.ok(gioHangId);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PostMapping("/tao-gio-hang-login")
    public ResponseEntity<UUID> taoGioHangLogin(Principal principal) {

            UUID gioHangId = gioHangService.taoGioHangLogin(principal.getName());
            return ResponseEntity.ok(gioHangId);
    }

    @GetMapping("/get-gio-hang-login")
    public ResponseEntity<UUID> getGioHangLogin(Principal principal) {
            UUID gioHangId = gioHangService.getGioHangLogin(principal);
            return ResponseEntity.ok(gioHangId);

    }
}