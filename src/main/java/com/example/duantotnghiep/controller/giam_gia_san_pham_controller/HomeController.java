package com.example.duantotnghiep.controller.giam_gia_san_pham_controller;

import com.example.duantotnghiep.response.ProductShopResponse;
import com.example.duantotnghiep.response.SanPhamBanChayResponse;
import com.example.duantotnghiep.response.SanPhamResponse;
import com.example.duantotnghiep.service.ban_tai_quay_service.impl.ProductCounterServiceImpl;
import com.example.duantotnghiep.service.thongke.impl.ThongKeServiceImpl;
import com.example.duantotnghiep.service.thuoc_tinh_dong_san_pham_service.impl.SanPhamServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/api/public/")
public class HomeController {

    @Autowired
    private SanPhamServiceImpl sanPhamService;

    @Autowired
    private ProductCounterServiceImpl chiTietSanPhamService;

    @Autowired
    private ThongKeServiceImpl thongKeService;

    @GetMapping("home")
    public ResponseEntity<List<ProductShopResponse>> getAll(
            @RequestParam(name = "pageNumber", defaultValue = "0") Integer pageNumber,
            @RequestParam(name = "pageSize", defaultValue = "8") Integer pageSize
    ) {
        return new ResponseEntity<>(chiTietSanPhamService.getAllShop(pageNumber, pageSize), HttpStatus.OK);
    }

    @GetMapping("list")
    public ResponseEntity<List<SanPhamResponse>> getProductSelt() {
        return new ResponseEntity<>(sanPhamService.getBestSellingProducts(), HttpStatus.OK);
    }

    @GetMapping("detailList")
    public ResponseEntity<List<SanPhamResponse>> ListDetail(@RequestParam(name = "id") UUID id) {
        return new ResponseEntity<>(sanPhamService.getNewProductbyId(id), HttpStatus.OK);
    }

    @GetMapping("detailListSelt")
    public ResponseEntity<List<SanPhamResponse>> ListDetailSelt(@RequestParam(name = "id") UUID id) {
        return new ResponseEntity<>(sanPhamService.getBestSellingProductsbyId(id), HttpStatus.OK);
    }

    @GetMapping("san-pham-ban-chay")
    public ResponseEntity<List<SanPhamBanChayResponse>> sanPhamBanChay() {
        return new ResponseEntity<>(thongKeService.listSanPhamBanChay(), HttpStatus.OK);
    }

}
