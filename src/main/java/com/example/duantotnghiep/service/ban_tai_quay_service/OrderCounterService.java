package com.example.duantotnghiep.service.ban_tai_quay_service;

import com.example.duantotnghiep.request.HoaDonGiaoThanhToanRequest;
import com.example.duantotnghiep.request.HoaDonThanhToanRequest;
import com.example.duantotnghiep.response.*;
import com.example.duantotnghiep.response.IdGioHangResponse;
import com.example.duantotnghiep.response.OrderCounterCResponse;
import com.example.duantotnghiep.response.OrderCounterCartsResponse;

import com.opencsv.exceptions.CsvValidationException;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

public interface OrderCounterService {

    OrderCounterCResponse taoHoaDon(String name) throws IOException, CsvValidationException;

    List<HoaDonResponse> viewHoaDonTaiQuay(Integer pageNumber, Integer pageSize);

    List<HoaDonResponse> findByCodeOrder(String ma);

    MessageResponse updateHoaDon(UUID idHoaDon, HoaDonThanhToanRequest hoaDonThanhToanRequest, String username) throws IOException, CsvValidationException;

    OrderCounterCartsResponse findByHoaDon(UUID id);

    IdGioHangResponse showIdGioHangCt(UUID id);

    MessageResponse removeOrder(UUID id, String username) throws IOException, CsvValidationException;

}
