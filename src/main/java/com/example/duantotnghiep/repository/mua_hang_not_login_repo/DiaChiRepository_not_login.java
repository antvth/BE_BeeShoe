package com.example.duantotnghiep.repository.mua_hang_not_login_repo;

import com.example.duantotnghiep.entity.DiaChi;
import com.example.duantotnghiep.mapper.not_login.loadDiaChi_not_login;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface DiaChiRepository_not_login extends JpaRepository<DiaChi, UUID> {
    @Query(value = "SELECT TK.fullname,sodienthoai,DC.id,DC.diachi,DC.tinh,DC.huyen,DC.xa,DC.trangthai \n" +
            "FROM DIACHI DC JOIN TAIKHOAN TK ON DC.idtaikhoan = TK.id  where TK.username = ?",nativeQuery = true)
    List<loadDiaChi_not_login> loadDiaChi(String username);

    List<DiaChi> findByTrangThaiAndTaiKhoanUsername(int trangThai, String taiKhoanUsername);
}
