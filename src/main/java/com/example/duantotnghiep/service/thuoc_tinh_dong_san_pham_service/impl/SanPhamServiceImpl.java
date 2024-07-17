package com.example.duantotnghiep.service.thuoc_tinh_dong_san_pham_service.impl;

import com.example.duantotnghiep.service.thuoc_tinh_dong_san_pham_service.SanPhamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

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
//    private AuditLogService auditLogService;

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

///


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
