package com.example.duantotnghiep.controller.audilog_controller;

import com.example.duantotnghiep.entity.AuditLog;
import com.example.duantotnghiep.service.audi_log_service.AuditLogService;
import com.opencsv.exceptions.CsvValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/v1/audilog/")
public class AuditLogController {
    private static final String BASE_DIRECTORY = "D:\\audilog";
    private static final String ADMIN_DIRECTORY = BASE_DIRECTORY + "\\admin";
    private static final String CUSTOMER_DIRECTORY = BASE_DIRECTORY + "\\customer";
    private static final String QUAN_LY_SAN_PHAM_DIRECTORY = ADMIN_DIRECTORY + "\\quanlysanpham";
    private static final String SANPHAM_DIRECTORY = QUAN_LY_SAN_PHAM_DIRECTORY + "\\sanpham";
    private static final String AUDIT_LOG_SANPHAM_FILE_PATH = SANPHAM_DIRECTORY + "\\audilog_sanpham.csv";
    private static final String SIZE_DIRECTORY = QUAN_LY_SAN_PHAM_DIRECTORY + "\\size";
    private static final String CHATLIEU_DIRECTORY = QUAN_LY_SAN_PHAM_DIRECTORY + "\\chatlieu";
    private static final String MAUSAC_DIRECTORY = QUAN_LY_SAN_PHAM_DIRECTORY + "\\mausac";
    private static final String DANHMUC_DIRECTORY = QUAN_LY_SAN_PHAM_DIRECTORY + "\\danhmuc";
    private static final String THUONGHIEU_DIRECTORY = QUAN_LY_SAN_PHAM_DIRECTORY + "\\thuonghieu";
    private static final String XUATXU_DIRECTORY = QUAN_LY_SAN_PHAM_DIRECTORY + "\\xuatxu";
    private static final String KIEUDE_DIRECTORY = QUAN_LY_SAN_PHAM_DIRECTORY + "\\kieude";
    private static final String QUAN_LY_TAI_KHOAN_DIRECTORY = ADMIN_DIRECTORY + "\\quanlytaikhoan";
    private static final String NHANVIEN_DIRECTORY = QUAN_LY_TAI_KHOAN_DIRECTORY + "\\nhanvien";
    private static final String KHACHHANG_DIRECTORY = QUAN_LY_TAI_KHOAN_DIRECTORY + "\\khachhang";
    private static final String VOUCHER_DIRECTORY = ADMIN_DIRECTORY + "\\voucher";
    private static final String HOADON_DIRECTORY = ADMIN_DIRECTORY + "\\hoadon";
    private static final String HOADON_CHI_TIET_DIRECTORY = ADMIN_DIRECTORY + "\\hoadonchitiet";
    private static final String KHUYENMAI_DIRECTORY = ADMIN_DIRECTORY + "\\khuyenmai";
    private static final String AUDIT_LOG_HOADON_FILE_PATH = HOADON_DIRECTORY + "\\audilog_hoadon.csv";
    private static final String AUDIT_LOG_HOADON_CHI_TIET_FILE_PATH = HOADON_CHI_TIET_DIRECTORY
            + "\\audilog_hoadonchitiet.csv";
    private static final String AUDIT_LOG_KHUYENMAI_FILE_PATH = KHUYENMAI_DIRECTORY + "\\audilog_khuyenmai.csv";
    private static final String AUDIT_LOG_SIZE_FILE_PATH = SIZE_DIRECTORY + "\\audilog_size.csv";
    private static final String AUDIT_LOG_CHATLIEU_FILE_PATH = CHATLIEU_DIRECTORY + "\\audilog_chatlieu.csv";
    private static final String AUDIT_LOG_MAUSAC_FILE_PATH = MAUSAC_DIRECTORY + "\\audilog_mausac.csv";
    private static final String AUDIT_LOG_DANHMUC_FILE_PATH = DANHMUC_DIRECTORY + "\\audilog_danhmuc.csv";
    private static final String AUDIT_LOG_THUONGHIEU_FILE_PATH = THUONGHIEU_DIRECTORY + "\\audilog_thuonghieu.csv";
    private static final String AUDIT_LOG_XUATXU_FILE_PATH = XUATXU_DIRECTORY + "\\audilog_xuatxu.csv";
    private static final String AUDIT_LOG_KIEUDE_FILE_PATH = KIEUDE_DIRECTORY + "\\audilog_kieude.csv";
    private static final String AUDIT_LOG_NHANVIEN_FILE_PATH = NHANVIEN_DIRECTORY + "\\audilog_nhanvien.csv";
    private static final String AUDIT_LOG_KHACHHANG_FILE_PATH = KHACHHANG_DIRECTORY + "\\audilog_khachhang.csv";
    private static final String AUDIT_LOG_VOUCHER_FILE_PATH = VOUCHER_DIRECTORY + "\\audilog_voucher.csv";
    @Autowired
    private AuditLogService auditLogService;

