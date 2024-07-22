package com.example.duantotnghiep.controller.ban_tai_quay_controller;

import com.example.duantotnghiep.request.HoaDonGiaoThanhToanRequest;
import com.example.duantotnghiep.request.HoaDonThanhToanRequest;
import com.example.duantotnghiep.response.*;
import com.example.duantotnghiep.service.ban_tai_quay_service.impl.OrderCounterServiceImpl;
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
@RequestMapping("/api/v1/don-hang/")
public class OrdersCounterController {

    @Autowired
    private OrderCounterServiceImpl hoaDonService;

    @GetMapping("show")
    public ResponseEntity<List<HoaDonResponse>> viewHoaDonTaiQuay(
            @RequestParam(name = "pageNumber", defaultValue = "0") Integer pageNumber,
            @RequestParam(name = "pageSize", defaultValue = "5") Integer pageSize) {
        if (pageNumber <= 0) {
            pageNumber = 0;
        }
        return new ResponseEntity<>(hoaDonService.viewHoaDonTaiQuay(pageNumber, pageSize), HttpStatus.OK);
    }

    @GetMapping("order-counter/{id}")
    public ResponseEntity<OrderCounterCartsResponse> findByHoaDon(@PathVariable("id") UUID id) {
        return new ResponseEntity<>(hoaDonService.findByHoaDon(id), HttpStatus.OK);
    }

    @GetMapping("id_cart")
    public ResponseEntity<IdGioHangResponse> showIdGioHang(@RequestParam(name = "id") UUID id) {
        return new ResponseEntity<>(hoaDonService.showIdGioHangCt(id), HttpStatus.OK);
    }

    @GetMapping("search/{ma}")
    public ResponseEntity<List<HoaDonResponse>> viewHoaDonTaiQuay(@PathVariable("ma") String ma) {
        return new ResponseEntity<>(hoaDonService.findByCodeOrder(ma), HttpStatus.OK);
    }

    @PostMapping("create")
    public ResponseEntity<OrderCounterCResponse> taoHoaDon(Principal principal) throws IOException, CsvValidationException {
        return new ResponseEntity<>(hoaDonService.taoHoaDon(principal.getName()), HttpStatus.CREATED);
    }

    @PostMapping("create-hoa-don-chi-tiet")
    public ResponseEntity<MessageResponse> taoHoaDonDetail(
            @RequestParam("idHoaDon") UUID idHoaDon,
            @RequestBody HoaDonThanhToanRequest hoaDonThanhToanRequest,
            Principal principal) throws IOException, CsvValidationException {
        return new ResponseEntity<>(hoaDonService.updateHoaDon(idHoaDon, hoaDonThanhToanRequest, principal.getName()), HttpStatus.CREATED);
    }

    @PostMapping("create-hoa-don-chi-tiet-giao")
    public ResponseEntity<MessageResponse> taoHoaDonGiao(
            @RequestParam("idHoaDon") UUID idHoaDon,
            @Valid @RequestBody HoaDonGiaoThanhToanRequest hoaDonGiaoThanhToanRequest,
            Principal principal) throws IOException, CsvValidationException {
        return new ResponseEntity<>(hoaDonService.updateHoaDonGiaoTaiQuay(idHoaDon, hoaDonGiaoThanhToanRequest, principal.getName(), true), HttpStatus.CREATED);
    }

    @PutMapping("remove")
    public ResponseEntity<MessageResponse> removeOrder(@RequestParam("id") UUID id, Principal principal) throws IOException, CsvValidationException {
        return new ResponseEntity<>(hoaDonService.removeOrder(id, principal.getName()),HttpStatus.OK);
    }

}
