package com.example.duantotnghiep.repository;

import com.example.duantotnghiep.entity.DiaChi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface DiaChiRepository extends JpaRepository<DiaChi, UUID> {

    @Query("SELECT dc FROM DiaChi dc JOIN dc.taiKhoan tk WHERE dc.trangThai = 1 AND tk.id = :id")
    DiaChi findByDiaChi(@Param("id") UUID id);
}
