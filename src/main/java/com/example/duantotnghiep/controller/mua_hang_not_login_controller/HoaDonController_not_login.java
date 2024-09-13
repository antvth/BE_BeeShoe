package com.example.duantotnghiep.controller.mua_hang_not_login_controller;


import com.example.duantotnghiep.request.not_login.CreateKhachRequest_not_login;
import com.example.duantotnghiep.response.MessageResponse;
import com.example.duantotnghiep.service.mua_hang_not_login_impl.HoaDonServiceImpl_not_login;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.UUID;

@RestController
@RequestMapping("/api/checkout-not-login")
public class HoaDonController_not_login {

    @Autowired
    private HoaDonServiceImpl_not_login hoaDonService_not_login;

    // Endpoint để tạo hóa đơn và thanh toán khi khách hàng không đăng nhập
    @PostMapping("/thanh-toan")
    public MessageResponse thanhToanKhongDangNhap(
            @RequestBody CreateKhachRequest_not_login createKhachRequest_not_login
    ) {
        return hoaDonService_not_login.thanhToanKhongDangNhap(createKhachRequest_not_login);
    }

    // Endpoint để tạo hóa đơn và thanh toán khi khách hàng không đăng nhập
    @PostMapping("/thanh-toan-login")
    public UUID thanhToanLogin(
            @RequestBody CreateKhachRequest_not_login createKhachRequest_not_login,
            Principal principal
    ) {
        return hoaDonService_not_login.thanhToanLogin(createKhachRequest_not_login,principal);
    }

    @PostMapping("/yeu-cau-tra-hang")
    public ResponseEntity<MessageResponse> yeuCauTraHang(
            @RequestParam(name = "idHoaDon") UUID idHoaDon,
            @RequestParam(name = "idHoaDonChiTiet") UUID idHoaDonChiTiet
            ,@RequestParam(name = "lyDo") String lyDo) {
        return new ResponseEntity<>(hoaDonService_not_login.yeuCauTraHang(idHoaDon, idHoaDonChiTiet, lyDo), HttpStatus.CREATED);
    }

    @PostMapping("/vn-pay")
    public ResponseEntity<MessageResponse> createTransactionVnPay(
            @RequestParam(name = "id") UUID id,
            @RequestParam("maGiaoDinh") String maGiaoDinh,
            @RequestParam("vnp_Amount") BigDecimal vnpAmount) {
        return new ResponseEntity<>(hoaDonService_not_login.cashVnPay(id, vnpAmount, maGiaoDinh), HttpStatus.CREATED);
    }

}

