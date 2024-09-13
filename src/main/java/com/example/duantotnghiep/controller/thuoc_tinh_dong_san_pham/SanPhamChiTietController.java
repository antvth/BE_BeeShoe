package com.example.duantotnghiep.controller.thuoc_tinh_dong_san_pham;

import com.example.duantotnghiep.request.ProductDetailRequest;
import com.example.duantotnghiep.response.MessageResponse;
import com.example.duantotnghiep.response.SanPhamChiTietResponse;
import com.example.duantotnghiep.service.thuoc_tinh_dong_san_pham_service.impl.SanPhamChiTietServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/san-pham-chi-tiet/")
public class SanPhamChiTietController {

    @Autowired
    private SanPhamChiTietServiceImpl sanPhamChiTietService;

    @GetMapping("show")
    public ResponseEntity<List<SanPhamChiTietResponse>> viewHoaDonTaiQuay(
            @RequestParam(name = "id") UUID id,
            @RequestParam(name = "pageNumber", defaultValue = "0") Integer pageNumber,
            @RequestParam(name = "pageSize", defaultValue = "20") Integer pageSize) {
        if (pageNumber <= 0) {
            pageNumber = 0;
        }
        return new ResponseEntity<>(sanPhamChiTietService.getAll(id, pageNumber, pageSize), HttpStatus.OK);
    }

    @GetMapping("show-by-status")
    public ResponseEntity<List<SanPhamChiTietResponse>> finByTrangThai(
            @RequestParam(name = "id") UUID id,
            @RequestParam(name = "trangThai") Integer trangThai,
            @RequestParam(name = "pageNumber", defaultValue = "0") Integer pageNumber,
            @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize) {
        if (pageNumber <= 0) {
            pageNumber = 0;
        }
        return new ResponseEntity<>(sanPhamChiTietService.finByTrangThai(id, trangThai, pageNumber, pageSize), HttpStatus.OK);
    }

    @GetMapping("show-by-key")
    public ResponseEntity<List<SanPhamChiTietResponse>> finByKey(
            @RequestParam(name = "id") UUID id,
            @RequestParam(name = "key") String key,
            @RequestParam(name = "pageNumber", defaultValue = "0") Integer pageNumber,
            @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize) {
        if (pageNumber <= 0) {
            pageNumber = 0;
        }
        return new ResponseEntity<>(sanPhamChiTietService.finByKey(id, key, pageNumber, pageSize), HttpStatus.OK);
    }

    @GetMapping("show-by-size")
    public ResponseEntity<List<SanPhamChiTietResponse>> finBySize(
            @RequestParam(name = "id") UUID id,
            @RequestParam(name = "size") Integer size,
            @RequestParam(name = "pageNumber", defaultValue = "0") Integer pageNumber,
            @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize) {
        if (pageNumber <= 0) {
            pageNumber = 0;
        }
        return new ResponseEntity<>(sanPhamChiTietService.finBySize(id, size, pageNumber, pageSize), HttpStatus.OK);
    }

    @PostMapping("create/{idProduct}")
    public ResponseEntity<MessageResponse> createSanPham(
            @PathVariable(name = "idProduct") UUID idProduct,
            @Valid @RequestBody ProductDetailRequest productDetailRequest
    ) {
        return new ResponseEntity<>(sanPhamChiTietService.createProductDetail(idProduct, productDetailRequest), HttpStatus.CREATED);
    }

    @PutMapping("update-huy")
    public ResponseEntity<MessageResponse> updateStatusHuy(
            @RequestParam(name = "id") UUID id
    ) {
        return new ResponseEntity<>(sanPhamChiTietService.updateStatusHuy(id), HttpStatus.OK);
    }

    @PutMapping("update-kich")
    public ResponseEntity<MessageResponse> updateStatusKichHoat(
            @RequestParam(name = "id") UUID id
    ) {
        return new ResponseEntity<>(sanPhamChiTietService.updateStatusKichHoat(id), HttpStatus.OK);
    }

    @PutMapping("update-quantity")
    public ResponseEntity<MessageResponse> updateQuantity(
            @RequestParam(name = "id") UUID id,
            @RequestParam(name = "soLuong") Integer soLuong
    ) {
        return new ResponseEntity<>(sanPhamChiTietService.updateQuantity(id, soLuong), HttpStatus.OK);
    }
}
