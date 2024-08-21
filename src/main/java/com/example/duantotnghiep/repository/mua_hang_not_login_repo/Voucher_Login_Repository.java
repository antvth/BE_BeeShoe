package com.example.duantotnghiep.repository.mua_hang_not_login_repo;

import com.example.duantotnghiep.entity.Voucher;
import com.example.duantotnghiep.mapper.not_login.voucher_map_login;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface Voucher_Login_Repository extends JpaRepository<Voucher, UUID> {

    @Query(value = "SELECT id,mavoucher,tenvoucher,ngayketthuc,soluongma,soluongdung,giatritoithieudonhang,giatrigiam,hinhthucgiam FROM VOUCHER\n" +
            "where trangthai = 1 AND soluongma > soluongdung",nativeQuery = true)
    List<voucher_map_login> getVouchers();
}
