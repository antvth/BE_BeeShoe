package com.example.duantotnghiep.service.account_service.impl;

import com.example.duantotnghiep.repository.LoaiTaiKhoanRepository;
import com.example.duantotnghiep.response.LoaiTaiKhoanResponse;
import com.example.duantotnghiep.service.account_service.LoaiTaiKhoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class LoaiTaiKhoanServiceImpl implements LoaiTaiKhoanService {
    @Autowired
    private LoaiTaiKhoanRepository loaiTaiKhoanRepository;

    @Override
    public List<LoaiTaiKhoanResponse> findRoles() {
        List<Integer> trangThaiList = Arrays.asList(1, 2, 5);
        return loaiTaiKhoanRepository.findByRoles(trangThaiList);
    }

}