    @GetMapping("/hoadon")
    public List<AuditLog> getAuditLogHoadon(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(required = false) String searchUsername,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate specificDate,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate) {
        try {
            return auditLogService.readAuditLogHoadon(page, size, searchUsername, specificDate, startDate, endDate);
        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();

            return null;
        }
    }

    @GetMapping("/hoadonchitiet")
    public List<AuditLog> getAuditLogHoadonChiTiet(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(required = false) String searchUsername,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate specificDate,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate) {
        try {
            return auditLogService.readAuditLogHoadonChiTiet(page, size, searchUsername, specificDate, startDate,
                    endDate);
        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();

            return null;
        }
    }

    @GetMapping("/khuyenmai")

    public List<AuditLog> getAuditLogKhuyenmai(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(required = false) String searchUsername,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate specificDate,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate) {
        try {
            return auditLogService.readAuditLogKhuyenmai(page, size, searchUsername, specificDate, startDate, endDate);
        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();

            return Collections.emptyList();
        }
    }

    @GetMapping("/sanpham")
    public List<AuditLog> getAuditLogSanPham(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(required = false) String searchUsername,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate specificDate,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate) {
        try {
            return auditLogService.readAuditLogSanPham(page, size, searchUsername, specificDate, startDate, endDate);
        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();

            return null;
        }
    }

    @GetMapping("/chatlieu")
    public List<AuditLog> getAuditLogChatLieu(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(required = false) String searchUsername,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate specificDate,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate) {
        try {
            return auditLogService.readAuditLogChatLieu(page, size, searchUsername, specificDate, startDate, endDate);
        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();

            return null;
        }
    }

    @GetMapping("/size")
    public List<AuditLog> getAuditLogSize(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(required = false) String searchUsername,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate specificDate,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate) {
        try {
            return auditLogService.readAuditLogSize(page, size, searchUsername, specificDate, startDate, endDate);
        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();

            return null;
        }
    }

    @GetMapping("/mausac")
    public List<AuditLog> getAuditLogMauSac(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(required = false) String searchUsername,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate specificDate,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate) {
        try {
            return auditLogService.readAuditLogMauSac(page, size, searchUsername, specificDate, startDate, endDate);
        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();

            return null;
        }
    }

    @GetMapping("/danhmuc")
    public List<AuditLog> getAuditLogDanhMuc(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(required = false) String searchUsername,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate specificDate,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate) {
        try {
            return auditLogService.readAuditLogDanhMuc(page, size, searchUsername, specificDate, startDate, endDate);
        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();

            return null;
        }
    }

    @GetMapping("/thuonghieu")
    public List<AuditLog> getAuditLogThuongHieu(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(required = false) String searchUsername,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate specificDate,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate) {
        try {
            return auditLogService.readAuditLogThuongHieu(page, size, searchUsername, specificDate, startDate, endDate);
        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();

            return null;
        }
    }

    @GetMapping("/xuatxu")
    public List<AuditLog> getAuditLogXuatXu(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(required = false) String searchUsername,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate specificDate,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate) {
        try {
            return auditLogService.readAuditLogXuatXu(page, size, searchUsername, specificDate, startDate, endDate);
        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();

            return null;
        }
    }

    @GetMapping("/kieude")
    public List<AuditLog> getAuditLogKieuDe(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(required = false) String searchUsername,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate specificDate,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate) {
        try {
            return auditLogService.readAuditLogKieuDe(page, size, searchUsername, specificDate, startDate, endDate);
        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();

            return null;
        }
    }

    @GetMapping("/nhanvien")
    public List<AuditLog> getAuditLogNhanVien(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(required = false) String searchUsername,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate specificDate,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate) {
        try {
            return auditLogService.readAuditLogNhanVien(page, size, searchUsername, specificDate, startDate, endDate);
        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();

            return null;
        }
    }

