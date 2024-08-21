package com.example.duantotnghiep.controller.giam_gia_san_pham_controller;

import com.example.duantotnghiep.entity.DanhMuc;
import com.example.duantotnghiep.entity.KieuDe;
import com.example.duantotnghiep.entity.ThuongHieu;
import com.example.duantotnghiep.entity.XuatXu;
import com.example.duantotnghiep.request.DanhMucRequest;
import com.example.duantotnghiep.request.KieuDeRequest;
import com.example.duantotnghiep.request.ThuongHieuRequest;
import com.example.duantotnghiep.request.XuatXuRequest;
import com.example.duantotnghiep.response.MessageResponse;
import com.example.duantotnghiep.service.thuoc_tinh_dong_san_pham_service.impl.DanhMucServiceImpl;
import com.example.duantotnghiep.service.thuoc_tinh_dong_san_pham_service.impl.KieuDeServiceImpl;
import com.example.duantotnghiep.service.thuoc_tinh_dong_san_pham_service.impl.ThuongHieuServiceImpl;
import com.example.duantotnghiep.service.thuoc_tinh_dong_san_pham_service.impl.XuatXuServiceImpl;
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
@RequestMapping("/api/v1/thuoc-tinh/")
public class ThuongTinhKhachHangController {

    @Autowired
    private DanhMucServiceImpl danhMucService;

    @GetMapping("show-danh-muc")
    public ResponseEntity<List<DanhMuc>> getAllDanhMuc() {
        return new ResponseEntity<>(danhMucService.getAll(), HttpStatus.OK);
    }

    @GetMapping("hien-thi-danh-muc")
    public ResponseEntity<List<DanhMuc>> getAllDanhMuc(
            @RequestParam(name = "tenDanhMuc", required = false) String tenDanhMuc,
            @RequestParam(name = "trangThai", required = false) Integer trangThai,
            @RequestParam(name = "pageNumber", defaultValue = "0") Integer pageNumber,
            @RequestParam(name = "pageSize", defaultValue = "20") Integer pageSize
    ) {
        return new ResponseEntity<>(danhMucService.getAllDanhMuc(trangThai, tenDanhMuc, pageNumber, pageSize), HttpStatus.OK);
    }

    @GetMapping("detail-danh-muc")
    public DanhMuc getDanhMucById(@RequestParam UUID id) {
        return danhMucService.getById(id);
    }

    @PostMapping("create-danh-muc")
    public ResponseEntity<MessageResponse> createDanhMuc(@Valid @RequestBody DanhMucRequest danhMucRequest, Principal principal) throws IOException, CsvValidationException {
        return new ResponseEntity<>(danhMucService.create(danhMucRequest, principal.getName()), HttpStatus.CREATED);
    }

