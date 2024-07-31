package com.example.duantotnghiep.service.thuoc_tinh_dong_san_pham_service.impl;

import com.example.duantotnghiep.entity.MauSac;
import com.example.duantotnghiep.repository.MauSacRepository;
import com.example.duantotnghiep.request.MauSacRequest;
import com.example.duantotnghiep.response.MessageResponse;
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
public class MauSacServiceImpl implements MauSacService {

    @Autowired
    private MauSacRepository mauSacRepository;



    private Timestamp timestamp = new Timestamp(System.currentTimeMillis());

    @Override
    public List<MauSac> getAll() {
        return mauSacRepository.findByTrangThai(1);
    }

    @Override
    public List<MauSac> getAllMauSac(Integer trangThai, String tenMauSac, Integer pageNumber, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<MauSac> pageList = mauSacRepository.getAllMauSac(trangThai, tenMauSac, pageable);
        return pageList.getContent();
    }


    @Override
    public MauSac getById(UUID id) {
        return mauSacRepository.findById(id).orElse(null);
    }

    @Override
    public MessageResponse create(MauSacRequest request,String username) throws IOException, CsvValidationException {
        MauSac mauSac = new MauSac();
        mauSac.setId(UUID.randomUUID());
        mauSac.setTenMauSac(request.getTenMauSac());
        mauSac.setTrangThai(request.getTrangThai());
        mauSac.setNgayTao(timestamp);
        mauSacRepository.save(mauSac);
        return MessageResponse.builder().message("Thêm thành công").build();
    }

    @Override
    public MessageResponse update(UUID id, MauSacRequest request,String username) throws IOException, CsvValidationException {
        Optional<MauSac> optionalMauSac= mauSacRepository.findById(id);
        if (optionalMauSac.isPresent()) {
            MauSac mauSac = optionalMauSac.get();
            mauSac.setTenMauSac(request.getTenMauSac());
            mauSac.setTrangThai(request.getTrangThai());
            mauSac.setNgayCapNhat(timestamp);
            mauSacRepository.save(mauSac);
            return MessageResponse.builder().message("Cập nhật thành công").build();
        } else {
            return MessageResponse.builder().message("Không tìm thấy thương hiệu với ID: " + id).build();
        }
    }

    @Override
    public MessageResponse delete(UUID id) {
        Optional<MauSac> optionalMauSac= mauSacRepository.findById(id);
        if (optionalMauSac.isPresent()) {
            MauSac mauSac = optionalMauSac.get();
            mauSac.setTrangThai(2);
            mauSac.setNgayCapNhat(timestamp);
            mauSacRepository.save(mauSac);
            return MessageResponse.builder().message("Delete thành công").build();
        } else {
            return MessageResponse.builder().message("Không tìm thấy thương hiệu với ID: " + id).build();
        }
    }

    public List<MauSac> findMauSac(String mausac) {
        return mauSacRepository.findAllByTenMauSac(mausac);
    }
}
