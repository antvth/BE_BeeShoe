package com.example.duantotnghiep.service.ban_tai_quay_service;

import com.example.duantotnghiep.response.MessageResponse;
import com.example.duantotnghiep.response.LoadVoucherCounterResponse;
import com.example.duantotnghiep.response.VoucherCounterResponse;
import com.opencsv.exceptions.CsvValidationException;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public interface VoucherCounterService {

    List<VoucherCounterResponse> findAll(Integer pageNumber, Integer pageSize);

    MessageResponse addVoucherOrder(UUID idHoaDon, UUID idVoucher, BigDecimal thanhTien, String username) throws IOException, CsvValidationException;

    MessageResponse closeVoucherOrder(UUID idHoaDon, BigDecimal thanhTien);

    String findByName(UUID id);

    LoadVoucherCounterResponse voucherResponse(UUID idHoaDon);
}
