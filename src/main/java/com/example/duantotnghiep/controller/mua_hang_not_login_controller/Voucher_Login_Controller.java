package com.example.duantotnghiep.controller.mua_hang_not_login_controller;

import com.example.duantotnghiep.entity.Voucher;
import com.example.duantotnghiep.mapper.not_login.voucher_map_login;
import com.example.duantotnghiep.request.VoucherRequest;
import com.example.duantotnghiep.response.MessageResponse;
import com.example.duantotnghiep.service.mua_hang_not_login_impl.Voucher_Login_Serviceimpl;
import com.example.duantotnghiep.service.voucher_service.impl.VoucherServiceimpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/voucher-login/")
public class Voucher_Login_Controller {
    @Autowired
    private Voucher_Login_Serviceimpl Service;

    @GetMapping("show")
    public ResponseEntity<List<voucher_map_login>> getAll() {
        return new ResponseEntity<>(Service.getVouchers(), HttpStatus.OK);
    }

}
