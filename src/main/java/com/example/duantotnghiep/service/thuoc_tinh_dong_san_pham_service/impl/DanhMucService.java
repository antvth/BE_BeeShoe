package com.example.duantotnghiep.service.thuoc_tinh_dong_san_pham_service.impl;

import com.example.duantotnghiep.entity.DanhMuc;
import com.example.duantotnghiep.request.DanhMucRequest;
import com.example.duantotnghiep.response.MessageResponse;
import com.opencsv.exceptions.CsvValidationException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
public interface DanhMucService {

    List<DanhMuc> getAll();

    List<DanhMuc> getAllDanhMuc(Integer trangThai, String tenDanhMuc, Integer pageNumber, Integer pageSize);

    DanhMuc getById(UUID id);

    MessageResponse create(DanhMucRequest danhMucRequest,String username) throws IOException, CsvValidationException;

    MessageResponse update(UUID id, DanhMucRequest danhMucRequest,String username) throws IOException, CsvValidationException;

    MessageResponse delete(UUID id);

}
