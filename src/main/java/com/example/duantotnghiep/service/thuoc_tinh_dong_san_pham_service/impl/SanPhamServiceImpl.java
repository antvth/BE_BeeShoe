package com.example.duantotnghiep.service.thuoc_tinh_dong_san_pham_service.impl;

import com.example.duantotnghiep.entity.*;
import com.example.duantotnghiep.mapper.ChiTietSanPhamCustom;
import com.example.duantotnghiep.repository.*;
import com.example.duantotnghiep.request.ProductRequest;
import com.example.duantotnghiep.response.*;
import com.example.duantotnghiep.service.audi_log_service.AuditLogService;
import com.example.duantotnghiep.service.thuoc_tinh_dong_san_pham_service.SanPhamService;
import com.example.duantotnghiep.util.FormatNumber;
import com.opencsv.exceptions.CsvValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.*;

@Service
public class SanPhamServiceImpl implements SanPhamService {

    @Autowired
    private SanPhamRepository sanPhamRepository;

    @Autowired
    private ThuongHieuRepository thuongHieuRepository;

    @Autowired
    private DanhMucRepository danhMucRepository;

    @Autowired
    private XuatSuRepository xuatSuRepository;

    @Autowired
    private KieuDeRepository kieuDeRepository;

    @Autowired
    private AuditLogService auditLogService;

    @Autowired
    private TaiKhoanRepository taiKhoanRepository;

    @Autowired
    private SpGiamGiaRepository spGiamGiaRepository;

    @Override
    public List<SanPhamResponse> getNewProduct() {
        return sanPhamRepository.getNewProduct();
    }

    @Override
    public List<SanPhamResponse> getBestSellingProducts() {
        return sanPhamRepository.getBestSellingProducts();
    }

    @Override
    public List<ProductResponse> findByThuongHieu(Integer pageNumber, Integer pageSize, String value) {
        List<ProductResponse> resultList = new ArrayList<>();
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Object[]> pageList =  sanPhamRepository.findByThuongHieu(pageable, value);
        for (Object[] result : pageList.getContent()) {
            UUID idSp = (UUID) result[0];
            String imgage = (String) result[1];
            String maSp = (String) result[2];
            String tenSanPham = (String) result[3];
            BigDecimal giaBan = (BigDecimal) result[4];
            Date ngayTao = (Date) result[5];
            Integer trangThai = (Integer) result[6];
            BigDecimal giaGiam = new BigDecimal(getGiaGiamCuoiCung(idSp));

            ProductResponse productResponse = new ProductResponse(idSp, imgage, maSp, tenSanPham, giaBan, ngayTao, trangThai, giaBan.subtract(giaGiam));
            resultList.add(productResponse);
        }
        return resultList;
    }

    @Override
    public List<ProductResponse> findByKieuDe(Integer pageNumber, Integer pageSize, String value) {
        List<ProductResponse> resultList = new ArrayList<>();
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Object[]> pageList =  sanPhamRepository.findByKieuDe(pageable, value);
        for (Object[] result : pageList.getContent()) {
            UUID idSp = (UUID) result[0];
            String imgage = (String) result[1];
            String maSp = (String) result[2];
            String tenSanPham = (String) result[3];
            BigDecimal giaBan = (BigDecimal) result[4];
            Date ngayTao = (Date) result[5];
            Integer trangThai = (Integer) result[6];
            BigDecimal giaGiam = new BigDecimal(getGiaGiamCuoiCung(idSp));

            ProductResponse productResponse = new ProductResponse(idSp, imgage, maSp, tenSanPham, giaBan, ngayTao, trangThai, giaBan.subtract(giaGiam));
            resultList.add(productResponse);
        }
        return resultList;
    }

    @Override
    public List<ProductResponse> findByXuatXu(Integer pageNumber, Integer pageSize, String value) {
        List<ProductResponse> resultList = new ArrayList<>();
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Object[]> pageList = sanPhamRepository.findByXuatXu(pageable, value);
        for (Object[] result : pageList.getContent()) {
            UUID idSp = (UUID) result[0];
            String imgage = (String) result[1];
            String maSp = (String) result[2];
            String tenSanPham = (String) result[3];
            BigDecimal giaBan = (BigDecimal) result[4];
            Date ngayTao = (Date) result[5];
            Integer trangThai = (Integer) result[6];
            BigDecimal giaGiam = new BigDecimal(getGiaGiamCuoiCung(idSp));

            ProductResponse productResponse = new ProductResponse(idSp, imgage, maSp, tenSanPham, giaBan, ngayTao, trangThai, giaBan.subtract(giaGiam));
            resultList.add(productResponse);
        }
        return resultList;
    }

