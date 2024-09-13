package com.example.duantotnghiep.controller.thuoc_tinh_dong_san_pham;

import com.example.duantotnghiep.entity.KieuDe;
import com.example.duantotnghiep.entity.MauSac;
import com.example.duantotnghiep.request.KieuDeRequest;
import com.example.duantotnghiep.request.MauSacRequest;
import com.example.duantotnghiep.response.MessageResponse;
import com.example.duantotnghiep.service.thuoc_tinh_dong_san_pham_service.impl.KieuDeServiceImpl;
import com.opencsv.exceptions.CsvValidationException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/kieu-de/")
public class KieuDeController {

    @Autowired
    private KieuDeServiceImpl kieuDeService;

    @GetMapping("show")
    public ResponseEntity<List<KieuDe>> getAllSanPhamGiamGia() {
        return new ResponseEntity<>(kieuDeService.getAll(), HttpStatus.OK);
    }

    @GetMapping("hien-thi")
    public ResponseEntity<List<KieuDe>> getAllKieuDe(
            @RequestParam(name = "tenKieuDe", required = false) String tenKieuDe,
            @RequestParam(name = "trangThai", required = false) Integer trangThai,
            @RequestParam(name = "pageNumber", defaultValue = "0") Integer pageNumber,
            @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize
    ) {
        return new ResponseEntity<>(kieuDeService.getAllKieuDe(trangThai, tenKieuDe, pageNumber, pageSize), HttpStatus.OK);
    }

    @GetMapping("detail")
    public KieuDe getKieuDeById(@RequestParam UUID id) {
        return kieuDeService.getById(id);
    }

    @PostMapping("create")
    public ResponseEntity<MessageResponse> createKieuDe(@Valid @RequestBody KieuDeRequest kieuDeRequest, Principal principal) throws IOException, CsvValidationException {
        return new ResponseEntity<>(kieuDeService.create(kieuDeRequest, principal.getName()), HttpStatus.CREATED);
    }

    @PutMapping("update")
    public ResponseEntity<MessageResponse> updateKieuDe(@RequestParam UUID id,@Valid @RequestBody KieuDeRequest kieuDeRequest,Principal principal) {
        try {
            MessageResponse response = kieuDeService.update(id, kieuDeRequest,principal.getName());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(MessageResponse.builder().message("Lỗi khi cập nhật").build(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("delete")
    public ResponseEntity<MessageResponse> deleteKieuDe(@RequestParam UUID id) {
        return new ResponseEntity<>(kieuDeService.delete(id), HttpStatus.OK);
    }
}
