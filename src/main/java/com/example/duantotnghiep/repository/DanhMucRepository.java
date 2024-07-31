package com.example.duantotnghiep.repository;

import com.example.duantotnghiep.entity.DanhMuc;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface DanhMucRepository extends JpaRepository<DanhMuc, UUID> {

    List<DanhMuc> findByTrangThai(Integer trangThai);

    @Query("SELECT NEW com.example.duantotnghiep.entity.DanhMuc(th.id, th.tenDanhMuc, th.trangThai, th.ngayTao, th.ngayCapNhat)\n" +
            "FROM DanhMuc th\n" +
            "WHERE (:trangThai IS NULL OR th.trangThai = :trangThai) " +
            "AND (:tenDanhMuc IS NULL OR th.tenDanhMuc LIKE %:tenDanhMuc%) ORDER BY th.ngayTao DESC")
    Page<DanhMuc> getAllDanhMuc(@Param("trangThai") Integer trangThai, @Param("tenDanhMuc") String tenDanhMuc, Pageable pageable);

}
