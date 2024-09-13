package com.example.duantotnghiep.service.thuoc_tinh_dong_san_pham_service.impl;

import com.example.duantotnghiep.entity.TaiKhoan;
import com.example.duantotnghiep.entity.XuatXu;
import com.example.duantotnghiep.repository.TaiKhoanRepository;
import com.example.duantotnghiep.repository.XuatSuRepository;
import com.example.duantotnghiep.request.XuatXuRequest;
import com.example.duantotnghiep.response.MessageResponse;
import com.example.duantotnghiep.service.audi_log_service.AuditLogService;
import com.example.duantotnghiep.service.thuoc_tinh_dong_san_pham_service.XuatXuService;
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
public class XuatXuServiceImpl implements XuatXuService {

    @Autowired
    private XuatSuRepository xuatSuRepository;
    private Timestamp timestamp = new Timestamp(System.currentTimeMillis());

    @Override
    public List<XuatXu> getAll() {
        return xuatSuRepository.findByTrangThai(1);
    }

    @Override
    public List<XuatXu> getAllXuatXu(Integer trangThai, String tenXuatXu, Integer pageNumber, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<XuatXu> pageList = xuatSuRepository.getAllXuatXu(trangThai, tenXuatXu, pageable);
        return pageList.getContent();
    }

    @Override
    public XuatXu getById(UUID id) {
        return xuatSuRepository.findById(id).orElse(null);
    }

    @Override
    public MessageResponse create(XuatXuRequest request, String username) throws IOException, CsvValidationException {
        XuatXu xuatXu = new XuatXu();
        xuatXu.setId(UUID.randomUUID());
        xuatXu.setTenXuatXu(request.getTenXuatXu());
        xuatXu.setTrangThai(request.getTrangThai());
        xuatXu.setNgayTao(timestamp);
        xuatSuRepository.save(xuatXu);
        return MessageResponse.builder().message("Thêm thành công").build();
    }

    @Override
    public MessageResponse update(UUID id, XuatXuRequest request, String username) throws IOException, CsvValidationException {
        Optional<XuatXu> optionalXuatXu = xuatSuRepository.findById(id);
        if (optionalXuatXu.isPresent()) {
            XuatXu xuatXu = optionalXuatXu.get();
            xuatXu.setTenXuatXu(request.getTenXuatXu());
            xuatXu.setTrangThai(request.getTrangThai());
            xuatXu.setNgayCapNhat(timestamp);
            xuatSuRepository.save(xuatXu);
            return MessageResponse.builder().message("Cập nhật thành công").build();
        } else {
            return MessageResponse.builder().message("Không tìm thấy thương hiệu với ID: " + id).build();
        }
    }

    @Override
    public MessageResponse delete(UUID id) {
        Optional<XuatXu> optionalXuatXu = xuatSuRepository.findById(id);
        if (optionalXuatXu.isPresent()) {
            XuatXu xuatXu = optionalXuatXu.get();
            xuatXu.setTrangThai(2);
            xuatXu.setNgayCapNhat(timestamp);
            xuatSuRepository.save(xuatXu);
            return MessageResponse.builder().message("Delete thành công").build();
        } else {
            return MessageResponse.builder().message("Không tìm thấy thương hiệu với ID: " + id).build();
        }
    }

    @Override
    public List<XuatXu> findByTenXuatXu(String name) {
        List<XuatXu> xuatXus = xuatSuRepository.findByTenXuatXu(name);
        return xuatXus;
    }
}
