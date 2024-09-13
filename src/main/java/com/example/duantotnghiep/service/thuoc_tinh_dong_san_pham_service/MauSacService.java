package com.example.duantotnghiep.service.thuoc_tinh_dong_san_pham_service;

import com.example.duantotnghiep.entity.MauSac;
import com.example.duantotnghiep.request.MauSacRequest;
import com.example.duantotnghiep.response.MessageResponse;
import com.opencsv.exceptions.CsvValidationException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
@Service
public interface MauSacService {

    List<MauSac> getAll();

    List<MauSac> getAllMauSac(Integer trangThai, String tenMauSac, Integer pageNumber, Integer pageSize);

    MauSac getById(UUID id);

    MessageResponse create(MauSacRequest mauSacRequest,String username) throws IOException, CsvValidationException;

    MessageResponse update(UUID id, MauSacRequest mauSacRequest,String username) throws IOException, CsvValidationException;

    MessageResponse delete(UUID id);
}
