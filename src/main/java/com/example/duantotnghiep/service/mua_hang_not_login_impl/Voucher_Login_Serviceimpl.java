package com.example.duantotnghiep.service.mua_hang_not_login_impl;


import com.example.duantotnghiep.mapper.not_login.voucher_map_login;
import com.example.duantotnghiep.repository.mua_hang_not_login_repo.Voucher_Login_Repository;
import com.example.duantotnghiep.service.mua_hang_not_login_service.Voucher_Login_Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class Voucher_Login_Serviceimpl implements Voucher_Login_Service {
    @Autowired
    private Voucher_Login_Repository Repository;


    @Override
    public List<voucher_map_login> getVouchers() {
        return Repository.getVouchers();
    }
}
