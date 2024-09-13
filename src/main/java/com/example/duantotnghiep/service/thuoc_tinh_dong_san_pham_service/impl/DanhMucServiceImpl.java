package com.example.duantotnghiep.service.thuoc_tinh_dong_san_pham_service.impl;

import com.example.duantotnghiep.entity.DanhMuc;
import com.example.duantotnghiep.entity.TaiKhoan;
import com.example.duantotnghiep.repository.DanhMucRepository;
import com.example.duantotnghiep.repository.TaiKhoanRepository;
import com.example.duantotnghiep.request.DanhMucRequest;
import com.example.duantotnghiep.response.MessageResponse;
import com.example.duantotnghiep.service.audi_log_service.AuditLogService;
import com.example.duantotnghiep.service.thuoc_tinh_dong_san_pham_service.DanhMucService;
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
public class DanhMucServiceImpl implements DanhMucService {

    @Autowired
    private DanhMucRepository danhMucRepository;

    private Timestamp timestamp = new Timestamp(System.currentTimeMillis());
    @Override
    public List<DanhMuc> getAll() {
        return danhMucRepository.findByTrangThai(1);
    }

    @Override
    public List<DanhMuc> getAllDanhMuc(Integer trangThai, String tenDanhMuc, Integer pageNumber, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<DanhMuc> pageList = danhMucRepository.getAllDanhMuc(trangThai, tenDanhMuc, pageable);
        return pageList.getContent();
    }


    @Override
    public DanhMuc getById(UUID id) {
        return danhMucRepository.findById(id).orElse(null);
    }

    @Override
    public MessageResponse create(DanhMucRequest request,String username) throws IOException, CsvValidationException {
        DanhMuc danhMuc = new DanhMuc();
        danhMuc.setId(UUID.randomUUID());
        danhMuc.setTenDanhMuc(request.getTenDanhMuc());
        danhMuc.setTrangThai(request.getTrangThai());
        danhMuc.setNgayTao(timestamp);
        danhMucRepository.save(danhMuc);
        return MessageResponse.builder().message("Thêm thành công").build();
    }

    @Override
    public MessageResponse update(UUID id, DanhMucRequest request,String username) throws IOException, CsvValidationException {
        Optional<DanhMuc> danhMucOptional= danhMucRepository.findById(id);
        if (danhMucOptional.isPresent()) {
            DanhMuc danhMuc = danhMucOptional.get();
            danhMuc.setTenDanhMuc(request.getTenDanhMuc());
            danhMuc.setTrangThai(request.getTrangThai());
            danhMuc.setNgayCapNhat(timestamp);
            danhMucRepository.save(danhMuc);
            return MessageResponse.builder().message("Cập nhật thành công").build();
        } else {
            return MessageResponse.builder().message("Không tìm thấy thương hiệu với ID: " + id).build();
        }
    }

    @Override
    public MessageResponse delete(UUID id) {
        Optional<DanhMuc> danhMucOptional= danhMucRepository.findById(id);
        if (danhMucOptional.isPresent()) {
            DanhMuc danhMuc = danhMucOptional.get();
            danhMuc.setTrangThai(2);
            danhMuc.setNgayCapNhat(timestamp);
            danhMucRepository.save(danhMuc);
            return MessageResponse.builder().message("Delete thành công").build();
        } else {
            return MessageResponse.builder().message("Không tìm thấy thương hiệu với ID: " + id).build();
        }
    }
}
