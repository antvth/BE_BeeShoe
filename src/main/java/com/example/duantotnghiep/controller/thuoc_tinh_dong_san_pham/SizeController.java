package com.example.duantotnghiep.controller.thuoc_tinh_dong_san_pham;

import com.example.duantotnghiep.entity.Size;
import com.example.duantotnghiep.entity.TaiKhoan;
import com.example.duantotnghiep.request.SizeRequest;
import com.example.duantotnghiep.response.MessageResponse;
import com.example.duantotnghiep.service.thuoc_tinh_dong_san_pham_service.impl.SizeServiceImpl;
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
@RequestMapping("/api/v1/size/")
public class SizeController {

    @Autowired
    private SizeServiceImpl sizeService;

    @GetMapping("show")
    public ResponseEntity<List<Size>> getAllSanPhamGiamGia() {
        return new ResponseEntity<>(sizeService.getAll(), HttpStatus.OK);
    }

    @GetMapping("hien-thi")
    public ResponseEntity<List<Size>> getAllSize(
            @RequestParam(name = "size", required = false) Integer size,
            @RequestParam(name = "trangThai", required = false) Integer trangThai,
            @RequestParam(name = "pageNumber", defaultValue = "0") Integer pageNumber,
            @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize
    ) {
        return new ResponseEntity<>(sizeService.getAllSize(trangThai, size, pageNumber, pageSize), HttpStatus.OK);
    }

    @GetMapping("detail")
    public Size getSizeById(@RequestParam UUID id) {
        return sizeService.getById(id);
    }

    @PostMapping("create")
    public ResponseEntity<MessageResponse> createSize(@Valid @RequestBody SizeRequest sizeRequest, Principal principal) throws IOException, CsvValidationException {
        return new ResponseEntity<>(sizeService.create(sizeRequest,principal.getName()), HttpStatus.CREATED);
    }

    @PutMapping("update")
    public ResponseEntity<MessageResponse> updateSize(@RequestParam UUID id,@Valid @RequestBody SizeRequest sizeRequest,Principal principal) {
        try {
            MessageResponse response = sizeService.update(id, sizeRequest,principal.getName());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(MessageResponse.builder().message("Lỗi khi cập nhật").build(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("delete")
    public ResponseEntity<MessageResponse> deleteSize(@RequestParam UUID id) {
        return new ResponseEntity<>(sizeService.delete(id), HttpStatus.OK);
    }

    @GetMapping("/find-by-size")
    public ResponseEntity<?> findKhachHangByEmail(@RequestParam("size") Integer size) {
        List<Size> thuocTinhList = sizeService.findSize(size);
        return new ResponseEntity<>(thuocTinhList.size(), HttpStatus.OK);
    }
}
