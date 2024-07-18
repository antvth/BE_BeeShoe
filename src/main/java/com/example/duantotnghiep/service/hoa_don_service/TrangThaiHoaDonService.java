package com.example.duantotnghiep.service.hoa_don_service;

import com.example.duantotnghiep.request.ConfirmOrderClientRequest;
import com.example.duantotnghiep.request.ConfirmOrderDeliver;
import com.example.duantotnghiep.request.TrangThaiHoaDonRequest;
import com.example.duantotnghiep.response.MessageResponse;
import com.opencsv.exceptions.CsvValidationException;

import java.io.IOException;
import java.util.UUID;

public interface TrangThaiHoaDonService {

    MessageResponse confirmOrder(UUID hoadonId, TrangThaiHoaDonRequest request, String name, boolean sendEmail);

    MessageResponse confirmOrderClient(UUID hoadonId, ConfirmOrderClientRequest request, String username) throws IOException, CsvValidationException;

    MessageResponse confirmOrderDeliver(UUID hoadonId, ConfirmOrderDeliver request);


}
