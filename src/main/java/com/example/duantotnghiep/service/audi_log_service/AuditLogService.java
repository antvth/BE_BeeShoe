package com.example.duantotnghiep.service.audi_log_service;

import com.example.duantotnghiep.entity.AuditLog;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvException;
import com.opencsv.exceptions.CsvValidationException;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class AuditLogService {
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

    public AuditLogService() {
        createDirectories();
        createAuditLogFileIfNotExists(AUDIT_LOG_HOADON_FILE_PATH);
        createAuditLogFileIfNotExists(AUDIT_LOG_KHUYENMAI_FILE_PATH);
        createAuditLogFileIfNotExists(AUDIT_LOG_SANPHAM_FILE_PATH);
        createAuditLogFileIfNotExists(AUDIT_LOG_SIZE_FILE_PATH);
        createAuditLogFileIfNotExists(AUDIT_LOG_CHATLIEU_FILE_PATH);
        createAuditLogFileIfNotExists(AUDIT_LOG_MAUSAC_FILE_PATH);
        createAuditLogFileIfNotExists(AUDIT_LOG_DANHMUC_FILE_PATH);
        createAuditLogFileIfNotExists(AUDIT_LOG_THUONGHIEU_FILE_PATH);
        createAuditLogFileIfNotExists(AUDIT_LOG_XUATXU_FILE_PATH);
        createAuditLogFileIfNotExists(AUDIT_LOG_KIEUDE_FILE_PATH);
        createAuditLogFileIfNotExists(AUDIT_LOG_NHANVIEN_FILE_PATH);
        createAuditLogFileIfNotExists(AUDIT_LOG_KHACHHANG_FILE_PATH);
        createAuditLogFileIfNotExists(AUDIT_LOG_VOUCHER_FILE_PATH);
        createAuditLogFileIfNotExists(AUDIT_LOG_HOADON_CHI_TIET_FILE_PATH);
    }

    private void createDirectories() {
        try {
            Files.createDirectories(Paths.get(BASE_DIRECTORY));
            Files.createDirectories(Paths.get(ADMIN_DIRECTORY));
            Files.createDirectories(Paths.get(CUSTOMER_DIRECTORY));
            Files.createDirectories(Paths.get(HOADON_DIRECTORY));
            Files.createDirectories(Paths.get(QUAN_LY_SAN_PHAM_DIRECTORY));
            Files.createDirectories(Paths.get(SANPHAM_DIRECTORY));
            Files.createDirectories(Paths.get(SIZE_DIRECTORY));
            Files.createDirectories(Paths.get(CHATLIEU_DIRECTORY));
            Files.createDirectories(Paths.get(MAUSAC_DIRECTORY));
            Files.createDirectories(Paths.get(DANHMUC_DIRECTORY));
            Files.createDirectories(Paths.get(THUONGHIEU_DIRECTORY));
            Files.createDirectories(Paths.get(XUATXU_DIRECTORY));
            Files.createDirectories(Paths.get(KIEUDE_DIRECTORY));
            Files.createDirectories(Paths.get(QUAN_LY_TAI_KHOAN_DIRECTORY));
            Files.createDirectories(Paths.get(NHANVIEN_DIRECTORY));
            Files.createDirectories(Paths.get(KHACHHANG_DIRECTORY));
            Files.createDirectories(Paths.get(VOUCHER_DIRECTORY));
            Files.createDirectories(Paths.get(KHUYENMAI_DIRECTORY));
            Files.createDirectories(Paths.get(HOADON_CHI_TIET_DIRECTORY));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createAuditLogFileIfNotExists(String filePath) {
        try {
            File auditLogFile = new File(filePath);
            if (!auditLogFile.exists()) {
                Files.createFile(Paths.get(filePath));
                writeAuditLogHeader(filePath);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // New method to filter audit logs based on a time range
    // public List<AuditLog> readAuditLogByTimeRange(String filePath, LocalDateTime
    // startTime, LocalDateTime endTime)
    // throws IOException, CsvValidationException {
    // List<AuditLog> auditLogList = readAuditLog(filePath);
    //
    // return auditLogList.stream()
    // .filter(log -> log.getTimestamp().isAfter(startTime) &&
    // log.getTimestamp().isBefore(endTime))
    // .collect(Collectors.toList());
    // }
    // public List<AuditLog> readAuditLogByDate(String filePath, LocalDate
    // ngayTimKiem) throws IOException, CsvValidationException {
    // List<AuditLog> danhSachAuditLog = readAuditLog(filePath);
    //
    // // Xác định thời gian bắt đầu và kết thúc của ngàyTimKiem
    // LocalDateTime gioBatDau = ngayTimKiem.atStartOfDay();
    // LocalDateTime gioKetThuc = LocalDateTime.of(ngayTimKiem, LocalTime.MAX);
    //
    // return danhSachAuditLog.stream()
    // .filter(log -> log.getTimestamp().isAfter(gioBatDau) &&
    // log.getTimestamp().isBefore(gioKetThuc))
    // .collect(Collectors.toList());
    // }
    private void writeAuditLogHeader(String filePath) throws IOException {
        try (CSVWriter writer = new CSVWriter(
                new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filePath, true), "UTF-8")))) {
            String[] header = { "Action", "Username", "Email", "Id", "Ma", "Ten", "TenKhach", "Loai Thanh Toan",
                    "Timestamp" };
            writer.writeNext(header);
        }
    }

    public List<AuditLog> readAuditLog(String filePath, int page, int size, String searchUsername,
            LocalDate specificDate, LocalDate startDate, LocalDate endDate) throws IOException, CsvValidationException {
        List<AuditLog> auditLogList = new ArrayList<>();
        try (CSVReader reader = new CSVReader(
                new BufferedReader(new InputStreamReader(new FileInputStream(filePath), StandardCharsets.UTF_8)))) {
            // Skip headers plus the rows that are before the desired start index
            for (int i = 0; i < page * size + 1; i++) {
                reader.readNext();
            }

            // Read the remaining rows and proceed with processing
            List<String[]> lines = new ArrayList<>();
            String[] nextLine;
            while ((nextLine = reader.readNext()) != null) {
                lines.add(nextLine);
            }

            lines.sort((line1, line2) -> {
                try {
                    LocalDateTime timestamp1 = LocalDateTime.parse(line1[8]);
                    LocalDateTime timestamp2 = LocalDateTime.parse(line2[8]);
                    return timestamp2.compareTo(timestamp1);
                } catch (DateTimeParseException e) {
                    // Xử lý lỗi, có thể in ra log để theo dõi giá trị thời gian không hợp lệ
                    e.printStackTrace();
                    return 0;
                }
            });

            int startIndex = 0;
            int endIndex = Math.min(lines.size(), startIndex + size);

            List<String[]> paginatedLines = lines.subList(startIndex, endIndex);

            for (String[] line : paginatedLines) {
                String username = line[1];
                try {
                    LocalDateTime timestamp = LocalDateTime.parse(line[8]);

                    // Check if the current record matches the search conditions
                    if ((searchUsername == null || username.equals(searchUsername)) &&
                            (specificDate == null || timestamp.toLocalDate().equals(specificDate)) &&
                            (startDate == null || timestamp.toLocalDate().isEqual(startDate)
                                    || timestamp.toLocalDate().isAfter(startDate))
                            &&
                            (endDate == null || timestamp.toLocalDate().isEqual(endDate)
                                    || timestamp.toLocalDate().isBefore(endDate))) {
                        String action = line[0];
                        String email = line[2];
                        String id = line[3];
                        String ma = line[4];
                        String ten = line[5];
                        String tenKhach = line[6];
                        String LoaiThanhToan = line[7];
                        auditLogList.add(
                                new AuditLog(action, username, email, id, ma, ten, tenKhach, LoaiThanhToan, timestamp));
                    }
                } catch (DateTimeParseException e) {
                    // Xử lý lỗi khi chuyển đổi timestamp không hợp lệ
                    e.printStackTrace();
                }
            }
        } catch (CsvException e) {
            e.printStackTrace();
        }
        return auditLogList;
    }

    public List<AuditLog> readAuditLogKhuyenmai(int page, int size, String searchUsername, LocalDate specificDate,
            LocalDate startDate, LocalDate endDate) throws IOException, CsvValidationException {
        return readAuditLog(AUDIT_LOG_KHUYENMAI_FILE_PATH, page, size, searchUsername, specificDate, startDate,
                endDate);
    }

    public List<AuditLog> readAuditLogHoadon(int page, int size, String searchUsername, LocalDate specificDate,
            LocalDate startDate, LocalDate endDate) throws IOException, CsvValidationException {
        return readAuditLog(AUDIT_LOG_HOADON_FILE_PATH, page, size, searchUsername, specificDate, startDate,
                endDate);
    }

    public List<AuditLog> readAuditLogHoadonChiTiet(int page, int size, String searchUsername, LocalDate specificDate,
            LocalDate startDate, LocalDate endDate) throws IOException, CsvValidationException {
        return readAuditLog(AUDIT_LOG_HOADON_CHI_TIET_FILE_PATH, page, size, searchUsername, specificDate, startDate,
                endDate);
    }

    public List<AuditLog> readAuditLogChatLieu(int page, int size, String searchUsername, LocalDate specificDate,
            LocalDate startDate, LocalDate endDate) throws IOException, CsvValidationException {
        return readAuditLog(AUDIT_LOG_CHATLIEU_FILE_PATH, page, size, searchUsername, specificDate, startDate,
                endDate);
    }

    public List<AuditLog> readAuditLogSanPham(int page, int size, String searchUsername, LocalDate specificDate,
            LocalDate startDate, LocalDate endDate) throws IOException, CsvValidationException {
        return readAuditLog(AUDIT_LOG_SANPHAM_FILE_PATH, page, size, searchUsername, specificDate, startDate,
                endDate);
    }

    public List<AuditLog> readAuditLogSize(int page, int size, String searchUsername, LocalDate specificDate,
            LocalDate startDate, LocalDate endDate) throws IOException, CsvValidationException {
        return readAuditLog(AUDIT_LOG_SIZE_FILE_PATH, page, size, searchUsername, specificDate, startDate,
                endDate);
    }

    public List<AuditLog> readAuditLogMauSac(int page, int size, String searchUsername, LocalDate specificDate,
            LocalDate startDate, LocalDate endDate) throws IOException, CsvValidationException {
        return readAuditLog(AUDIT_LOG_MAUSAC_FILE_PATH, page, size, searchUsername, specificDate, startDate,
                endDate);
    }

    public List<AuditLog> readAuditLogDanhMuc(int page, int size, String searchUsername, LocalDate specificDate,
            LocalDate startDate, LocalDate endDate) throws IOException, CsvValidationException {
        return readAuditLog(AUDIT_LOG_DANHMUC_FILE_PATH, page, size, searchUsername, specificDate, startDate,
                endDate);
    }

    public List<AuditLog> readAuditLogThuongHieu(int page, int size, String searchUsername, LocalDate specificDate,
            LocalDate startDate, LocalDate endDate) throws IOException, CsvValidationException {
        return readAuditLog(AUDIT_LOG_THUONGHIEU_FILE_PATH, page, size, searchUsername, specificDate, startDate,
                endDate);
    }

    public List<AuditLog> readAuditLogXuatXu(int page, int size, String searchUsername, LocalDate specificDate,
            LocalDate startDate, LocalDate endDate) throws IOException, CsvValidationException {
        return readAuditLog(AUDIT_LOG_XUATXU_FILE_PATH, page, size, searchUsername, specificDate, startDate,
                endDate);
    }

    public List<AuditLog> readAuditLogKieuDe(int page, int size, String searchUsername, LocalDate specificDate,
            LocalDate startDate, LocalDate endDate) throws IOException, CsvValidationException {
        return readAuditLog(AUDIT_LOG_KIEUDE_FILE_PATH, page, size, searchUsername, specificDate, startDate,
                endDate);
    }

    public List<AuditLog> readAuditLogNhanVien(int page, int size, String searchUsername, LocalDate specificDate,
            LocalDate startDate, LocalDate endDate) throws IOException, CsvValidationException {
        return readAuditLog(AUDIT_LOG_NHANVIEN_FILE_PATH, page, size, searchUsername, specificDate, startDate,
                endDate);
    }

    public List<AuditLog> readAuditLogKhachHang(int page, int size, String searchUsername, LocalDate specificDate,
            LocalDate startDate, LocalDate endDate) throws IOException, CsvValidationException {
        return readAuditLog(AUDIT_LOG_KHACHHANG_FILE_PATH, page, size, searchUsername, specificDate, startDate,
                endDate);
    }

    public List<AuditLog> readAuditLogVoucher(int page, int size, String searchUsername, LocalDate specificDate,
            LocalDate startDate, LocalDate endDate) throws IOException, CsvValidationException {
        return readAuditLog(AUDIT_LOG_VOUCHER_FILE_PATH, page, size, searchUsername, specificDate, startDate,
                endDate);
    }

    public void writeAuditLogHoadon(String action, String username, String email, String Id, String Ma, String Ten,
            String TenKhach, String Loai)
            throws IOException, CsvValidationException {
        AuditLog auditLog = new AuditLog(action, username, email, Id, Ma, Ten, TenKhach, Loai, LocalDateTime.now());
        writeAuditLog(auditLog, AUDIT_LOG_HOADON_FILE_PATH);
    }

    public void writeAuditLogHoadonChiTiet(String action, String username, String email, String Id, String Ma,
            String Ten, String TenKhach, String Loai)
            throws IOException, CsvValidationException {
        AuditLog auditLog = new AuditLog(action, username, email, Id, Ma, Ten, TenKhach, Loai, LocalDateTime.now());
        writeAuditLog(auditLog, AUDIT_LOG_HOADON_CHI_TIET_FILE_PATH);
    }

    public void writeAuditLogSanPham(String action, String username, String email, String Id, String Ma, String Ten,
            String TenKhach, String Loai)
            throws IOException, CsvValidationException {
        AuditLog auditLog = new AuditLog(action, username, email, Id, Ma, Ten, TenKhach, Loai, LocalDateTime.now());
        writeAuditLog(auditLog, AUDIT_LOG_SANPHAM_FILE_PATH);
    }

    public void writeAuditLogSize(String action, String username, String email, String Id, String Ma, String Ten,
            String TenKhach, String Loai)
            throws IOException, CsvValidationException {
        AuditLog auditLog = new AuditLog(action, username, email, Id, Ma, Ten, TenKhach, Loai, LocalDateTime.now());
        writeAuditLog(auditLog, AUDIT_LOG_SIZE_FILE_PATH);
    }

    public void writeAuditLogChatlieu(String action, String username, String email, String Id, String Ma, String Ten,
            String TenKhach, String Loai)
            throws IOException, CsvValidationException {
        AuditLog auditLog = new AuditLog(action, username, email, Id, Ma, Ten, TenKhach, Loai, LocalDateTime.now());
        writeAuditLog(auditLog, AUDIT_LOG_CHATLIEU_FILE_PATH);
    }

    public void writeAuditLogMausac(String action, String username, String email, String Id, String Ma, String Ten,
            String TenKhach, String Loai)
            throws IOException, CsvValidationException {
        AuditLog auditLog = new AuditLog(action, username, email, Id, Ma, Ten, TenKhach, Loai, LocalDateTime.now());
        writeAuditLog(auditLog, AUDIT_LOG_MAUSAC_FILE_PATH);
    }

    public void writeAuditLogDanhmuc(String action, String username, String email, String Id, String Ma, String Ten,
            String TenKhach, String Loai)
            throws IOException, CsvValidationException {
        AuditLog auditLog = new AuditLog(action, username, email, Id, Ma, Ten, TenKhach, Loai, LocalDateTime.now());
        writeAuditLog(auditLog, AUDIT_LOG_DANHMUC_FILE_PATH);
    }

    public void writeAuditLogThuonghieu(String action, String username, String email, String Id, String Ma, String Ten,
            String TenKhach, String Loai)
            throws IOException, CsvValidationException {
        AuditLog auditLog = new AuditLog(action, username, email, Id, Ma, Ten, TenKhach, Loai, LocalDateTime.now());
        writeAuditLog(auditLog, AUDIT_LOG_THUONGHIEU_FILE_PATH);
    }

    public void writeAuditLogXuatxu(String action, String username, String email, String Id, String Ma, String Ten,
            String TenKhach, String Loai)
            throws IOException, CsvValidationException {
        AuditLog auditLog = new AuditLog(action, username, email, Id, Ma, Ten, TenKhach, Loai, LocalDateTime.now());
        writeAuditLog(auditLog, AUDIT_LOG_XUATXU_FILE_PATH);
    }

    public void writeAuditLogKieude(String action, String username, String email, String Id, String Ma, String Ten,
            String TenKhach, String Loai)
            throws IOException, CsvValidationException {
        AuditLog auditLog = new AuditLog(action, username, email, Id, Ma, Ten, TenKhach, Loai, LocalDateTime.now());
        writeAuditLog(auditLog, AUDIT_LOG_KIEUDE_FILE_PATH);
    }

    public void writeAuditLogNhanvien(String action, String username, String email, String Id, String Ma, String Ten,
            String TenKhach, String Loai)
            throws IOException, CsvValidationException {
        AuditLog auditLog = new AuditLog(action, username, email, Id, Ma, Ten, TenKhach, Loai, LocalDateTime.now());
        writeAuditLog(auditLog, AUDIT_LOG_NHANVIEN_FILE_PATH);
    }

    public void writeAuditLogKhachhang(String action, String username, String email, String Id, String Ma, String Ten,
            String TenKhach, String Loai)
            throws IOException, CsvValidationException {
        AuditLog auditLog = new AuditLog(action, username, email, Id, Ma, Ten, TenKhach, Loai, LocalDateTime.now());
        writeAuditLog(auditLog, AUDIT_LOG_KHACHHANG_FILE_PATH);
    }

    public void writeAuditLogVoucher(String action, String username, String email, String Id, String Ma, String Ten,
            String TenKhach, String Loai)
            throws IOException, CsvValidationException {
        AuditLog auditLog = new AuditLog(action, username, email, Id, Ma, Ten, TenKhach, Loai, LocalDateTime.now());
        writeAuditLog(auditLog, AUDIT_LOG_VOUCHER_FILE_PATH);
    }

    public void writeAuditLogKhuyenmai(String action, String username, String email, String Id, String Ma, String Ten,
            String TenKhach, String Loai)
            throws IOException, CsvValidationException {
        AuditLog auditLog = new AuditLog(action, username, email, Id, Ma, Ten, TenKhach, Loai, LocalDateTime.now());
        writeAuditLog(auditLog, AUDIT_LOG_KHUYENMAI_FILE_PATH);
    }

    public void writeAuditLog(AuditLog auditLog, String filePath) {
        try (CSVWriter writer = new CSVWriter(
                new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filePath, true), "UTF-8")))) {
            String[] logData = {
                    auditLog.getAction(),
                    auditLog.getUsername(),
                    auditLog.getPassword(),
                    auditLog.getId(),
                    auditLog.getMa(),
                    auditLog.getTen(),
                    auditLog.getTenKhachHang(),
                    auditLog.getLoaiThanhToan(),
                    auditLog.getTimestamp().toString()
            };

            writer.writeNext(logData);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}