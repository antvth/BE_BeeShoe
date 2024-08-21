package com.example.duantotnghiep.controller;

import com.example.duantotnghiep.response.PaymentResponse;
import com.example.duantotnghiep.service.payment.impl.VnPayServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/payment/")
public class VnpayController {

    @Autowired
    private VnPayServiceImpl vnPayService;

    @PostMapping("vn_pay")
    public ResponseEntity<PaymentResponse> transfer(
            HttpServletRequest req, @RequestParam("amountParam") Long amountParam)
    {
        return ResponseEntity.ok(vnPayService.callPaymentApi(req,amountParam));
    }
}
