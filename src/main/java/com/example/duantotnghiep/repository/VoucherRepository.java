package com.example.duantotnghiep.repository;

import com.example.duantotnghiep.entity.Voucher;
import com.example.duantotnghiep.response.LoadVoucherCounterResponse;
import com.example.duantotnghiep.response.VoucherCounterResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VoucherRepository extends JpaRepository<Voucher, UUID> {

        Optional<Voucher> findByIdAndTrangThai(UUID id, Integer trangThai);

        List<Voucher> findByTenVoucherContainingIgnoreCaseOrMaVoucherContainingIgnoreCase(String tenVoucher,
                        String maVoucher);

        @Query("SELECT v FROM Voucher v " +
                        "WHERE (:maVoucher is null or v.maVoucher = :maVoucher) " +
                        "AND (:tenVoucher is null or v.tenVoucher LIKE :tenVoucher%) " +
                        "AND (:trangThai is null or v.trangThai = :trangThai) " +
                        "ORDER BY  v.ngayTao DESC")
        Page<Voucher> listVoucher(@Param("maVoucher") String maVoucher, @Param("tenVoucher") String tenVoucher,
                        @Param("trangThai") Integer trangThai, Pageable pageable);

        @Query("SELECT new com.example.duantotnghiep.response.VoucherCounterResponse" +
                        "(v.id, v.maVoucher, v.tenVoucher, v.hinhThucGiam, v.soLuongMa, v.soLuongDung,v.giaTriGiam, v.giaTriToiThieuDonhang, v.ngayBatDau, v.ngayKetThuc) FROM Voucher v WHERE v.trangThai = 1")
        Page<VoucherCounterResponse> findAllVoucher(Pageable pageable);

        @Query("SELECT new com.example.duantotnghiep.response.VoucherCounterResponse" +
                "(v.id, v.maVoucher, v.tenVoucher, v.hinhThucGiam, v.soLuongMa, v.soLuongDung,v.giaTriGiam, v.giaTriToiThieuDonhang, v.ngayBatDau, v.ngayKetThuc) " +
                "FROM Voucher v WHERE v.trangThai = 1 AND v.maVoucher like %:key% OR v.tenVoucher like %:key%")
        Page<VoucherCounterResponse> searchVoucher(Pageable pageable,@Param("key") String key);

        @Query("SELECT v FROM Voucher v WHERE v.trangThai = 1")
        List<Voucher> getAllVoucher();

        List<Voucher> findByTrangThai(Integer trangThai);
}
