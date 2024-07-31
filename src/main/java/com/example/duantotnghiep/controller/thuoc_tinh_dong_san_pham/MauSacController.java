package com.example.duantotnghiep.controller.thuoc_tinh_dong_san_pham;

import com.example.duantotnghiep.entity.MauSac;
import com.example.duantotnghiep.request.MauSacRequest;
import com.example.duantotnghiep.response.MessageResponse;
import com.example.duantotnghiep.service.thuoc_tinh_dong_san_pham_service.impl.MauSacServiceImpl;
import com.opencsv.exceptions.CsvValidationException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/mau-sac/")
public class MauSacController {

    @Autowired
    private MauSacServiceImpl mauSacService;

    @GetMapping("show")
    public ResponseEntity<List<MauSac>> getAllSanPhamGiamGia() {
        return new ResponseEntity<>(mauSacService.getAll(), HttpStatus.OK);
    }

    @GetMapping("hien-thi")
    public ResponseEntity<List<MauSac>> getAllMauSac(
            @RequestParam(name = "tenMauSac", required = false) String tenMauSac,
            @RequestParam(name = "trangThai", required = false) Integer trangThai,
            @RequestParam(name = "pageNumber", defaultValue = "0") Integer pageNumber,
            @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize
    ) {
        return new ResponseEntity<>(mauSacService.getAllMauSac(trangThai, tenMauSac, pageNumber, pageSize), HttpStatus.OK);
    }

    @GetMapping("detail")
    public MauSac getSizeById(@RequestParam UUID id) {
        return mauSacService.getById(id);
    }

    @PostMapping("create")
    public ResponseEntity<MessageResponse> createSize(@Valid @RequestBody MauSacRequest mauSacRequest, Principal principal) throws IOException, CsvValidationException {
        return new ResponseEntity<>(mauSacService.create(mauSacRequest,principal.getName()), HttpStatus.CREATED);
    }

    @PutMapping("update")
    public ResponseEntity<MessageResponse> updateSize(@RequestParam UUID id,@Valid @RequestBody MauSacRequest mauSacRequest,Principal principal) {
        try {
            MessageResponse response = mauSacService.update(id, mauSacRequest,principal.getName());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(MessageResponse.builder().message("Lỗi khi cập nhật").build(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("delete")
    public ResponseEntity<MessageResponse> deleteSize(@RequestParam UUID id) {
        return new ResponseEntity<>(mauSacService.delete(id), HttpStatus.OK);
    }

    @GetMapping("/find-by-mau-sac")
    public ResponseEntity<?> findmausac(@RequestParam("mausac") String mausac) {
        List<MauSac> thuocTinhList = mauSacService.findMauSac(mausac);
        return new ResponseEntity<>(thuocTinhList.size(), HttpStatus.OK);
    }
}
