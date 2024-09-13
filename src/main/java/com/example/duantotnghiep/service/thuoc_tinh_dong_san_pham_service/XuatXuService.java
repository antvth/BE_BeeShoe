package com.example.duantotnghiep.service.thuoc_tinh_dong_san_pham_service;

import com.example.duantotnghiep.entity.XuatXu;
import com.example.duantotnghiep.request.XuatXuRequest;
import com.example.duantotnghiep.response.MessageResponse;
import com.opencsv.exceptions.CsvValidationException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
@Service
public interface XuatXuService {

    List<XuatXu> getAll();

    List<XuatXu> getAllXuatXu(Integer trangThai, String tenXuatXu, Integer pageNumber, Integer pageSize);

    XuatXu getById(UUID id);

    MessageResponse create(XuatXuRequest xuatXuRequest,String username) throws IOException, CsvValidationException;

    MessageResponse update(UUID id, XuatXuRequest xuatXuRequest,String username) throws IOException, CsvValidationException;

    MessageResponse delete(UUID id);

    List<XuatXu> findByTenXuatXu(String name);
}
