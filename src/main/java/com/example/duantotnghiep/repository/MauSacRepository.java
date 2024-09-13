package com.example.duantotnghiep.repository;

import com.example.duantotnghiep.entity.ChatLieu;
import com.example.duantotnghiep.entity.MauSac;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface MauSacRepository extends JpaRepository<MauSac, UUID> {

    List<MauSac> findByTrangThai(Integer trangThai);

    @Query("SELECT NEW com.example.duantotnghiep.entity.MauSac(th.id, th.tenMauSac, th.trangThai, th.ngayTao, th.ngayCapNhat)\n" +
            "FROM MauSac th\n" +
            "WHERE (:trangThai IS NULL OR th.trangThai = :trangThai) " +
            "AND (:tenMauSac IS NULL OR th.tenMauSac LIKE %:tenMauSac%) ORDER BY th.ngayTao DESC")
    Page<MauSac> getAllMauSac(@Param("trangThai") Integer trangThai, @Param("tenMauSac") String tenMauSac, Pageable pageable);

    List<MauSac> findAllByTenMauSac(String mausac);

}
