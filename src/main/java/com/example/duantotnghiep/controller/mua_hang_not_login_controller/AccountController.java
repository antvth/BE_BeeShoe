package com.example.duantotnghiep.controller.mua_hang_not_login_controller;

import com.example.duantotnghiep.entity.DiaChi;
import com.example.duantotnghiep.entity.TaiKhoan;
import com.example.duantotnghiep.mapper.not_login.DiaChiDto;
import com.example.duantotnghiep.mapper.not_login.TaiKhoanDto;

import com.example.duantotnghiep.mapper.not_login.loadDiaChi_not_login;
import com.example.duantotnghiep.repository.TaiKhoanRepository;
import com.example.duantotnghiep.repository.mua_hang_not_login_repo.DiaChiRepository_not_login;
import com.example.duantotnghiep.service.mua_hang_not_login_impl.TaiKhoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/account")
public class AccountController {

    private final TaiKhoanService taiKhoanService;

    @Autowired
    public AccountController(TaiKhoanService taiKhoanService) {
        this.taiKhoanService = taiKhoanService;
    }

    @Autowired
    DiaChiRepository_not_login diaChiRepository_not_login;

    @Autowired
    TaiKhoanRepository taiKhoanRepository;

    @GetMapping("/profile")
    public ResponseEntity<TaiKhoanDto> getProfile(Principal principal) {
        String username = principal.getName();

        TaiKhoanDto taiKhoanDto = taiKhoanService.getProfileByUsername(username);

        if (taiKhoanDto != null) {
            return new ResponseEntity<>(taiKhoanDto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/dia-chi")
    public ResponseEntity<List<loadDiaChi_not_login>> getDiaChi(Principal principal) {
        String username = principal.getName();

        return ResponseEntity.ok(taiKhoanService.loadDiaChi(username));
    }
    @PostMapping("/create-dia-chi")
    public ResponseEntity<String> createAddress(@RequestBody DiaChiDto diaChiRequest, Principal principal) {
        // Tìm tài khoản theo username của Principal
        Optional<TaiKhoan> taiKhoan = taiKhoanRepository.findByUsername(principal.getName());

        if (taiKhoan != null) {
            // Tạo mới địa chỉ
            DiaChi diaChi = new DiaChi();
            diaChi.setId(UUID.randomUUID());
            diaChi.setDiaChi(diaChiRequest.getDiaChi());
            diaChi.setTrangThai(2);
            diaChi.setTaiKhoan(taiKhoan.get());

            // Lưu địa chỉ vào cơ sở dữ liệu
            diaChiRepository_not_login.save(diaChi);

            return new ResponseEntity<>("Đã tạo mới địa chỉ thành công", HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>("Không tìm thấy tài khoản", HttpStatus.NOT_FOUND);
        }
    }
    //end add

    @PutMapping("/update-dia-chi/{diaChiId}")
    public ResponseEntity<String> updateAddress(
            @PathVariable UUID diaChiId,
            @RequestBody DiaChiDto diaChiRequest,
            Principal principal) {
        // Tìm địa chỉ theo ID
        Optional<DiaChi> optionalDiaChi = diaChiRepository_not_login.findById(diaChiId);

        if (optionalDiaChi.isPresent()) {
            DiaChi diaChi = optionalDiaChi.get();

            // Kiểm tra xem địa chỉ thuộc về tài khoản của Principal hay không
            if (diaChi.getTaiKhoan().getUsername().equals(principal.getName())) {
                // Cập nhật thông tin địa chỉ
                diaChi.setDiaChi(diaChiRequest.getDiaChi());
                // Lưu địa chỉ đã cập nhật vào cơ sở dữ liệu
                diaChiRepository_not_login.save(diaChi);

                return new ResponseEntity<>("Đã cập nhật địa chỉ thành công", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Địa chỉ không thuộc về tài khoản của bạn", HttpStatus.FORBIDDEN);
            }
        } else {
            return new ResponseEntity<>("Không tìm thấy địa chỉ", HttpStatus.NOT_FOUND);
        }
    }
    //end update

    @PutMapping("/set-default-dia-chi/{diaChiId}")
    public ResponseEntity<String> setDefaultAddress(@PathVariable UUID diaChiId, Principal principal) {
        // Tìm địa chỉ theo ID
        Optional<DiaChi> optionalDiaChi = diaChiRepository_not_login.findById(diaChiId);

        if (optionalDiaChi.isPresent()) {
            DiaChi diaChi = optionalDiaChi.get();

            // Kiểm tra xem địa chỉ thuộc về tài khoản của Principal hay không
            if (diaChi.getTaiKhoan().getUsername().equals(principal.getName())) {
                // Kiểm tra xem có địa chỉ mặc định nào khác không
                List<DiaChi> defaultAddresses = diaChiRepository_not_login.findByTrangThaiAndTaiKhoanUsername(1, principal.getName());

                if (!defaultAddresses.isEmpty()) {
                    // Nếu đã có địa chỉ mặc định, đặt trạng thái của nó về 2 (không còn là mặc định)
                    DiaChi oldDefaultAddress = defaultAddresses.get(0);
                    oldDefaultAddress.setTrangThai(2);
                    diaChiRepository_not_login.save(oldDefaultAddress);
                }

                // Đặt địa chỉ này làm mặc định (ví dụ: trạng thái = 1)
                diaChi.setTrangThai(1);

                // Lưu địa chỉ đã cập nhật vào cơ sở dữ liệu
                diaChiRepository_not_login.save(diaChi);

                return new ResponseEntity<>("Đã đặt địa chỉ làm mặc định thành công", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Địa chỉ không thuộc về tài khoản của bạn", HttpStatus.FORBIDDEN);
            }
        } else {
            return new ResponseEntity<>("Không tìm thấy địa chỉ", HttpStatus.NOT_FOUND);
        }
    }


    @PutMapping("/update-profile")
    public ResponseEntity<String> updateProfile(@RequestBody TaiKhoanDto updatedData, Principal principal) {
        String username = principal.getName();
        ResponseEntity<String> response = taiKhoanService.updateProfile(username, updatedData);
        return response;
    }
}