    @Override
    public List<ProductResponse> findByDanhMuc(Integer pageNumber, Integer pageSize, String value) {
        List<ProductResponse> resultList = new ArrayList<>();
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Object[]> pageList = sanPhamRepository.findByDanhMuc(pageable, value);
        for (Object[] result : pageList.getContent()) {
            UUID idSp = (UUID) result[0];
            String imgage = (String) result[1];
            String maSp = (String) result[2];
            String tenSanPham = (String) result[3];
            BigDecimal giaBan = (BigDecimal) result[4];
            Date ngayTao = (Date) result[5];
            Integer trangThai = (Integer) result[6];
            BigDecimal giaGiam = new BigDecimal(getGiaGiamCuoiCung(idSp));

            ProductResponse productResponse = new ProductResponse(idSp, imgage, maSp, tenSanPham, giaBan, ngayTao, trangThai, giaBan.subtract(giaGiam));
            resultList.add(productResponse);
        }
        return resultList;
    }

    @Override
    public List<ProductResponse> findByNameOrCode(Integer pageNumber, Integer pageSize, String value) {
        List<ProductResponse> resultList = new ArrayList<>();
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Object[]> pageList = sanPhamRepository.findByNameOrCode(pageable, value);
        for (Object[] result : pageList.getContent()) {
            UUID idSp = (UUID) result[0];
            String imgage = (String) result[1];
            String maSp = (String) result[2];
            String tenSanPham = (String) result[3];
            BigDecimal giaBan = (BigDecimal) result[4];
            Date ngayTao = (Date) result[5];
            Integer trangThai = (Integer) result[6];
            BigDecimal giaGiam = new BigDecimal(getGiaGiamCuoiCung(idSp));

            ProductResponse productResponse = new ProductResponse(idSp, imgage, maSp, tenSanPham, giaBan, ngayTao, trangThai, giaBan.subtract(giaGiam));
            resultList.add(productResponse);
        }
        return resultList;
    }

    @Override
    public List<ProductResponse> getHoaDonByFilter(Integer pageNumber, Integer pageSize) {
        List<ProductResponse> resultList = new ArrayList<>();
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Object[]> pageList = sanPhamRepository.getAllSanPham(pageable);
        for (Object[] result : pageList.getContent()) {
            UUID idSp = (UUID) result[0];
            String imgage = (String) result[1];
            String maSp = (String) result[2];
            String tenSanPham = (String) result[3];
            BigDecimal giaBan = (BigDecimal) result[4];
            Date ngayTao = (Date) result[5];
            Integer trangThai = (Integer) result[6];
            BigDecimal giaGiam = new BigDecimal(getGiaGiamCuoiCung(idSp));

            ProductResponse productResponse = new ProductResponse(idSp, imgage, maSp, tenSanPham, giaBan, ngayTao, trangThai, giaBan.subtract(giaGiam));
            resultList.add(productResponse);
        }
        return resultList;
    }

    public Long getGiaGiamCuoiCung(UUID id) {
        long tongTienGiam = 0L;
        List<SpGiamGia> spGiamGiaList = spGiamGiaRepository.findBySanPham_Id(id);

        for (SpGiamGia spGiamGia : spGiamGiaList) {
            // Cập nhật tổng tiền giảm đúng cách, không khai báo lại biến tongTienGiam
            if (spGiamGia.getGiaGiam() == null) {
                return tongTienGiam;
            }
            if (spGiamGia.getGiaGiam() != null) {
                tongTienGiam += spGiamGia.getGiaGiam().longValue();
            }

        }

        return tongTienGiam;
    }

    public BigDecimal tienGiam(UUID id) {
        Long tienGiam = getGiaGiamCuoiCung(id);
        return new BigDecimal(tienGiam);
    }

    @Override
    public List<ProductResponse> findByStatus(Integer pageNumber, Integer pageSize, Integer status) {
        List<ProductResponse> resultList = new ArrayList<>();
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Object[]> pageList =  sanPhamRepository.findByStatus(pageable, status);
        for (Object[] result : pageList.getContent()) {
            UUID idSp = (UUID) result[0];
            String imgage = (String) result[1];
            String maSp = (String) result[2];
            String tenSanPham = (String) result[3];
            BigDecimal giaBan = (BigDecimal) result[4];
            Date ngayTao = (Date) result[5];
            Integer trangThai = (Integer) result[6];
            BigDecimal giaGiam = new BigDecimal(getGiaGiamCuoiCung(idSp));

            ProductResponse productResponse = new ProductResponse(idSp, imgage, maSp, tenSanPham, giaBan, ngayTao, trangThai, giaBan.subtract(giaGiam));
            resultList.add(productResponse);
        }
        return resultList;
    }

