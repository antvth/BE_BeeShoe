package com.example.duantotnghiep.repository;

import com.example.duantotnghiep.entity.TaiKhoan;
import com.example.duantotnghiep.response.NhanVienDTOReponse;
import com.example.duantotnghiep.response.NhanVienResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TaiKhoanRepository extends JpaRepository<TaiKhoan, UUID> {

    @Query("select new com.example.duantotnghiep.response.NhanVienResponse(" +
            "tk.image,tk.username,tk.email,tk.name,tk.trangThai, tk.loaiTaiKhoan.name)" +
            " from TaiKhoan tk JOIN tk.loaiTaiKhoan vt where vt.name = 'EMPLOYEE'")
    Page<NhanVienResponse> getAllPage(Pageable pageable);

    Optional<TaiKhoan> findByUsername(String username);

    Optional<TaiKhoan> findByEmail(String email);

    @Query("SELECT tk FROM TaiKhoan tk WHERE tk.id =: id")
    Optional<TaiKhoan> findByKhachHang(UUID id);

    @Query("select new com.example.duantotnghiep.response.NhanVienResponse(" +
            "tk.image,tk.username,tk.email,tk.name,tk.trangThai, vt.name)" +
            " from TaiKhoan tk JOIN tk.loaiTaiKhoan vt where tk.username = :name")
    NhanVienResponse getList(@Param("name") String name);

    @Query("SELECT NEW com.example.duantotnghiep.response.NhanVienDTOReponse(tk.id, tk.maTaiKhoan, tk.name, tk.soDienThoai, tk.gioiTinh, tk.ngaySinh, tk.trangThai, tk.email, tk.image, dc.diaChi)\n" +
            "FROM TaiKhoan tk\n" +
            "JOIN tk.loaiTaiKhoan ltk JOIN tk.diaChiList dc\n" +
            "WHERE (:trangThai IS NULL OR tk.trangThai = :trangThai)  AND ltk.trangThai IN (:trangThaiList) " +
            "AND (:maNhanVien IS NULL OR tk.maTaiKhoan LIKE %:maNhanVien%) AND (:name IS NULL OR tk.name LIKE %:name%) AND (:soDienThoai IS NULL OR tk.soDienThoai LIKE %:soDienThoai%) ORDER BY tk.ngayTao DESC")
    Page<NhanVienDTOReponse> getAllNhanVien(@Param("trangThaiList") List<Integer> trangThaiList, @Param("maNhanVien") String maNhanVien, @Param("name") String name, @Param("soDienThoai") String soDienThoai, @Param("trangThai") Integer trangThai, Pageable pageable);

    @Query("SELECT NEW com.example.duantotnghiep.response.NhanVienDTOReponse(tk.id, tk.maTaiKhoan, tk.name, tk.soDienThoai, tk.gioiTinh, tk.ngaySinh, tk.trangThai, tk.email, tk.image, dc.diaChi)\n" +
            "FROM TaiKhoan tk " +
            "JOIN tk.loaiTaiKhoan ltk JOIN tk.diaChiList dc " +
            "WHERE tk.id = :id AND dc.trangThai = 1")
    NhanVienDTOReponse getNhanVienById(@Param("id") UUID id);

}
