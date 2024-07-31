package com.example.duantotnghiep.repository;

import com.example.duantotnghiep.entity.KieuDe;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface KieuDeRepository extends JpaRepository<KieuDe, UUID> {

    List<KieuDe> findByTrangThai(Integer trangThai);

    @Query("SELECT NEW com.example.duantotnghiep.entity.KieuDe(th.id, th.tenDe, th.trangThai, th.ngayTao, th.ngayCapNhat)\n" +
            "FROM KieuDe th\n" +
            "WHERE (:trangThai IS NULL OR th.trangThai = :trangThai) " +
            "AND (:tenKieuDe IS NULL OR th.tenDe LIKE %:tenKieuDe%) ORDER BY th.ngayTao DESC")
    Page<KieuDe> getAllKieuDe(@Param("trangThai") Integer trangThai, @Param("tenKieuDe") String tenKieuDe, Pageable pageable);
}

