package com.example.duantotnghiep.controller.don_hang_khach_hang;

import com.example.duantotnghiep.mapper.don_hang_khach_hang.DonHangKhachHangMap;
import com.example.duantotnghiep.repository.KhachHangRepository;
import com.example.duantotnghiep.repository.don_hang_khach_hang_repo.DonHangKhachHangRepository;
import com.example.duantotnghiep.repository.mua_hang_not_login_repo.KhachHangRepository_not_login;
import com.example.duantotnghiep.service.account_service.TaiKhoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/don-hang-khach-hang/")
public class DonHangKhachHangController {

    @Autowired
    private DonHangKhachHangRepository donHangKhachHangRepository;

    @GetMapping("filter")
    public ResponseEntity<List<DonHangKhachHangMap>> filter(Principal principal,@RequestParam Integer trangthai) {
        String username = principal.getName();
        return ResponseEntity.ok(donHangKhachHangRepository.filterStatus(username,trangthai));
    }

    @GetMapping("search")
    public ResponseEntity<List<DonHangKhachHangMap>> search(Principal principal, @RequestParam String tensanpham, @RequestParam String mahoadon, @RequestParam String trangthai) {
        String username = principal.getName();
        return ResponseEntity.ok(donHangKhachHangRepository.searchByMaOrName(username,trangthai,tensanpham,mahoadon));
    }

    @GetMapping("load-tra-hang")
    public ResponseEntity<List<DonHangKhachHangMap>> loadTraHang(Principal principal) {
        String username = principal.getName();
        return ResponseEntity.ok(donHangKhachHangRepository.loadTraHang(username));
    }

    @GetMapping("search-tra-hang")
    public ResponseEntity<List<DonHangKhachHangMap>> search(Principal principal, @RequestParam String tensanpham, @RequestParam String mahoadon) {
        String username = principal.getName();
        return ResponseEntity.ok(donHangKhachHangRepository.searchByMaOrNameTraHang(username,tensanpham,mahoadon));
    }

    @GetMapping("load-don-huy")
    public ResponseEntity<List<DonHangKhachHangMap>> loadDonHuy(Principal principal) {
        String username = principal.getName();
        return ResponseEntity.ok(donHangKhachHangRepository.loadDonHuy(username));
    }

    @GetMapping("search-don-huy")
    public ResponseEntity<List<DonHangKhachHangMap>> searchDonHuy(Principal principal, @RequestParam String tensanpham, @RequestParam String mahoadon) {
        String username = principal.getName();
        return ResponseEntity.ok(donHangKhachHangRepository.searchByMaOrNameDonHuy(username,tensanpham,mahoadon));
    }
}
