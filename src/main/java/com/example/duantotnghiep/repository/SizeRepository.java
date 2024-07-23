package com.example.duantotnghiep.repository;

import com.example.duantotnghiep.entity.Size;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface SizeRepository extends JpaRepository<Size, UUID> {

    List<Size> findByTrangThai(Integer trangThai);

    @Query("SELECT NEW com.example.duantotnghiep.entity.Size(th.id, th.size, th.trangThai, th.ngayTao, th.ngayCapNhat)\n" +
            "FROM Size th\n" +
            "WHERE (:trangThai IS NULL OR th.trangThai = :trangThai) " +
            "AND (:size IS NULL OR th.size = :size) ORDER BY th.ngayTao DESC")
    Page<Size> getAllSize(@Param("trangThai") Integer trangThai, @Param("size") Integer size, Pageable pageable);

    List<Size> findAllBySize(Integer size);
}
