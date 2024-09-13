package com.example.duantotnghiep.service.thuoc_tinh_dong_san_pham_service;

import com.example.duantotnghiep.entity.ThuongHieu;
import com.example.duantotnghiep.request.ThuongHieuRequest;
import com.example.duantotnghiep.response.MessageResponse;
import com.opencsv.exceptions.CsvValidationException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
@Service
public interface ThuongHieuService {

    List<ThuongHieu> getAll();

    List<ThuongHieu> getAllThuongHieu(Integer trangThai, String tenThuongHieu, Integer pageNumber, Integer pageSize);

    ThuongHieu getById(UUID id);

    MessageResponse create(ThuongHieuRequest thuongHieu,String username) throws IOException, CsvValidationException;

    MessageResponse update(UUID id, ThuongHieuRequest thuongHieu,String username) throws IOException, CsvValidationException;

    MessageResponse delete(UUID id);
}
