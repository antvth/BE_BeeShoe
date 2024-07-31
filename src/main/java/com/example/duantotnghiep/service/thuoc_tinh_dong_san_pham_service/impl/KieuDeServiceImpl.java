package com.example.duantotnghiep.service.thuoc_tinh_dong_san_pham_service.impl;

import com.example.duantotnghiep.entity.KieuDe;
import com.example.duantotnghiep.repository.KieuDeRepository;
import com.example.duantotnghiep.request.KieuDeRequest;
import com.example.duantotnghiep.response.MessageResponse;
import com.example.duantotnghiep.service.thuoc_tinh_dong_san_pham_service.KieuDeService;
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
public class KieuDeServiceImpl implements KieuDeService {

    @Autowired
    private KieuDeRepository kieuDeRepository;

    private Timestamp timestamp = new Timestamp(System.currentTimeMillis());
    @Override
    public List<KieuDe> getAll() {
        return kieuDeRepository.findAll();
    }

    @Override
    public List<KieuDe> getAllKieuDe(Integer trangThai, String tenKieuDe, Integer pageNumber, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<KieuDe> pageList = kieuDeRepository.getAllKieuDe(trangThai, tenKieuDe, pageable);
        return pageList.getContent();
    }


    @Override
    public KieuDe getById(UUID id) {
        return kieuDeRepository.findById(id).orElse(null);
    }

    @Override
    public MessageResponse create(KieuDeRequest request,String username) throws IOException, CsvValidationException {
        KieuDe kieuDe = new KieuDe();
        kieuDe.setId(UUID.randomUUID());
        kieuDe.setTenDe(request.getTenDe());
        kieuDe.setTrangThai(request.getTrangThai());
        kieuDe.setNgayTao(timestamp);
        kieuDeRepository.save(kieuDe);
        return MessageResponse.builder().message("Thêm thành công").build();
    }

    @Override
    public MessageResponse update(UUID id, KieuDeRequest request,String username) throws IOException, CsvValidationException {
        Optional<KieuDe> kieuDeOptional= kieuDeRepository.findById(id);
        if (kieuDeOptional.isPresent()) {
            KieuDe kieuDe = kieuDeOptional.get();
            kieuDe.setTenDe(request.getTenDe());
            kieuDe.setTrangThai(request.getTrangThai());
            kieuDe.setNgayCapNhat(timestamp);
            kieuDeRepository.save(kieuDe);

            return MessageResponse.builder().message("Cập nhật thành công").build();
        } else {
            return MessageResponse.builder().message("Không tìm thấy thương hiệu với ID: " + id).build();
        }
    }

    @Override
    public MessageResponse delete(UUID id) {
        Optional<KieuDe> kieuDeOptional= kieuDeRepository.findById(id);
        if (kieuDeOptional.isPresent()) {
            KieuDe kieuDe = kieuDeOptional.get();
            kieuDe.setTrangThai(2);
            kieuDe.setNgayCapNhat(timestamp);
            kieuDeRepository.save(kieuDe);
            return MessageResponse.builder().message("Delete thành công").build();
        } else {
            return MessageResponse.builder().message("Không tìm thấy thương hiệu với ID: " + id).build();
        }
    }


}
