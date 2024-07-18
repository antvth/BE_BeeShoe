package com.example.duantotnghiep.repository;

import com.example.duantotnghiep.entity.LoaiTaiKhoan;
import com.example.duantotnghiep.enums.TypeAccountEnum;
import com.example.duantotnghiep.response.LoaiTaiKhoanResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface LoaiTaiKhoanRepository extends JpaRepository<LoaiTaiKhoan, UUID> {

    Optional<LoaiTaiKhoan> findByName(TypeAccountEnum name);


    @Query("SELECT new com.example.duantotnghiep.response.LoaiTaiKhoanResponse(lt.id, lt.name) FROM LoaiTaiKhoan lt WHERE lt.trangThai IN (:trangThaiList)")
    List<LoaiTaiKhoanResponse> findByRoles(@Param("trangThaiList") List<Integer> trangThaiList);

}
