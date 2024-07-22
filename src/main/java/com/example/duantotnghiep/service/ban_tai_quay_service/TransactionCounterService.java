package com.example.duantotnghiep.service.ban_tai_quay_service;

import com.example.duantotnghiep.request.TransactionRequest;
import com.example.duantotnghiep.request.TransactionVnPayRequest;
import com.example.duantotnghiep.response.MessageResponse;
import com.example.duantotnghiep.response.TransactionResponse;
import com.fasterxml.jackson.databind.JsonNode;
import com.opencsv.exceptions.CsvValidationException;
import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public interface TransactionCounterService {

    MessageResponse createTransaction(UUID idHoaDon, UUID id, TransactionRequest transactionRequest, String username) throws IOException, CsvValidationException;

    List<TransactionResponse> findAllTran(UUID id);

    MessageResponse cashVnPay(UUID idHoaDon, UUID id, BigDecimal vnpAmount, String maGiaoDinh, String username) throws IOException, CsvValidationException;


}
