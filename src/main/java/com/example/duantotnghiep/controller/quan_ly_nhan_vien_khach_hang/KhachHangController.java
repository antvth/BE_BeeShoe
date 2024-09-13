package com.example.duantotnghiep.controller.quan_ly_nhan_vien_khach_hang;

import com.example.duantotnghiep.entity.TaiKhoan;
import com.example.duantotnghiep.request.CreateQLKhachHangRequest;
import com.example.duantotnghiep.response.MessageResponse;
import com.example.duantotnghiep.response.QLKhachHangResponse;
import com.example.duantotnghiep.service.quan_ly_nhan_vien_khach_hang.impl.QLKhachHangImpl;
import com.opencsv.exceptions.CsvValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@CrossOrigin
@RestController
@RequestMapping("/api/ql-khach-hang")
public class KhachHangController {

    @Autowired
    QLKhachHangImpl service;

    @GetMapping("/hien-thi")
    public ResponseEntity<List<QLKhachHangResponse>> getAll(
            @RequestParam(name = "trangThai", required = false) Integer trangThai,
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "soDienThoai", required = false) String soDienThoai,
            @RequestParam(name = "maTaiKhoan", required = false) String maTaiKhoan,
            @RequestParam(name = "pageNumber", defaultValue = "0") Integer pageNumber,
            @RequestParam(name = "pageSize", defaultValue = "20") Integer pageSize
    ) {

        return new ResponseEntity<>(service.getQLKhachHang(trangThai, name, soDienThoai, maTaiKhoan, pageNumber, pageSize), HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<MessageResponse> createKhachHang(
            @RequestParam("file") MultipartFile file,
            @ModelAttribute CreateQLKhachHangRequest createQLKhachHangRequest, Principal principal) throws IOException, CsvValidationException {
        return new ResponseEntity<>(service.createKhachHang(file, createQLKhachHangRequest, true, principal.getName()), HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<MessageResponse> updateKhachHang(
            @RequestParam("file") MultipartFile file,
            @RequestParam("khachHangId") UUID khachHangId,
            @ModelAttribute CreateQLKhachHangRequest createQLKhachHangRequest, Principal principal) throws IOException, CsvValidationException {
        MessageResponse response = service.updateKhachHang(file, khachHangId, createQLKhachHangRequest, principal.getName());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/detail")
    public ResponseEntity<QLKhachHangResponse> search(@RequestParam(name = "id") UUID id) {
        return new ResponseEntity<>(service.detailKhachHang(id), HttpStatus.OK);
    }

    @GetMapping("/find-by-so-dien-thoai")
    public ResponseEntity<?> findKhachHangBySoDienThoai(@RequestParam("soDienThoai") String soDienThoai) {
        List<TaiKhoan> khachHangList = service.findKhachHangBySoDienThoai(soDienThoai);
        return new ResponseEntity<>(khachHangList.size(), HttpStatus.OK);
    }

    @GetMapping("/find-by-email")
    public ResponseEntity<?> findKhachHangByEmail(@RequestParam("email") String email) {
        List<TaiKhoan> khachHangList = service.findKhachHangByEmail(email);
        return new ResponseEntity<>(khachHangList.size(), HttpStatus.OK);
    }

}
