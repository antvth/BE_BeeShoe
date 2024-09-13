package com.example.duantotnghiep.controller.quan_ly_nhan_vien_khach_hang;

import com.example.duantotnghiep.request.CreateQLKhachHangRequest;
import com.example.duantotnghiep.request.NhanVienDTORequest;
import com.example.duantotnghiep.response.LoaiTaiKhoanResponse;
import com.example.duantotnghiep.response.MessageResponse;
import com.example.duantotnghiep.response.NhanVienDTOReponse;
import com.example.duantotnghiep.service.account_service.impl.LoaiTaiKhoanServiceImpl;
import com.example.duantotnghiep.service.account_service.impl.NhanVienServiceImpl;
import com.opencsv.exceptions.CsvValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/nhan-vien/")
public class NhanVienController {

    @Autowired
    private NhanVienServiceImpl nhanVienService;

    @Autowired
    private LoaiTaiKhoanServiceImpl loaiTaiKhoanService;

    @GetMapping("hien-thi")
    public ResponseEntity<List<NhanVienDTOReponse>> getAllSanPhamGiamGia(
            @RequestParam(name = "maNhanVien", required = false) String maNhanVien,
            @RequestParam(name = "trangThai", required = false) Integer trangThai,
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "soDienThoai", required = false) String soDienTHoai,
            @RequestParam(name = "pageNumber", defaultValue = "0") Integer pageNumber,
            @RequestParam(name = "pageSize", defaultValue = "20") Integer pageSize
    ) {
        return new ResponseEntity<>(nhanVienService.getAllNhanVien(maNhanVien, name, soDienTHoai, trangThai, pageNumber, pageSize), HttpStatus.OK);
    }

    @GetMapping("detail")
    public NhanVienDTOReponse getXuatXuById(@RequestParam UUID id) {
        return nhanVienService.getNhanVienById(id);
    }

    @GetMapping("hien-thi-roles")
    public ResponseEntity<List<LoaiTaiKhoanResponse>> getRoles() {
        return new ResponseEntity<>(loaiTaiKhoanService.findRoles(), HttpStatus.OK);
    }

    @PostMapping("create")
    public ResponseEntity<MessageResponse> createNhanVien(
            @RequestParam("file") MultipartFile file,
            @ModelAttribute NhanVienDTORequest createQLKhachHangRequest, Principal principal) throws IOException, CsvValidationException {
        return new ResponseEntity<>(nhanVienService.create(file, createQLKhachHangRequest, true,principal.getName()), HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<MessageResponse> updateKhachHang(
            @RequestParam("file") MultipartFile file,
            @RequestParam("idNhanVien") UUID khachHangId,
            @ModelAttribute NhanVienDTORequest request,Principal principal) throws IOException, CsvValidationException {
        MessageResponse response = nhanVienService.update(file, khachHangId, request,principal.getName());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("delete")
    public ResponseEntity<MessageResponse> deleteThuongHieu(@RequestParam("idNhanVien") UUID id) {
        return new ResponseEntity<>(nhanVienService.delete(id), HttpStatus.OK);
    }
}
