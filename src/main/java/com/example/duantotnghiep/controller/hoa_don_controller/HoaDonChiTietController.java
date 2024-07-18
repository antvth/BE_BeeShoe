package com.example.duantotnghiep.controller.hoa_don_controller;

import com.example.duantotnghiep.entity.HoaDon;
import com.example.duantotnghiep.request.*;
import com.example.duantotnghiep.response.*;
import com.example.duantotnghiep.service.hoa_don_service.impl.HoaDonChiTietServiceImpl;
import com.example.duantotnghiep.service.hoa_don_service.impl.TrangThaiHoaDonServiceImpl;
import com.opencsv.exceptions.CsvValidationException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/hoa-don-chi-tiet/")
public class HoaDonChiTietController {

    @Autowired
    private HoaDonChiTietServiceImpl hoaDonChiTietService;

     @Autowired
    private TrangThaiHoaDonServiceImpl trangThaiHoaDonService;

    @GetMapping("hien-thi-don/{idHoaDon}")
    public ResponseEntity<ThongTinDonHang> viewThongTinDonHang(@PathVariable(name = "idHoaDon") UUID idHoaDon) {
        return new ResponseEntity<>(hoaDonChiTietService.getThongTinDonHang(idHoaDon), HttpStatus.OK);
    }

    @GetMapping("hien-thi-san-pham/{idHoaDon}")
    public ResponseEntity<List<SanPhamHoaDonChiTietResponse>> getSanPhamHDCT(
            @PathVariable(name = "idHoaDon") UUID idHoaDon,
            @RequestParam(name = "pageNumber", defaultValue = "0") Integer pageNumber,
            @RequestParam(name = "pageSize", defaultValue = "5") Integer pageSize
) {
        return new ResponseEntity<>(hoaDonChiTietService.getSanPhamHDCT(pageNumber, pageSize, idHoaDon), HttpStatus.OK);
    }

    @GetMapping("hien-thi-lich-su/{idHoaDon}")
    public ResponseEntity<List<HinhThucThanhToanResponse>> getLichSuThanhToan(@PathVariable(name = "idHoaDon") UUID idHoaDon) {
        return new ResponseEntity<>(hoaDonChiTietService.getLichSuThanhToan(idHoaDon), HttpStatus.OK);
    }

    @GetMapping("hien-thi-trang-thai/{idHoaDon}")
    public ResponseEntity<List<TrangThaiHoaDonResponse>> getAllTrangThaiHoaDon(@PathVariable(name = "idHoaDon") UUID idHoaDon) {
        return new ResponseEntity<>(hoaDonChiTietService.getAllTrangThaiHoaDon(idHoaDon), HttpStatus.OK);
    }

    @PostMapping("confirm-order/{hoadonId}")
    public ResponseEntity<MessageResponse> confirmOrder(
            @PathVariable UUID hoadonId,
            @Valid @RequestBody TrangThaiHoaDonRequest request, Principal principal) {
        return new ResponseEntity<>(trangThaiHoaDonService.confirmOrder(hoadonId, request, principal.getName(), true), HttpStatus.CREATED);
    }

    @PutMapping("confirm-order-client/{hoadonId}")
    public ResponseEntity<MessageResponse> confirmOrderClient(
            @PathVariable UUID hoadonId,
            @Valid @RequestBody ConfirmOrderClientRequest request, Principal principal) throws IOException, CsvValidationException {
        return new ResponseEntity<>(trangThaiHoaDonService.confirmOrderClient(hoadonId, request, principal.getName()), HttpStatus.CREATED);
    }

    @PutMapping("confirm-order-deliver/{hoadonId}")
    public ResponseEntity<MessageResponse> confirmOrderDeliver(
            @PathVariable UUID hoadonId,
            @Valid @RequestBody ConfirmOrderDeliver request) {
        return new ResponseEntity<>(trangThaiHoaDonService.confirmOrderDeliver(hoadonId, request), HttpStatus.CREATED);
    }

    @PostMapping("confirm-thanh-toan/{hoadonId}")
    public ResponseEntity<String> confirmThanhToan(@PathVariable UUID hoadonId, @RequestBody XacNhanThanhToanRequest request) {
        hoaDonChiTietService.confirmThanhToan(hoadonId, request);
        return ResponseEntity.ok("Hóa đơn đã được thanh toán.");
    }

    @GetMapping("thanh-tien/{idHoaDon}")
    public ResponseEntity<MoneyResponse> getAllMoneyByHoaDon(@PathVariable(name = "idHoaDon") UUID idHoaDon) {
        return new ResponseEntity<>(hoaDonChiTietService.getMoneyByHoaDon(idHoaDon), HttpStatus.OK);
    }