    @GetMapping("/voucher")
    public List<AuditLog> getAuditLogVoucher(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(required = false) String searchUsername,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate specificDate,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate) {
        try {
            return auditLogService.readAuditLogVoucher(page, size, searchUsername, specificDate, startDate, endDate);
        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();
            return null;
        }
    }

    @GetMapping("/khachhang")
    public List<AuditLog> getAuditLogKhachHang(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(required = false) String searchUsername,
            @RequestParam(required = false) @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate specificDate,
            @RequestParam(required = false) @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate endDate) {
        try {
            return auditLogService.readAuditLogKhachHang(page, size, searchUsername, specificDate, startDate, endDate);
        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();

            return null;
        }
    }
    // @GetMapping("/hoadonseach")
    // public List<AuditLog> getAuditLogHoadons(
    // @RequestParam @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate startDate,
    // @RequestParam @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate endDate) {
    // try {
    // LocalDateTime startTime = startDate.atStartOfDay();
    // LocalDateTime endTime = endDate.atTime(LocalTime.MAX);
    // return auditLogService.readAuditLogByTimeRange(AUDIT_LOG_HOADON_FILE_PATH,
    // startTime, endTime);
    // } catch (IOException | CsvValidationException e) {
    // e.printStackTrace();
    // Handle errors, possibly return a ResponseEntity to inform the client about
    // the error
    // return null;
    // }
    // }
    // @GetMapping("/sanphamseach")
    // public List<AuditLog> getAuditLogSanPhams(
    // @RequestParam @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate startDate,
    // @RequestParam @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate endDate) {
    // try {
    // LocalDateTime startTime = startDate.atStartOfDay();
    // LocalDateTime endTime = endDate.atTime(LocalTime.MAX);
    // return auditLogService.readAuditLogByTimeRange(AUDIT_LOG_SANPHAM_FILE_PATH,
    // startTime, endTime);
    // } catch (IOException | CsvValidationException e) {
    // e.printStackTrace();
    // Handle errors, possibly return a ResponseEntity to inform the client about
    // the error
    // return null;
    // }
    // }
    // @GetMapping("/khuyenmaiseach")
    // public List<AuditLog> getAuditLogkhuyenmais(
    // @RequestParam @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate startDate,
    // @RequestParam @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate endDate) {
    // try {
    // LocalDateTime startTime = startDate.atStartOfDay();
    // LocalDateTime endTime = endDate.atTime(LocalTime.MAX);
    // return auditLogService.readAuditLogByTimeRange(AUDIT_LOG_KHUYENMAI_FILE_PATH,
    // startTime, endTime);
    // } catch (IOException | CsvValidationException e) {
    // e.printStackTrace();
    // return null;
    // }
    // }
    // @GetMapping("/chatlieusearch")
    // public List<AuditLog> getAuditLogChatLieus(
    // @RequestParam @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate startDate,
    // @RequestParam @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate endDate) {
    // try {
    // LocalDateTime startTime = startDate.atStartOfDay();
    // LocalDateTime endTime = endDate.atTime(LocalTime.MAX);
    // return auditLogService.readAuditLogByTimeRange(AUDIT_LOG_CHATLIEU_FILE_PATH,
    // startTime, endTime);
    // } catch (IOException | CsvValidationException e) {
    // e.printStackTrace();
    // Handle errors, possibly return a ResponseEntity to inform the client about
    // the error
    // return null;
    // }
    // }
    // @GetMapping("/sizesearch")
    // public List<AuditLog> getAuditLogSizes(
    // @RequestParam @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate startDate,
    // @RequestParam @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate endDate) {
    // try {
    // LocalDateTime startTime = startDate.atStartOfDay();
    // LocalDateTime endTime = endDate.atTime(LocalTime.MAX);
    // return auditLogService.readAuditLogByTimeRange(AUDIT_LOG_SIZE_FILE_PATH,
    // startTime, endTime);
    // } catch (IOException | CsvValidationException e) {
    // e.printStackTrace();
    // Handle errors, possibly return a ResponseEntity to inform the client about
    // the error
    // return null;
    // }
    // }

