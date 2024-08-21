package com.example.duantotnghiep.repository;

import com.example.duantotnghiep.entity.XuatXu;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface XuatSuRepository extends JpaRepository<XuatXu, UUID> {

    List<XuatXu> findByTrangThai(Integer trangThai);

    List<XuatXu> findByTenXuatXu(String name);

    @Query("SELECT NEW com.example.duantotnghiep.entity.XuatXu(th.id, th.tenXuatXu, th.trangThai, th.ngayTao, th.ngayCapNhat)\n" +
            "FROM XuatXu th\n" +
            "WHERE (:trangThai IS NULL OR th.trangThai = :trangThai) " +
            "AND (:tenXuatXu IS NULL OR th.tenXuatXu LIKE %:tenXuatXu%) ORDER BY th.ngayTao DESC")
    Page<XuatXu> getAllXuatXu(@Param("trangThai") Integer trangThai, @Param("tenXuatXu") String tenXuatXu, Pageable pageable);
}
