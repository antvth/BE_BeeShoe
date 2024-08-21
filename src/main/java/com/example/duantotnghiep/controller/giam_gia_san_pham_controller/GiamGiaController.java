package com.example.duantotnghiep.controller.giam_gia_san_pham_controller;

import com.example.duantotnghiep.entity.Size;
import com.example.duantotnghiep.request.GiamGiaRequest;
import com.example.duantotnghiep.request.UpdateGiamGiaResquest;
import com.example.duantotnghiep.response.*;
import com.example.duantotnghiep.service.giam_gia_service.impl.GiamGiaServiceimpl;
import com.opencsv.exceptions.CsvValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.Principal;
import java.time.LocalDate;
import java.util.*;

@RestController
@RequestMapping("/api/v1/giam-gia/")
public class GiamGiaController {

    @Autowired
    private GiamGiaServiceimpl Service;

    @GetMapping("hien-thi")
    public ResponseEntity<List<GiamGiaResponse>> getAllSize(
            @RequestParam(name = "trangThai", required = false) Integer trangThai,
            @RequestParam(name = "maGiamGia", required = false) String maGiamGia,
            @RequestParam(name = "tenGiamGia", required = false) String tenGiamGia,
            @RequestParam(name = "tenSanPham", required = false) String tenSanPham,
            @RequestParam(name = "startDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
            @RequestParam(name = "pageNumber", defaultValue = "0") Integer pageNumber,
            @RequestParam(name = "pageSize", defaultValue = "20") Integer pageSize
    ) {
        return new ResponseEntity<>(Service.getAll(trangThai, maGiamGia, tenGiamGia,tenSanPham, startDate, pageNumber, pageSize), HttpStatus.OK);
    }

    @GetMapping("/check-ten-giam-gia")
    public ResponseEntity<?> checkTenGiamGial(@RequestParam String tenGiamGia) {
        boolean isExisted = Service.isTenGiamGiaExisted(tenGiamGia);
        Map<String, Object> response = new HashMap<>();
        response.put("tenGiamGia", tenGiamGia);
        response.put("existed", isExisted);

        return ResponseEntity.ok(response);
    }
    @PutMapping("update/{id}")
    public ResponseEntity<MessageResponse> updateGiamGia(@PathVariable UUID id,
            @RequestBody UpdateGiamGiaResquest updateGiamGiaRequest, Principal principal)
            throws IOException, CsvValidationException {
        Service.updateGiamGia(id, updateGiamGiaRequest, principal.getName());
        return new ResponseEntity<>(
                MessageResponse.builder().message("Cập nhật thông tin giảm giá thành công.").build(), HttpStatus.OK);
    }

    @GetMapping("updateStatus")
    public ResponseEntity<MessageResponse> updateVoucherStatus() {
        return new ResponseEntity<>(Service.checkAndSetStatus(), HttpStatus.OK);
    }

    // search by
    @GetMapping("searchString_bykey")
    public ResponseEntity<List<GiamGiaResponse>> findByKhachHangByIdHoaDon(@RequestParam(name = "key") String key) {
        return null;
    }

    @GetMapping("showproduct")
    public ResponseEntity<List<ProductDetailResponse>> getAllProduct(
            @RequestParam(name = "tenSanPham", required = false) String tenGiamGia,
            @RequestParam(name = "pageNumber", defaultValue = "0") Integer pageNumber,
            @RequestParam(name = "pageSize", defaultValue = "20") Integer pageSize
    ) {
        return new ResponseEntity<>(Service.getAllProduct( tenGiamGia, pageNumber, pageSize), HttpStatus.OK);
    }


    @GetMapping("detail")
    public ResponseEntity<List<ProductDetailResponse>> search(@RequestParam(name = "id") UUID id) {
        return new ResponseEntity<>(Service.ListSearch(id), HttpStatus.OK);
    }

    @GetMapping("detailList")
    public ResponseEntity<List<GiamGiaResponse>> ListDetail(@RequestParam(name = "id") UUID id) {
        return new ResponseEntity<>(Service.ListGiamGiaDeatil(id), HttpStatus.OK);
    }

    @GetMapping("searchDatebykey")
    public ResponseEntity<List<GiamGiaResponse>> findByKhachHangB(
            @RequestParam(name = "key1") @DateTimeFormat(pattern = "yyyy-MM-dd") Date key1) {

        List<GiamGiaResponse> result = Service.findbyValueDate(key1);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("searchProduct_bykey")
    public ResponseEntity<List<ProductDetailResponse>> findProduct(@RequestParam(name = "key") String key) {
        return new ResponseEntity<>(Service.findbyProduct(key), HttpStatus.OK);
    }
    @GetMapping("updateStatus/{id}")
    public ResponseEntity<MessageResponse> updateVoucherbyStaus(@PathVariable UUID id) {
        Service.updateGiamGiaStaus(id);
        return new ResponseEntity<>(MessageResponse.builder().message("Cập nhật voucher thành công.").build(),
                HttpStatus.OK);
    }
    @GetMapping("searchStatus_bykey")
    public ResponseEntity<List<GiamGiaResponse>> findByKha(@RequestParam(name = "key") Integer key) {
        return new ResponseEntity<>(Service.findbyValueStatus(key), HttpStatus.OK);
    }

    @PostMapping("create")
    public ResponseEntity<MessageResponse> createKhachHang(@RequestBody GiamGiaRequest createKhachRequest,
            Principal principal) throws IOException, CsvValidationException {
        return new ResponseEntity<>(Service.createGiamGia(createKhachRequest, principal.getName()), HttpStatus.CREATED);
    }

    @GetMapping("checkTenGiamGia")
    public ResponseEntity<Boolean> checkTenGiamGia(@RequestParam(name = "tenGiamGia") String tenGiamGia) {
        boolean exists = Service.isTenGiamGiaExists(tenGiamGia);
        return null;
    }

    @GetMapping("check-product-record-count")
    public ResponseEntity<Boolean> checkProductRecordCount(@RequestParam(name = "idsanpham") UUID productId) {
        boolean result = Service.checkProductRecordCount(productId);
        return null;
    }

    @GetMapping("gia")
    public ResponseEntity<Long> giaGiam(@RequestParam(name = "idsanpham") UUID productId) {
        return new ResponseEntity<>(Service.getGiaGiamCuoiCung(productId), HttpStatus.OK);
    }

}