    // @GetMapping("/mausacsearch")
    // public List<AuditLog> getAuditLogMauSacs(
    // @RequestParam @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate startDate,
    // @RequestParam @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate endDate) {
    // try {
    // LocalDateTime startTime = startDate.atStartOfDay();
    // LocalDateTime endTime = endDate.atTime(LocalTime.MAX);
    // return auditLogService.readAuditLogByTimeRange(AUDIT_LOG_MAUSAC_FILE_PATH,
    // startTime, endTime);
    // } catch (IOException | CsvValidationException e) {
    // e.printStackTrace();
    // Handle errors, possibly return a ResponseEntity to inform the client about
    // the error
    // return null;
    // }
    // }
    // @GetMapping("/danhmucsearch")
    // public List<AuditLog> getAuditLogDanhMucs(
    // @RequestParam @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate startDate,
    // @RequestParam @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate endDate) {
    // try {
    // LocalDateTime startTime = startDate.atStartOfDay();
    // LocalDateTime endTime = endDate.atTime(LocalTime.MAX);
    // return auditLogService.readAuditLogByTimeRange(AUDIT_LOG_DANHMUC_FILE_PATH,
    // startTime, endTime);
    // } catch (IOException | CsvValidationException e) {
    // e.printStackTrace();
    // Handle errors, possibly return a ResponseEntity to inform the client about
    // the error
    // return null;
    // }
    // }

    // @GetMapping("/thuonghieusearch")
    // public List<AuditLog> getAuditLogThuongHieus(
    // @RequestParam @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate startDate,
    // @RequestParam @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate endDate) {
    // try {
    // LocalDateTime startTime = startDate.atStartOfDay();
    // LocalDateTime endTime = endDate.atTime(LocalTime.MAX);
    // return
    // auditLogService.readAuditLogByTimeRange(AUDIT_LOG_THUONGHIEU_FILE_PATH,
    // startTime, endTime);
    // } catch (IOException | CsvValidationException e) {
    // e.printStackTrace();
    // Handle errors, possibly return a ResponseEntity to inform the client about
    // the error
    // return null;
    // }
    // }

    // @GetMapping("/xuatxusearch")
    // public List<AuditLog> getAuditLogXuatXus(
    // @RequestParam @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate startDate,
    // @RequestParam @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate endDate) {
    // try {
    // LocalDateTime startTime = startDate.atStartOfDay();
    // LocalDateTime endTime = endDate.atTime(LocalTime.MAX);
    // return auditLogService.readAuditLogByTimeRange(AUDIT_LOG_XUATXU_FILE_PATH,
    // startTime, endTime);
    // } catch (IOException | CsvValidationException e) {
    // e.printStackTrace();
    // Handle errors, possibly return a ResponseEntity to inform the client about
    // the error
    // return null;
    // }
    // }

    // @GetMapping("/kieudesearch")
    // public List<AuditLog> getAuditLogKieuDes(
    // @RequestParam @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate startDate,
    // @RequestParam @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate endDate) {
    // try {
    // LocalDateTime startTime = startDate.atStartOfDay();
    // LocalDateTime endTime = endDate.atTime(LocalTime.MAX);
    // return auditLogService.readAuditLogByTimeRange(AUDIT_LOG_KIEUDE_FILE_PATH,
    // startTime, endTime);
    // } catch (IOException | CsvValidationException e) {
    // e.printStackTrace();
    // Handle errors, possibly return a ResponseEntity to inform the client about
    // the error
    // return null;
    // }
    // }
    // @GetMapping("/nhanviensearch")
    // public List<AuditLog> getAuditLogNhanViens(
    // @RequestParam @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate startDate,
    // @RequestParam @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate endDate) {
    // try {
    // LocalDateTime startTime = startDate.atStartOfDay();
    // LocalDateTime endTime = endDate.atTime(LocalTime.MAX);
    // return auditLogService.readAuditLogByTimeRange(AUDIT_LOG_NHANVIEN_FILE_PATH,
    // startTime, endTime);
    // } catch (IOException | CsvValidationException e) {
    // e.printStackTrace();
    // Handle errors, possibly return a ResponseEntity to inform the client about
    // the error
    // return null;
    // }
    // }

    // @GetMapping("/khachhangsearch")
    // public List<AuditLog> getAuditLogKhachHangs(
    // @RequestParam @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate startDate,
    // @RequestParam @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate endDate) {
    // try {
    // LocalDateTime startTime = startDate.atStartOfDay();
    // LocalDateTime endTime = endDate.atTime(LocalTime.MAX);
    // return auditLogService.readAuditLogByTimeRange(AUDIT_LOG_KHACHHANG_FILE_PATH,
    // startTime, endTime);
    // } catch (IOException | CsvValidationException e) {
    // e.printStackTrace();
    // Handle errors, possibly return a ResponseEntity to inform the client about
    // the error
    // return null;
    // }
    // }

