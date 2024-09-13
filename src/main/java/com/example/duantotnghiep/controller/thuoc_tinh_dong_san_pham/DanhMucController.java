package com.example.duantotnghiep.controller.thuoc_tinh_dong_san_pham;

import com.example.duantotnghiep.entity.DanhMuc;
import com.example.duantotnghiep.entity.ThuongHieu;
import com.example.duantotnghiep.entity.TrangThaiHoaDon;
import com.example.duantotnghiep.request.DanhMucRequest;
import com.example.duantotnghiep.request.ThuongHieuRequest;
import com.example.duantotnghiep.response.MessageResponse;
import com.example.duantotnghiep.service.thuoc_tinh_dong_san_pham_service.impl.DanhMucServiceImpl;
import com.example.duantotnghiep.service.thuoc_tinh_dong_san_pham_service.impl.ThuongHieuServiceImpl;
import com.opencsv.exceptions.CsvValidationException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.Principal;
import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/danh-muc/")
public class DanhMucController {

    @Autowired
    private DanhMucServiceImpl danhMucService;

    @GetMapping("show")
    public ResponseEntity<List<DanhMuc>> getAllSanPhamGiamGia() {
        return new ResponseEntity<>(danhMucService.getAll(), HttpStatus.OK);
    }

    @GetMapping("hien-thi")
    public ResponseEntity<List<DanhMuc>> getAllDanhMuc(
            @RequestParam(name = "tenDanhMuc", required = false) String tenDanhMuc,
            @RequestParam(name = "trangThai", required = false) Integer trangThai,
            @RequestParam(name = "pageNumber", defaultValue = "0") Integer pageNumber,
            @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize
    ) {
        return new ResponseEntity<>(danhMucService.getAllDanhMuc(trangThai, tenDanhMuc, pageNumber, pageSize), HttpStatus.OK);
    }

    @GetMapping("detail")
    public DanhMuc getDanhMucById(@RequestParam UUID id) {
        return danhMucService.getById(id);
    }

    @PostMapping("create")
    public ResponseEntity<MessageResponse> createDanhMuc(@Valid @RequestBody DanhMucRequest danhMucRequest, Principal principal) throws IOException, CsvValidationException {
        return new ResponseEntity<>(danhMucService.create(danhMucRequest, principal.getName()), HttpStatus.CREATED);
    }

    @PutMapping("update")
    public ResponseEntity<MessageResponse> updateDanhMuc(@RequestParam UUID id,@Valid @RequestBody DanhMucRequest danhMucRequest,Principal principal) {
        try {
            MessageResponse response = danhMucService.update(id, danhMucRequest, principal.getName());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(MessageResponse.builder().message("Lỗi khi cập nhật").build(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PutMapping("delete")
    public ResponseEntity<MessageResponse> deleteDanhMuc(@RequestParam UUID id) {
        return new ResponseEntity<>(danhMucService.delete(id), HttpStatus.OK);
    }


}
