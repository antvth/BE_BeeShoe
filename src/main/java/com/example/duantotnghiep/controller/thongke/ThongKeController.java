package com.example.duantotnghiep.controller.thongke;

import com.example.duantotnghiep.response.DoanhThuResponse;
import com.example.duantotnghiep.response.SanPhamBanChayResponse;
import com.example.duantotnghiep.service.thongke.impl.ThongKeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Date;

import java.util.List;

@RestController
@RequestMapping("/api/thong-ke/")
public class  ThongKeController {

    @Autowired
    private ThongKeServiceImpl thongKeService;

    @GetMapping("doanhthu")
    public ResponseEntity<List<DoanhThuResponse>> doanhThu(
            @RequestParam(name = "ngayBd") Date ngayBd,
            @RequestParam(name = "ngayKt") Date ngayKt) {
        return new ResponseEntity<>(thongKeService.doanhThu(ngayBd, ngayKt), HttpStatus.OK);
    }

    @GetMapping("san-pham-ban-chay")
    public ResponseEntity<List<SanPhamBanChayResponse>> sanPhamBanChay() {
        return new ResponseEntity<>(thongKeService.listSanPhamBanChay(), HttpStatus.OK);
    }

    @GetMapping("tongtienhomnay")
    public ResponseEntity<BigDecimal> tongTienDonHomNay() {
        return new ResponseEntity<>(thongKeService.tongTienDonHomNay(), HttpStatus.OK);
    }

    @GetMapping("tongtientuannay")
    public ResponseEntity<BigDecimal> tongTienDonTuanNay() {
        return new ResponseEntity<>(thongKeService.tongTienDonTuanNay(), HttpStatus.OK);
    }

    @GetMapping("tongtiendonthangnay")
    public ResponseEntity<BigDecimal> tongTienDonThangNay() {
        return new ResponseEntity<>(thongKeService.tongTienDonThangNay(), HttpStatus.OK);
    }

    @GetMapping("tongtiennamnay")
    public ResponseEntity<BigDecimal> tongTienDonNamNay() {
        return new ResponseEntity<>(thongKeService.tongTienDonNamNay(), HttpStatus.OK);
    }

    @GetMapping("so-don-hom-nay")
    public ResponseEntity<Integer> soDonHomNay() {
        return new ResponseEntity<>(thongKeService.soDonHomNay(), HttpStatus.OK);
    }

    @GetMapping("so-don-tuan-nay")
    public ResponseEntity<Integer> soDonTuanNay() {
        return new ResponseEntity<>(thongKeService.soDonTuanNay(), HttpStatus.OK);
    }

    @GetMapping("so-don-thang-nay")
    public ResponseEntity<Integer> soDonThangNay() {
        return new ResponseEntity<>(thongKeService.soDonThangNay(), HttpStatus.OK);
    }


    @GetMapping("so-don-nam-nay")
    public ResponseEntity<Integer> soDonNamNay() {
        return new ResponseEntity<>(thongKeService.soDonNamNay(), HttpStatus.OK);
    }


    @GetMapping("so-don-huy-hom-nay")
    public ResponseEntity<Integer> soDonHuyHomNay() {
        return new ResponseEntity<>(thongKeService.soDonHuyHomNay(), HttpStatus.OK);
    }

    @GetMapping("so-don-huy-tuan-nay")
    public ResponseEntity<Integer> soDonHuyTuanNay() {
        return new ResponseEntity<>(thongKeService.soDonHuyTuanNay(), HttpStatus.OK);
    }

    @GetMapping("so-don-huy-thang-nay")
    public ResponseEntity<Integer> soDonHuyThangNay() {
        return new ResponseEntity<>(thongKeService.soDonHuyThangNay(), HttpStatus.OK);
    }

    @GetMapping("so-don-huy-nam-nay")
    public ResponseEntity<Integer> soDonHuyNamNay() {
        return new ResponseEntity<>(thongKeService.soDonHuyNamNay(), HttpStatus.OK);
    }

    @GetMapping("sum-day-number-product")
    public ResponseEntity<Integer> soSanPhamBanRaHomNay() {
        return new ResponseEntity<>(thongKeService.soSanPhamBanRaHomNay(), HttpStatus.OK);
    }

    @GetMapping("sum-week-number-product")
    public ResponseEntity<Integer> soSanPhamBanRaTuanNay() {
        return new ResponseEntity<>(thongKeService.soSanPhamBanRaTuanNay(), HttpStatus.OK);
    }

    @GetMapping("sum-month-number-product")
    public ResponseEntity<Integer> soSanPhamBanRaThangNay() {
        return new ResponseEntity<>(thongKeService.soSanPhamBanRaThangNay(), HttpStatus.OK);
    }

    @GetMapping("sum-year-number-product")
    public ResponseEntity<Integer> soSanPhamBanNamNay() {
        return new ResponseEntity<>(thongKeService.soSanPhamBanNamNay(), HttpStatus.OK);
    }

    @GetMapping("count-one")
    public ResponseEntity<Integer> demSoHoaDonChoXacNhan() {
        return new ResponseEntity<>(thongKeService.demSoHoaDonChoXacNhan(), HttpStatus.OK);
    }

    @GetMapping("count-two")
    public ResponseEntity<Integer> demSoHoaDonXacNhan() {
        return new ResponseEntity<>(thongKeService.demSoHoaDonXacNhan(), HttpStatus.OK);
    }

    @GetMapping("count-three")
    public ResponseEntity<Integer> demSoHoaDonChoGiaoHang() {
        return new ResponseEntity<>(thongKeService.demSoHoaDonChoGiaoHang(), HttpStatus.OK);
    }

    @GetMapping("count-four")
    public ResponseEntity<Integer> demSoHoaDonDangGiao() {
        return new ResponseEntity<>(thongKeService.demSoHoaDonDangGiao(), HttpStatus.OK);
    }

    @GetMapping("count-five")
    public ResponseEntity<Integer> demSoHoaDonThanhCong() {
        return new ResponseEntity<>(thongKeService.demSoHoaDonThanhCong(), HttpStatus.OK);
    }

    @GetMapping("count-six")
    public ResponseEntity<Integer> demSoHoaDonDaHuy() {
        return new ResponseEntity<>(thongKeService.demSoHoaDonDaHuy(), HttpStatus.OK);
    }

}
