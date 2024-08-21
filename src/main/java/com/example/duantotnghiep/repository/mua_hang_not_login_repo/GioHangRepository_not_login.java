package com.example.duantotnghiep.repository.mua_hang_not_login_repo;

import com.example.duantotnghiep.entity.GioHang;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface GioHangRepository_not_login extends JpaRepository<GioHang, UUID> {
        @Query(value = "SELECT gh.id FROM GIOHANG gh JOIN taikhoan tk on tk.id = gh.idtaikhoan\n" +
                "where tk.username = ? and gh.trangthai = 1", nativeQuery = true)
        UUID getGioHangLogin( String username);
}
