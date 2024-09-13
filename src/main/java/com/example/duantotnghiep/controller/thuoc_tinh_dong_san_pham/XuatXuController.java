package com.example.duantotnghiep.controller.thuoc_tinh_dong_san_pham;

import com.example.duantotnghiep.entity.XuatXu;
import com.example.duantotnghiep.request.XuatXuRequest;
import com.example.duantotnghiep.response.MessageResponse;
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
@RequestMapping("/api/v1/xuat-xu/")
public class XuatXuController {

    @Autowired
    private XuatXuServiceImpl xuatXuService;

    @GetMapping("show")
    public ResponseEntity<List<XuatXu>> getAllSanPhamGiamGia() {
        return new ResponseEntity<>(xuatXuService.getAll(), HttpStatus.OK);
    }

    @GetMapping("hien-thi")
    public ResponseEntity<List<XuatXu>> getAllXuatXu(
            @RequestParam(name = "tenXuatXu", required = false) String tenXuatXu,
            @RequestParam(name = "trangThai", required = false) Integer trangThai,
            @RequestParam(name = "pageNumber", defaultValue = "0") Integer pageNumber,
            @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize
    ) {
        return new ResponseEntity<>(xuatXuService.getAllXuatXu(trangThai, tenXuatXu, pageNumber, pageSize), HttpStatus.OK);
    }

    @GetMapping("detail")
    public XuatXu getXuatXuById(@RequestParam UUID id) {
        return xuatXuService.getById(id);
    }

    @PostMapping("create")
    public ResponseEntity<MessageResponse> createXuatXu(@Valid @RequestBody XuatXuRequest xuatXuRequest, Principal principal) throws IOException, CsvValidationException {
        return new ResponseEntity<>(xuatXuService.create(xuatXuRequest, principal.getName()), HttpStatus.CREATED);
    }

    @PutMapping("update")
    public ResponseEntity<MessageResponse> updateXuatXu(@RequestParam UUID id,@Valid @RequestBody XuatXuRequest xuatXuRequest,Principal principal) {
        try {
            MessageResponse response = xuatXuService.update(id, xuatXuRequest, principal.getName());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(MessageResponse.builder().message("Lỗi khi cập nhật").build(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PutMapping("delete")
    public ResponseEntity<MessageResponse> deleteXuatXu(@RequestParam UUID id) {
        return new ResponseEntity<>(xuatXuService.delete(id), HttpStatus.OK);
    }

    @GetMapping("check")
    public ResponseEntity<List<XuatXu>> findByTenXuatXu(@RequestParam String name) {
        return new ResponseEntity<>(xuatXuService.findByTenXuatXu(name), HttpStatus.OK);
    }
}