    @PostMapping("them-san-pham")
    public ResponseEntity<MessageResponse> themSanPhamVaoGioHangChiTiet(
            @RequestParam(name = "idHoaDon") UUID idHoaDon,
            @RequestParam(name = "idSanPhamChiTiet") UUID idSanPhamChiTiet,
            @RequestParam(name = "soLuong") int soLuong,
            Principal principal) throws IOException, CsvValidationException {
        return new ResponseEntity<>(
                hoaDonChiTietService.themSanPhamVaoHoaDonChiTiet(idHoaDon, idSanPhamChiTiet, soLuong, principal.getName()),
                HttpStatus.CREATED);
    }

    @PutMapping("update-quantity")
    public ResponseEntity<String> capNhatSL(
            @RequestParam(name = "idHoaDonChiTiet") UUID idHoaDonChiTiet,
            @RequestParam(name = "quantity") Integer quantity,
            Principal principal) throws IOException, CsvValidationException {
        try {
            hoaDonChiTietService.capNhatSoLuong(idHoaDonChiTiet, quantity, principal.getName());
            return ResponseEntity.ok("Số lượng đã được cập nhật.(-> nên xem lại Console log)");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.badRequest().body("Không tìm thấy sản phẩm trong giỏ hàng.");
        }
    }

    @GetMapping("status-order/{id}")
    public ResponseEntity<HoaDon> getStatusOrder(@PathVariable(name = "id") UUID id) {
        return new ResponseEntity<>(hoaDonChiTietService.findByHoaDon(id), HttpStatus.OK);
    }

    @GetMapping("detail-product")
    public ResponseEntity<ProductDetailDTOResponse> getDetailSanPham(@RequestParam UUID idhdct) {
        return new ResponseEntity<>(hoaDonChiTietService.getDetailSanPham(idhdct), HttpStatus.OK);
    }

    @PostMapping("tra-hang")
    public ResponseEntity<MessageResponse> createOrUpdate(
            @RequestParam("id") UUID id,
            @RequestParam("idhdct") UUID idhdct,
            @Valid @RequestBody TraHangRequest traHangRequest,
            Principal principal) throws IOException, CsvValidationException {
        MessageResponse response = hoaDonChiTietService.createOrUpdate(id, idhdct, traHangRequest, principal.getName());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("delete")
    public ResponseEntity<MessageResponse> deleteOrderDetail(
            @RequestParam(name = "idHoaDon") UUID idHoaDon,
            @RequestParam(name = "id") UUID id,
            Principal principal) throws IOException, CsvValidationException {
        hoaDonChiTietService.deleteOrderDetail(idHoaDon, id, principal.getName());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("tra-hang/{id}")
    public ResponseEntity<?> traHang(@PathVariable(name = "id") UUID id) {
        return new ResponseEntity<>(hoaDonChiTietService.traHang(id), HttpStatus.OK);
    }

    @GetMapping("order-detail-update/{idHoaDon}")
    public ResponseEntity<OrderDetailUpdate> orderDetailUpdate(@PathVariable(name = "idHoaDon") UUID idHoaDon) {
        return new ResponseEntity<>(hoaDonChiTietService.orderDetailUpdate(idHoaDon), HttpStatus.OK);
    }

    @GetMapping("list-nhan-vien")
    public ResponseEntity<List<NhanVienOrderResponse>> nhanVienList() {
        return new ResponseEntity<>(hoaDonChiTietService.taiKhoanList(), HttpStatus.OK);
    }

    @PutMapping("update-nhan-vien")
    public ResponseEntity<MessageResponse> updateNhanVien(
            @RequestParam(name = "idHoaDon") UUID idHoaDon,
            @RequestParam(name = "idNhanVien") UUID idNhanVien,
            Principal principal) throws IOException, CsvValidationException {
        return new ResponseEntity<>(hoaDonChiTietService.updateNhanVien(idHoaDon, idNhanVien, principal.getName()), HttpStatus.OK);
    }

    @GetMapping("tong-tien-don-hang/{id}")
    public ResponseEntity<BigDecimal> tongTienSanPham(@PathVariable("id") UUID id) {
        return new ResponseEntity<>(hoaDonChiTietService.tongTienHang(id), HttpStatus.OK);
    }

    @PutMapping("huy-don/{id}")
    public ResponseEntity<MessageResponse> comfirmStatusHuyDon(
            @PathVariable("id") UUID id,
            @Valid @RequestBody TrangThaiHoaDonRequest trangThaiHoaDonRequest,
            Principal principal
    ) throws IOException, CsvValidationException {
        return new ResponseEntity<>(hoaDonChiTietService.comfirmStatusHuyDon(id, trangThaiHoaDonRequest, principal.getName()), HttpStatus.OK);
    }

}