    // @GetMapping("/vouchersearch")
    // public List<AuditLog> getAuditLogVouchers(
    // @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
    // @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate) {
    // try {
    // LocalDateTime startTime = startDate.atStartOfDay();
    // LocalDateTime endTime = endDate.atTime(LocalTime.MAX);
    // return auditLogService.readAuditLogByTimeRange(AUDIT_LOG_VOUCHER_FILE_PATH,
    // startTime, endTime);
    // } catch (IOException | CsvValidationException e) {
    // e.printStackTrace();
    // Handle errors, possibly return a ResponseEntity to inform the client about
    // the error
    // return null;
    // }
    // }

    // @GetMapping("/auditlogvoucherbydate")
    // public List<AuditLog> getAuditLogByDate(
    // @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate searchDate) {
    // try {
    // return auditLogService.readAuditLogByDate(AUDIT_LOG_VOUCHER_FILE_PATH,
    // searchDate);
    // } catch (IOException | CsvValidationException e) {
    // e.printStackTrace();
    // Handle errors, possibly return a ResponseEntity to inform the client about
    // the error
    // return null;
    // }
    // }
    // @GetMapping("/auditlogsanphambydate")
    // public List<AuditLog> getAuditLogSanPhamByDate(
    // @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate searchDate) {
    // try {
    // return auditLogService.readAuditLogByDate(AUDIT_LOG_SANPHAM_FILE_PATH,
    // searchDate);
    // } catch (IOException | CsvValidationException e) {
    // e.printStackTrace();
    // Handle errors, possibly return a ResponseEntity to inform the client about
    // the error
    // return null;
    // }
    // }
    // @GetMapping("/auditloghoadonbydate")
    // public List<AuditLog> getAuditLogHoaDonByDate(
    // @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate searchDate) {
    // try {
    // return auditLogService.readAuditLogByDate(AUDIT_LOG_HOADON_FILE_PATH,
    // searchDate);
    // } catch (IOException | CsvValidationException e) {
    // e.printStackTrace();
    // Handle errors, possibly return a ResponseEntity to inform the client about
    // the error
    // return null;
    // }
    // }

    // @GetMapping("/auditlogkhuyenmaibydate")
    // public List<AuditLog> getAuditLogKhuyenMaiByDate(
    // @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate searchDate) {
    // try {
    // return auditLogService.readAuditLogByDate(AUDIT_LOG_KHUYENMAI_FILE_PATH,
    // searchDate);
    // } catch (IOException | CsvValidationException e) {
    // e.printStackTrace();
    // Handle errors, possibly return a ResponseEntity to inform the client about
    // the error
    // return null;
    // }
    // }
    // @GetMapping("/auditlogsizebydate")
    // public List<AuditLog> getAuditLogSizeByDate(
    // @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate searchDate) {
    // try {
    // return auditLogService.readAuditLogByDate(AUDIT_LOG_SIZE_FILE_PATH,
    // searchDate);
    // } catch (IOException | CsvValidationException e) {
    // e.printStackTrace();
    // Handle errors, possibly return a ResponseEntity to inform the client about
    // the error
    // return null;
    // }
    // }

