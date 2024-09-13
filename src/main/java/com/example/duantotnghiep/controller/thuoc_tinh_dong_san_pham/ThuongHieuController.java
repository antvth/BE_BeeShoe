package com.example.duantotnghiep.controller.thuoc_tinh_dong_san_pham;

import com.example.duantotnghiep.entity.ThuongHieu;
import com.example.duantotnghiep.request.ThuongHieuRequest;
import com.example.duantotnghiep.response.MessageResponse;
import com.example.duantotnghiep.service.thuoc_tinh_dong_san_pham_service.impl.ThuongHieuServiceImpl;
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
@RequestMapping("/api/v1/thuong-hieu/")
public class ThuongHieuController {

    @Autowired
    private ThuongHieuServiceImpl thuongHieuService;

    @GetMapping("hien-thi")
    public ResponseEntity<List<ThuongHieu>> getAllThuongHieu(
            @RequestParam(name = "tenThuongHieu", required = false) String tenThuongHieu,
            @RequestParam(name = "trangThai", required = false) Integer trangThai,
            @RequestParam(name = "pageNumber", defaultValue = "0") Integer pageNumber,
            @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize
    ) {
        return new ResponseEntity<>(thuongHieuService.getAllThuongHieu(trangThai, tenThuongHieu, pageNumber, pageSize), HttpStatus.OK);
    }

    @GetMapping("detail")
    public ThuongHieu getThuongHieuById(@RequestParam UUID id) {
        return thuongHieuService.getById(id);
    }

    @PostMapping("create")
    public ResponseEntity<MessageResponse> createThuongHieu(
            @Valid @RequestBody ThuongHieuRequest thuongHieuRequest,
                Principal principal) throws IOException, CsvValidationException {
        return new ResponseEntity<>(thuongHieuService.create(thuongHieuRequest, principal.getName()), HttpStatus.CREATED);
    }

    @PutMapping("update")
    public ResponseEntity<MessageResponse> updateThuongHieu(@RequestParam UUID id,@Valid @RequestBody ThuongHieuRequest thuongHieuRequest,Principal principal) {
        try {
            MessageResponse response = thuongHieuService.update(id, thuongHieuRequest, principal.getName());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(MessageResponse.builder().message("Lỗi khi cập nhật").build(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PutMapping("delete")
    public ResponseEntity<MessageResponse> deleteThuongHieu(@RequestParam UUID id) {
        return new ResponseEntity<>(thuongHieuService.delete(id), HttpStatus.OK);
    }
}
