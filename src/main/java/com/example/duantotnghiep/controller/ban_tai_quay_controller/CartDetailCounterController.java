package com.example.duantotnghiep.controller.ban_tai_quay_controller;

import com.example.duantotnghiep.entity.GioHangChiTiet;
import com.example.duantotnghiep.mapper.GioHangCustom;
import com.example.duantotnghiep.response.MessageResponse;
import com.example.duantotnghiep.service.ban_tai_quay_service.impl.CartDetailCounterServiceImpl;
import com.opencsv.exceptions.CsvValidationException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.security.Principal;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/gio-hang-chi-tiet/")
public class CartDetailCounterController {

    @Autowired
    private CartDetailCounterServiceImpl gioHangChiTietService;

    @GetMapping("hien-thi")
    public ResponseEntity<List<GioHangCustom>> show(
            @RequestParam(name = "id") UUID id,
            @RequestParam(name = "pageNumber", defaultValue = "0") Integer pageNumber,
            @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize) {
        return ResponseEntity.ok(gioHangChiTietService.loadGH(id, pageNumber, pageSize));
    }

    @GetMapping("hien-thi-tien")
    public ResponseEntity<List<GioHangCustom>> showTien(
            @RequestParam(name = "id") UUID id) {
        return ResponseEntity.ok(gioHangChiTietService.loadGHTien(id));
    }

    @PostMapping("them-san-pham")
    public ResponseEntity<MessageResponse> themSanPhamVaoGioHangChiTiet(
            @RequestParam(name = "idGioHang") UUID idGioHang,
            @RequestParam(name = "idSanPhamChiTiet") UUID idSanPhamChiTiet,
            @RequestParam(name = "soLuong") int soLuong,
            @RequestParam(name = "id") UUID id,
            Principal principal) throws IOException, CsvValidationException {
        return new ResponseEntity<>(
                gioHangChiTietService.themSanPhamVaoGioHangChiTiet(idGioHang, idSanPhamChiTiet, soLuong, id, principal.getName()),
                HttpStatus.CREATED);
    }

    @PostMapping("them-san-pham-qrcode")
    public ResponseEntity<MessageResponse> themSanPhamVaoGioHangChiTietQrcode(
            @RequestParam(name = "idGioHang") UUID idGioHang,
            @RequestParam(name = "qrCode") String qrCode,
            Principal principal) throws IOException, CsvValidationException {
        return new ResponseEntity<>(
                gioHangChiTietService.themSanPhamVaoGioHangChiTietQrCode(idGioHang, qrCode, principal.getName()),
                HttpStatus.CREATED);
    }

    @PutMapping("update-quantity")
    public ResponseEntity<String> capNhatSoLuong(
            @RequestParam(name = "idgiohangchitiet") UUID idgiohangchitiet,
            @RequestParam(name = "quantity") Integer quantity,
            Principal principal
    ) {
        try {
            gioHangChiTietService.capNhatSoLuong(idgiohangchitiet, quantity, principal.getName());
            return ResponseEntity.ok("Số lượng đã được cập nhật.(-> nên xem lại Console log)");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.badRequest().body("Không tìm thấy sản phẩm trong giỏ hàng.");
        } catch (CsvValidationException | IOException e) {
            // Log the exception details
            e.printStackTrace(); // You can replace this with proper logging using a logger framework

            // Return an appropriate response to the client
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Đã xảy ra lỗi khi cập nhật số lượng sản phẩm. Vui lòng thử lại sau.");
        } catch (Exception e) {
            // Log any other unexpected exceptions
            e.printStackTrace(); // Replace this with proper logging

            // Return a generic error response to the client
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Đã xảy ra lỗi không xác định. Vui lòng liên hệ quản trị viên.");
        }
    }
    @DeleteMapping("delete_product")
    public ResponseEntity<Void> deleteProductInCart(@RequestParam("id") UUID id, Principal principal) throws IOException, CsvValidationException {
        gioHangChiTietService.deleteProductInCart(id, principal.getName());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("fill-id")
    public ResponseEntity<List<GioHangChiTiet>> show(
            @RequestParam(name = "id") UUID id) {
        return new ResponseEntity<>(gioHangChiTietService.getIdCartDetail(id), HttpStatus.OK);
    }

    @GetMapping("total-price")
    public ResponseEntity<?> tongTien(@RequestParam(name = "id") UUID id) {
        String totalPrice = gioHangChiTietService.tongTienHang(id);
        Map<String, String> response = new HashMap<>();
        response.put("totalPrice", totalPrice);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("total-price-order")
    public BigDecimal tongTienHoaDon(@RequestParam(name = "id") UUID id) {
        return gioHangChiTietService.tongTienSpHoaDon(id);
    }

    @GetMapping("tien-thieu")
    public BigDecimal tienThieu(@RequestParam(name = "id") UUID id, @RequestParam(name = "idKhach") UUID idKhach) {
        return gioHangChiTietService.tienThieu(id, idKhach);
    }

    @GetMapping("so-luong-san-pham")
    public Integer soLuong(@RequestParam(name = "id") UUID id) {
        return gioHangChiTietService.soLuong(id);
    }

}
