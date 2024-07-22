package com.example.duantotnghiep.service.ban_tai_quay_service;

import com.example.duantotnghiep.request.CreateKhachRequest;
import com.example.duantotnghiep.response.KhachHangResponse;
import com.example.duantotnghiep.response.MessageResponse;
import com.opencsv.exceptions.CsvValidationException;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

public interface CustomerCounterService {

    List<KhachHangResponse> getKhachHang(Integer pageNumber, Integer pageSize);

    List<KhachHangResponse> findByKeyToKhachHang(String key);

    MessageResponse createKhachHang(CreateKhachRequest createKhachRequest, boolean sendEmail);

    MessageResponse updateHoaDon(UUID id, UUID idHoaDon, UUID idGioHang, String username) throws IOException, CsvValidationException;

    KhachHangResponse detailKhachHang(UUID id);

}
