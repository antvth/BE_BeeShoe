package com.example.duantotnghiep.service.thuoc_tinh_dong_san_pham_service.impl;

import com.example.duantotnghiep.entity.Size;
import com.example.duantotnghiep.request.SizeRequest;
import com.example.duantotnghiep.response.MessageResponse;
import com.opencsv.exceptions.CsvValidationException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
public interface SizeService {

    List<Size> getAll();

    List<Size> getAllSize(Integer trangThai, Integer size, Integer pageNumber, Integer pageSize);

    Size getById(UUID id);

    MessageResponse create(SizeRequest sizeRequest,String username) throws IOException, CsvValidationException;

    MessageResponse update(UUID id, SizeRequest sizeRequest,String username) throws IOException, CsvValidationException;

    MessageResponse delete(UUID id);
}
