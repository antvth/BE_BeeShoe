package com.example.duantotnghiep.service.payment;

import com.example.duantotnghiep.request.PaymentRequest;
import com.example.duantotnghiep.response.PaymentResponse;
import jakarta.servlet.http.HttpServletRequest;

public interface VnPayService {

    PaymentResponse callPaymentApi(HttpServletRequest req, Long amountParam);

    PaymentResponse callPaymentApiOnline(HttpServletRequest req, Long amountParam);
}
