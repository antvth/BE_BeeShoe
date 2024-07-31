package com.example.duantotnghiep.service.thuoc_tinh_dong_san_pham_service.impl;

import com.example.duantotnghiep.entity.KieuDe;
import com.example.duantotnghiep.request.KieuDeRequest;
import com.example.duantotnghiep.response.MessageResponse;
import com.opencsv.exceptions.CsvValidationException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
public interface KieuDeService {

    List<KieuDe> getAll();

    List<KieuDe> getAllKieuDe(Integer trangThai, String tenDe, Integer pageNumber, Integer pageSize);

    KieuDe getById(UUID id);

    MessageResponse create(KieuDeRequest kieuDeRequest,String username) throws IOException, CsvValidationException;

    MessageResponse update(UUID id, KieuDeRequest kieuDeRequest,String username) throws IOException, CsvValidationException;

    MessageResponse delete(UUID id);
}
