package com.example.duantotnghiep.controller.don_hang_khach_hang;

import com.example.duantotnghiep.mapper.don_hang_khach_hang.DonHangKhachHangMap;
import com.example.duantotnghiep.mapper.don_hang_khach_hang.ThongTinDonHangKhachHangMap;
import com.example.duantotnghiep.mapper.don_hang_khach_hang.ThongTinSanPhamKhachHangMap;
import com.example.duantotnghiep.repository.don_hang_khach_hang_repo.DonHangKhachHangChiTietRepository;
import com.example.duantotnghiep.repository.don_hang_khach_hang_repo.DonHangKhachHangRepository;
import com.example.duantotnghiep.response.*;
import com.example.duantotnghiep.service.hoa_don_service.impl.HoaDonChiTietServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/don-hang-khach-hang-chi-tiet/")
public class DonHangChiTietKhachHangController {

    @Autowired
    private HoaDonChiTietServiceImpl hoaDonChiTietService;

    @Autowired
    private DonHangKhachHangChiTietRepository donHangKhachHangChiTietRepository;

    @GetMapping("hien-thi-don/{idHoaDon}")
    public ResponseEntity<ThongTinDonHangKhachHangMap> viewThongTinDonHang(@PathVariable(name = "idHoaDon") UUID idHoaDon, Principal principal) {
        return new ResponseEntity<>(donHangKhachHangChiTietRepository.getThongTinDonHang(idHoaDon,principal.getName()), HttpStatus.OK);
    }

    @GetMapping("hien-thi-san-pham/{idHoaDon}")
    public ResponseEntity<List<ThongTinSanPhamKhachHangMap>> getSanPhamHDCT(@PathVariable(name = "idHoaDon") UUID idHoaDon, Principal principal) {
        return new ResponseEntity<>(donHangKhachHangChiTietRepository.getSanPhamHDCT(idHoaDon, principal.getName()), HttpStatus.OK);
    }

    @GetMapping("hien-thi-trang-thai/{idHoaDon}")
    public ResponseEntity<List<TrangThaiHoaDonResponse>> getAllTrangThaiHoaDon(@PathVariable(name = "idHoaDon") UUID idHoaDon) {
        return new ResponseEntity<>(donHangKhachHangChiTietRepository.getAllTrangThaiHoaDon(idHoaDon), HttpStatus.OK);
    }

    @GetMapping("thanh-tien/{idHoaDon}")
    public ResponseEntity<MoneyResponse> getAllMoneyByHoaDon(@PathVariable(name = "idHoaDon") UUID idHoaDon) {
        return new ResponseEntity<>(donHangKhachHangChiTietRepository.getAllMoneyByHoaDon(idHoaDon), HttpStatus.OK);
    }

    @GetMapping("hien-thi-lich-su/{idHoaDon}")
    public ResponseEntity<List<HinhThucThanhToanResponse>> getLichSuThanhToan(@PathVariable(name = "idHoaDon") UUID idHoaDon) {
        return new ResponseEntity<>(hoaDonChiTietService.getLichSuThanhToan(idHoaDon), HttpStatus.OK);
    }

    @GetMapping("hien-thi-don-hang/{idHoaDon}")
    public ResponseEntity<ThongTinDonHang> viewThongTinDonHang(@PathVariable(name = "idHoaDon") UUID idHoaDon) {
        return new ResponseEntity<>(hoaDonChiTietService.getThongTinDonHang(idHoaDon), HttpStatus.OK);
    }
}