    // @GetMapping("/auditlogchatlieubydate")
    // public List<AuditLog> getAuditLogChatLieuByDate(
    // @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate searchDate) {
    // try {
    // return auditLogService.readAuditLogByDate(AUDIT_LOG_CHATLIEU_FILE_PATH,
    // searchDate);
    // } catch (IOException | CsvValidationException e) {
    // e.printStackTrace();
    // Handle errors, possibly return a ResponseEntity to inform the client about
    // the error
    // return null;
    // }
    // }
    // @GetMapping("/auditlogmausacbydate")
    // public List<AuditLog> getAuditLogMauSacByDate(
    // @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate searchDate) {
    // try {
    // return auditLogService.readAuditLogByDate(AUDIT_LOG_MAUSAC_FILE_PATH,
    // searchDate);
    // } catch (IOException | CsvValidationException e) {
    // e.printStackTrace();
    // Handle errors, possibly return a ResponseEntity to inform the client about
    // the error
    // return null;
    // }
    // }
    // @GetMapping("/auditlogdanhmucbydate")
    // public List<AuditLog> getAuditLogDanhMucByDate(
    // @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate searchDate) {
    // try {
    // return auditLogService.readAuditLogByDate(AUDIT_LOG_DANHMUC_FILE_PATH,
    // searchDate);
    // } catch (IOException | CsvValidationException e) {
    // e.printStackTrace();
    // Handle errors, possibly return a ResponseEntity to inform the client about
    // the error
    // return null;
    // }
    // }
    // @GetMapping("/auditlogthuonghieubydate")
    // public List<AuditLog> getAuditLogThuongHieuByDate(
    // @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate searchDate) {
    // try {
    // return auditLogService.readAuditLogByDate(AUDIT_LOG_THUONGHIEU_FILE_PATH,
    // searchDate);
    // } catch (IOException | CsvValidationException e) {
    // e.printStackTrace();
    // Handle errors, possibly return a ResponseEntity to inform the client about
    // the error
    // return null;
    // }
    // }
    // @GetMapping("/auditlogxuatxubydate")
    // public List<AuditLog> getAuditLogXuatXuByDate(
    // @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate searchDate) {
    // try {
    // return auditLogService.readAuditLogByDate(AUDIT_LOG_XUATXU_FILE_PATH,
    // searchDate);
    // } catch (IOException | CsvValidationException e) {
    // e.printStackTrace();
    // Handle errors, possibly return a ResponseEntity to inform the client about
    // the error
    // return null;
    // }
    // }
    // @GetMapping("/auditlogkieudebydate")
    // public List<AuditLog> getAuditLogKieuDeByDate(
    // @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate searchDate) {
    // try {
    // return auditLogService.readAuditLogByDate(AUDIT_LOG_KIEUDE_FILE_PATH,
    // searchDate);
    // } catch (IOException | CsvValidationException e) {
    // e.printStackTrace();
    // Handle errors, possibly return a ResponseEntity to inform the client about
    // the error
    // return null;
    // }
    // }
    // @GetMapping("/auditlognhanvienbydate")
    // public List<AuditLog> getAuditLogNhanVienByDate(
    // @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate searchDate) {
    // try {
    // return auditLogService.readAuditLogByDate(AUDIT_LOG_NHANVIEN_FILE_PATH,
    // searchDate);
    // } catch (IOException | CsvValidationException e) {
    // e.printStackTrace();
    // Handle errors, possibly return a ResponseEntity to inform the client about
    // the error
    // return null;
    // }
    // }
    // @GetMapping("/auditlogkhachhangbydate")
    // public List<AuditLog> getAuditLogKhachHangByDate(
    // @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate searchDate) {
    // try {
    // return auditLogService.readAuditLogByDate(AUDIT_LOG_KHACHHANG_FILE_PATH,
    // searchDate);
    // } catch (IOException | CsvValidationException e) {
    // e.printStackTrace();
    // Handle errors, possibly return a ResponseEntity to inform the client about
    // the error
    // return null;
    // }
    // }
    // @GetMapping("/hoadonchitietsearch")
    // public List<AuditLog> getAuditLogHoaDonChiTiet(
    // @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
    // @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate) {
    // try {
    // LocalDateTime startTime = startDate.atStartOfDay();
    // LocalDateTime endTime = endDate.atTime(LocalTime.MAX);
    // return
    // auditLogService.readAuditLogByTimeRange(AUDIT_LOG_HOADON_CHI_TIET_FILE_PATH,
    // startTime, endTime);
    // } catch (IOException | CsvValidationException e) {
    // e.printStackTrace();
    // Handle errors, possibly return a ResponseEntity to inform the client about
    // the error
    // return null;
    // }
    // }

    // @GetMapping("/auditlogbydatehoadonchitiet")
    // public List<AuditLog> getAuditLogByDateHoaDonChiTiet(
    // @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate searchDate) {
    // try {
    // return
    // auditLogService.readAuditLogByDate(AUDIT_LOG_HOADON_CHI_TIET_FILE_PATH,
    // searchDate);
    // } catch (IOException | CsvValidationException e) {
    // e.printStackTrace();
    // Handle errors, possibly return a ResponseEntity to inform the client about
    // the error
    // return null;
    // }
    // }
}