    public SanPham findByName(String name) {
        SanPham sanPham = sanPhamRepository.findByTenSanPham(name);
        return sanPham;
    }

    @Override
    public SanPham createProduct(ProductRequest productRequest, String username)
            throws CsvValidationException, IOException {
        TaiKhoan taiKhoanUser = taiKhoanRepository.findByUsername(username).orElse(null);
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        Optional<KieuDe> kieuDe = kieuDeRepository.findById(productRequest.getIdKieuDe());
        Optional<XuatXu> xuatXu = xuatSuRepository.findById(productRequest.getIdXuatXu());
        Optional<DanhMuc> danhMuc = danhMucRepository.findById(productRequest.getIdCategory());
        Optional<ThuongHieu> thuongHieu = thuongHieuRepository.findById(productRequest.getIdBrand());
        SanPham sanPham = new SanPham();
        sanPham.setId(UUID.randomUUID());
        sanPham.setNgayTao(timestamp);
        sanPham.setMaSanPham(productRequest.getMaSanPham());
        sanPham.setTenSanPham(productRequest.getProductName());
        sanPham.setMoTa(productRequest.getDescribe());
        sanPham.setGiaBan(productRequest.getPrice());
        sanPham.setDanhMuc(danhMuc.get());
        sanPham.setThuongHieu(thuongHieu.get());
        sanPham.setKieuDe(kieuDe.get());
        sanPham.setXuatXu(xuatXu.get());
        sanPham.setTrangThai(1);
        sanPhamRepository.save(sanPham);
        String thuongHieuInfo = thuongHieu.isPresent() ? thuongHieu.get().getTenThuongHieu() : "";
        String danhMucInfo = danhMuc.isPresent() ? danhMuc.get().getTenDanhMuc() : "";
        String kieuDeInfo = kieuDe.isPresent() ? kieuDe.get().getTenDe() : "";
        String xuatXuInfo = xuatXu.isPresent() ? xuatXu.get().getTenXuatXu() : "";

        auditLogService.writeAuditLogSanPham("Thêm Mới sản phẩm", username, taiKhoanUser.getEmail(), null,
                "Mã : " + productRequest.getMaSanPham() + "," + "Tên :" + productRequest.getProductName()
                        + "," + "Giá bán : " + productRequest.getPrice() + ","
                        + "Mô Tả : " + productRequest.getDescribe() + "," + "Thương Hiệu: " + thuongHieuInfo
                        + "," + "Danh Mục: " + danhMucInfo + "," + "Kiểu Đế: " + kieuDeInfo
                        + "," + "Xuất Xứ: " + xuatXuInfo,
                null, null, null);
        return sanPham;
    }

    @Override
    public MessageResponse updateProduct(ProductRequest productRequest, UUID id) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        Optional<KieuDe> kieuDe = kieuDeRepository.findById(productRequest.getIdKieuDe());
        Optional<XuatXu> xuatXu = xuatSuRepository.findById(productRequest.getIdXuatXu());
        Optional<DanhMuc> danhMuc = danhMucRepository.findById(productRequest.getIdCategory());
        Optional<ThuongHieu> thuongHieu = thuongHieuRepository.findById(productRequest.getIdBrand());
        SanPham sanPham = sanPhamRepository.findById(id).get();
        sanPham.setNgayCapNhat(timestamp);
        sanPham.setMaSanPham(productRequest.getMaSanPham());
        sanPham.setTenSanPham(productRequest.getProductName());
        sanPham.setMoTa(productRequest.getDescribe());
        sanPham.setGiaBan(productRequest.getPrice());
        sanPham.setDanhMuc(danhMuc.get());
        sanPham.setThuongHieu(thuongHieu.get());
        sanPham.setKieuDe(kieuDe.get());
        sanPham.setXuatXu(xuatXu.get());
        sanPham.setTrangThai(1);
        sanPhamRepository.save(sanPham);
        return MessageResponse.builder().message("Update thành công").build();
    }

    public List<SanPhamResponse> getNewProductbyId(UUID id) {
        return sanPhamRepository.getNewProductbyId(id);
    }

    @Override
    public List<SanPhamResponse> getBestSellingProductsbyId(UUID id) {
        return sanPhamRepository.getBestSellingProductsbyId(id);
    }

    @Override
    public ProductUpdateResponse findByProduct(UUID id) {
        return sanPhamRepository.findByProduct(id);
    }

    @Override
    public List<ProductDetailUpdateReponse> findByProductDetail(UUID id) {
        return sanPhamRepository.findByProductDetail(id);
    }

    @Override
    public List<SanPhamResponse> getNewProductbyMoney(BigDecimal key1, BigDecimal key2) {
        return sanPhamRepository.getNewProductbyMoney(key1, key2);
    }

}
