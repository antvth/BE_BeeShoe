package com.example.duantotnghiep.controller.ban_tai_quay_controller;

import com.example.duantotnghiep.mapper.ChiTietSanPhamCustom;
import com.example.duantotnghiep.response.MessageResponse;
import com.example.duantotnghiep.service.ban_tai_quay_service.impl.ProductCounterServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@CrossOrigin
@RestController
@RequestMapping("/api/chi-tiet-sp/")
public class ProductCounterController {

    @Autowired
    private ProductCounterServiceImpl chiTietSanPhamService;

    @GetMapping("hien-thi")
    public ResponseEntity<List<ChiTietSanPhamCustom>> getAll(
            @RequestParam(name = "pageNumber", defaultValue = "0") Integer pageNumber,
            @RequestParam(name = "pageSize", defaultValue = "20") Integer pageSize) {
        return ResponseEntity.ok(chiTietSanPhamService.getAll(pageNumber, pageSize));
    }

    @GetMapping("search-name")
    public ResponseEntity<List<ChiTietSanPhamCustom>> searchByName(
            @RequestParam(name = "pageNumber", defaultValue = "0") Integer pageNumber,
            @RequestParam(name = "pageSize", defaultValue = "20") Integer pageSize,
            @RequestParam String name) {
        return ResponseEntity.ok(chiTietSanPhamService.searchByName(pageNumber, pageSize, name));
    }

    @GetMapping("filter-brand")
    public ResponseEntity<List<ChiTietSanPhamCustom>> filterBrand(
            @RequestParam(name = "pageNumber", defaultValue = "0") Integer pageNumber,
            @RequestParam(name = "pageSize", defaultValue = "20") Integer pageSize,
            @RequestParam(name = "tenThuongHieu", required = false) String tenThuongHieu,
            @RequestParam(name = "tenXuatXu", required = false) String tenXuatXu,
            @RequestParam(name = "tenDanhMuc", required = false) String tenDanhMuc,
            @RequestParam(name = "tenDe", required = false) String tenDe,
            @RequestParam(name = "tenChatLieu", required = false) String tenChatLieu,
            @RequestParam(name = "tenMauSac", required = false) String tenMauSac,
            @RequestParam(name = "size", required = false) Integer size
    ) {
        return ResponseEntity.ok(chiTietSanPhamService.filterBrand(pageNumber, pageSize, tenThuongHieu, tenXuatXu, tenDanhMuc, tenDe, tenChatLieu, tenMauSac, size));
    }

    @GetMapping("filter-category")
    public ResponseEntity<List<ChiTietSanPhamCustom>> filterCategory(
            @RequestParam(name = "pageNumber", defaultValue = "0") Integer pageNumber,
            @RequestParam(name = "pageSize", defaultValue = "20") Integer pageSize,
            @RequestParam String name) {
        return ResponseEntity.ok(chiTietSanPhamService.filterCategory(pageNumber, pageSize, name));
    }

    @GetMapping("filter-sole")
    public ResponseEntity<List<ChiTietSanPhamCustom>> filterSole(
            @RequestParam(name = "pageNumber", defaultValue = "0") Integer pageNumber,
            @RequestParam(name = "pageSize", defaultValue = "20") Integer pageSize,
            @RequestParam String name) {
        return ResponseEntity.ok(chiTietSanPhamService.filterSole(pageNumber, pageSize, name));
    }

    @GetMapping("filter-origin")
    public ResponseEntity<List<ChiTietSanPhamCustom>> filterOrigin(
            @RequestParam(name = "pageNumber", defaultValue = "0") Integer pageNumber,
            @RequestParam(name = "pageSize", defaultValue = "20") Integer pageSize,
            @RequestParam String name) {
        return ResponseEntity.ok(chiTietSanPhamService.filterOrigin(pageNumber, pageSize, name));
    }

    @GetMapping("filter-size")
    public ResponseEntity<List<ChiTietSanPhamCustom>> filterSize(
            @RequestParam(name = "pageNumber", defaultValue = "0") Integer pageNumber,
            @RequestParam(name = "pageSize", defaultValue = "20") Integer pageSize,
            @RequestParam Integer size) {
        return ResponseEntity.ok(chiTietSanPhamService.filterSize(pageNumber, pageSize, size));
    }

    @GetMapping("filter-material")
    public ResponseEntity<List<ChiTietSanPhamCustom>> filterMaterial(
            @RequestParam(name = "pageNumber", defaultValue = "0") Integer pageNumber,
            @RequestParam(name = "pageSize", defaultValue = "20") Integer pageSize,
            @RequestParam String name) {
        return ResponseEntity.ok(chiTietSanPhamService.filterMaterial(pageNumber, pageSize, name));
    }

    @GetMapping("filter-color")
    public ResponseEntity<List<ChiTietSanPhamCustom>> filterColor(
            @RequestParam(name = "pageNumber", defaultValue = "0") Integer pageNumber,
            @RequestParam(name = "pageSize", defaultValue = "20") Integer pageSize,
            @RequestParam String name) {
        return ResponseEntity.ok(chiTietSanPhamService.filterColor(pageNumber, pageSize, name));
    }

    @GetMapping("tien-cuoi-cung")
    public Long getGiaGiamCuoiCung(@RequestParam UUID id) {
        return chiTietSanPhamService.getGiaGiamCuoiCung(id);
    }

    @GetMapping("so-luong/{id}")
    public Integer soLuong(@PathVariable(name = "id") UUID id) {
        return chiTietSanPhamService.soLuong(id);
    }

}