    @PutMapping("update-danh-muc")
    public ResponseEntity<MessageResponse> updateDanhMuc(@RequestParam UUID id,@Valid @RequestBody DanhMucRequest danhMucRequest,Principal principal) {
        try {
            MessageResponse response = danhMucService.update(id, danhMucRequest, principal.getName());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(MessageResponse.builder().message("Lỗi khi cập nhật").build(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PutMapping("delete-danh-muc")
    public ResponseEntity<MessageResponse> deleteDanhMuc(@RequestParam UUID id) {
        return new ResponseEntity<>(danhMucService.delete(id), HttpStatus.OK);
    }

    @Autowired
    private ThuongHieuServiceImpl thuongHieuService;

    @GetMapping("show-thuong-hieu")
    public ResponseEntity<List<ThuongHieu>> getAllThuongHieu() {
        return new ResponseEntity<>(thuongHieuService.getAll(), HttpStatus.OK);
    }

    @GetMapping("hien-thi-thuong-hieu")
    public ResponseEntity<List<ThuongHieu>> getAllThuongHieu(
            @RequestParam(name = "tenThuongHieu", required = false) String tenThuongHieu,
            @RequestParam(name = "trangThai", required = false) Integer trangThai,
            @RequestParam(name = "pageNumber", defaultValue = "0") Integer pageNumber,
            @RequestParam(name = "pageSize", defaultValue = "20") Integer pageSize
    ) {
        return new ResponseEntity<>(thuongHieuService.getAllThuongHieu(trangThai, tenThuongHieu, pageNumber, pageSize), HttpStatus.OK);
    }

    @GetMapping("detail-thuong-hieu")
    public ThuongHieu getThuongHieuById(@RequestParam UUID id) {
        return thuongHieuService.getById(id);
    }

    @PostMapping("create-thuong-hieu")
    public ResponseEntity<MessageResponse> createThuongHieu(
            @Valid @RequestBody ThuongHieuRequest thuongHieuRequest,
            Principal principal) throws IOException, CsvValidationException {
        return new ResponseEntity<>(thuongHieuService.create(thuongHieuRequest, principal.getName()), HttpStatus.CREATED);
    }

    @PutMapping("update-thuong-hieu")
    public ResponseEntity<MessageResponse> updateThuongHieu(@RequestParam UUID id,@Valid @RequestBody ThuongHieuRequest thuongHieuRequest,Principal principal) {
        try {
            MessageResponse response = thuongHieuService.update(id, thuongHieuRequest, principal.getName());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(MessageResponse.builder().message("Lỗi khi cập nhật").build(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PutMapping("delete-thuong-hieu")
    public ResponseEntity<MessageResponse> deleteThuongHieu(@RequestParam UUID id) {
        return new ResponseEntity<>(thuongHieuService.delete(id), HttpStatus.OK);
    }

    @Autowired
    private XuatXuServiceImpl xuatXuService;

    @GetMapping("show-xuat-xu")
    public ResponseEntity<List<XuatXu>> getAllXuatXu() {
        return new ResponseEntity<>(xuatXuService.getAll(), HttpStatus.OK);
    }

    @GetMapping("hien-thi-xuat-xu")
    public ResponseEntity<List<XuatXu>> getAllXuatXu(
            @RequestParam(name = "tenXuatXu", required = false) String tenXuatXu,
            @RequestParam(name = "trangThai", required = false) Integer trangThai,
            @RequestParam(name = "pageNumber", defaultValue = "0") Integer pageNumber,
            @RequestParam(name = "pageSize", defaultValue = "20") Integer pageSize
    ) {
        return new ResponseEntity<>(xuatXuService.getAllXuatXu(trangThai, tenXuatXu, pageNumber, pageSize), HttpStatus.OK);
    }

    @GetMapping("detail-xuat-xu")
    public XuatXu getXuatXuById(@RequestParam UUID id) {
        return xuatXuService.getById(id);
    }

    @PostMapping("create-xuat-xu")
    public ResponseEntity<MessageResponse> createXuatXu(@Valid @RequestBody XuatXuRequest xuatXuRequest, Principal principal) throws IOException, CsvValidationException {
        return new ResponseEntity<>(xuatXuService.create(xuatXuRequest, principal.getName()), HttpStatus.CREATED);
    }

    @PutMapping("update-xuat-xu")
    public ResponseEntity<MessageResponse> updateXuatXu(@RequestParam UUID id,@Valid @RequestBody XuatXuRequest xuatXuRequest,Principal principal) {
        try {
            MessageResponse response = xuatXuService.update(id, xuatXuRequest, principal.getName());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(MessageResponse.builder().message("Lỗi khi cập nhật").build(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PutMapping("delete-xuat-xu")
    public ResponseEntity<MessageResponse> deleteXuatXu(@RequestParam UUID id) {
        return new ResponseEntity<>(xuatXuService.delete(id), HttpStatus.OK);
    }

    @Autowired
    private KieuDeServiceImpl kieuDeService;

    @GetMapping("show-kieu-de")
    public ResponseEntity<List<KieuDe>> getAllKieuDe() {
        return new ResponseEntity<>(kieuDeService.getAll(), HttpStatus.OK);
    }

    @GetMapping("hien-thi-kieu-de")
    public ResponseEntity<List<KieuDe>> getAllKieuDe(
            @RequestParam(name = "tenKieuDe", required = false) String tenKieuDe,
            @RequestParam(name = "trangThai", required = false) Integer trangThai,
            @RequestParam(name = "pageNumber", defaultValue = "0") Integer pageNumber,
            @RequestParam(name = "pageSize", defaultValue = "20") Integer pageSize
    ) {
        return new ResponseEntity<>(kieuDeService.getAllKieuDe(trangThai, tenKieuDe, pageNumber, pageSize), HttpStatus.OK);
    }

    @GetMapping("detail-kieu-de")
    public KieuDe getKieuDeById(@RequestParam UUID id) {
        return kieuDeService.getById(id);
    }

    @PostMapping("create-kieu-de")
    public ResponseEntity<MessageResponse> createKieuDe(@Valid @RequestBody KieuDeRequest kieuDeRequest, Principal principal) throws IOException, CsvValidationException {
        return new ResponseEntity<>(kieuDeService.create(kieuDeRequest, principal.getName()), HttpStatus.CREATED);
    }

    @PutMapping("update-kieu-de")
    public ResponseEntity<MessageResponse> updateKieuDe(@RequestParam UUID id,@Valid @RequestBody KieuDeRequest kieuDeRequest,Principal principal) {
        try {
            MessageResponse response = kieuDeService.update(id, kieuDeRequest,principal.getName());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(MessageResponse.builder().message("Lỗi khi cập nhật").build(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("delete-kieu-de")
    public ResponseEntity<MessageResponse> deleteKieuDe(@RequestParam UUID id) {
        return new ResponseEntity<>(kieuDeService.delete(id), HttpStatus.OK);
    }
}
