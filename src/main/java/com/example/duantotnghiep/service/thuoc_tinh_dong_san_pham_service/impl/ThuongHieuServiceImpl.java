package com.example.duantotnghiep.service.thuoc_tinh_dong_san_pham_service.impl;

import com.example.duantotnghiep.entity.TaiKhoan;
import com.example.duantotnghiep.entity.ThuongHieu;
import com.example.duantotnghiep.repository.TaiKhoanRepository;
import com.example.duantotnghiep.repository.ThuongHieuRepository;
import com.example.duantotnghiep.request.ThuongHieuRequest;
import com.example.duantotnghiep.response.MessageResponse;
import com.example.duantotnghiep.service.audi_log_service.AuditLogService;
import com.example.duantotnghiep.service.thuoc_tinh_dong_san_pham_service.ThuongHieuService;
import com.opencsv.exceptions.CsvValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ThuongHieuServiceImpl implements ThuongHieuService {

    @Autowired
    private ThuongHieuRepository thuongHieuRepository;

    private Timestamp timestamp = new Timestamp(System.currentTimeMillis());
    @Override
    public List<ThuongHieu> getAll() {
        return thuongHieuRepository.findByTrangThai(1);
    }

    @Override
    public List<ThuongHieu> getAllThuongHieu(Integer trangThai, String tenThuongHieu, Integer pageNumber, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<ThuongHieu> pageList = thuongHieuRepository.getAllThuongHieu(trangThai, tenThuongHieu, pageable);
        return pageList.getContent();
    }

    @Override
    public ThuongHieu getById(UUID id) {
        return thuongHieuRepository.findById(id).orElse(null);
    }

    @Override
    public MessageResponse create(ThuongHieuRequest request,String username) throws IOException, CsvValidationException {
        ThuongHieu thuongHieu = new ThuongHieu();
        thuongHieu.setId(UUID.randomUUID());
        thuongHieu.setTenThuongHieu(request.getTenThuongHieu());
        thuongHieu.setTrangThai(request.getTrangThai());
        thuongHieu.setNgayTao(timestamp);
        thuongHieuRepository.save(thuongHieu);
        return MessageResponse.builder().message("Thêm thành công").build();
    }

    @Override
    public MessageResponse update(UUID id, ThuongHieuRequest request,String username) throws IOException, CsvValidationException {
        Optional<ThuongHieu> optionalThuongHieu = thuongHieuRepository.findById(id);
        if (optionalThuongHieu.isPresent()) {
            ThuongHieu thuongHieu = optionalThuongHieu.get();
            thuongHieu.setTenThuongHieu(request.getTenThuongHieu());
            thuongHieu.setTrangThai(request.getTrangThai());
            thuongHieu.setNgayCapNhat(timestamp);
            thuongHieuRepository.save(thuongHieu);
            return MessageResponse.builder().message("Cập nhật thành công").build();
        } else {
            return MessageResponse.builder().message("Không tìm thấy thương hiệu với ID: " + id).build();
        }
    }

    @Override
    public MessageResponse delete(UUID id) {
        Optional<ThuongHieu> optionalThuongHieu = thuongHieuRepository.findById(id);
        if (optionalThuongHieu.isPresent()) {
            ThuongHieu thuongHieu = optionalThuongHieu.get();
            thuongHieu.setTrangThai(2);
            thuongHieu.setNgayCapNhat(timestamp);
            thuongHieuRepository.save(thuongHieu);
            return MessageResponse.builder().message("Delete thành công").build();
        } else {
            return MessageResponse.builder().message("Không tìm thấy thương hiệu với ID: " + id).build();
        }
    }
}
