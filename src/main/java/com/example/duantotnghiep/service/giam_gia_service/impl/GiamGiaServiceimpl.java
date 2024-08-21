package com.example.duantotnghiep.service.giam_gia_service.impl;

import com.example.duantotnghiep.entity.*;
import com.example.duantotnghiep.mapper.ChiTietSanPhamCustom;
import com.example.duantotnghiep.repository.*;
import com.example.duantotnghiep.request.GiamGiaRequest;
import com.example.duantotnghiep.request.UpdateGiamGiaResquest;
import com.example.duantotnghiep.response.*;
import com.example.duantotnghiep.service.audi_log_service.AuditLogService;
import com.example.duantotnghiep.service.giam_gia_service.GiamGiaService;
import com.opencsv.exceptions.CsvValidationException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

@Service
public class GiamGiaServiceimpl implements GiamGiaService {

    @Autowired
    private GiamGiaRepository Repository;

    @Autowired
    private SpGiamGiaRepository spggRepository;

    @Autowired
    private SanPhamRepository spRepository;

    @Autowired
    private SpGiamGiaRepository spGiamGiaRepository;

    @Override
    public List<GiamGiaResponse> getAll(Integer trangThai, String maGiamGia, String tenGiamGia, String tenSanPham, Date startDate,
            Integer pageNumber, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<GiamGiaResponse> pageList = Repository.listGiamGia(maGiamGia, tenGiamGia, tenSanPham,trangThai, startDate, pageable);
        return pageList.getContent();
    }

    @Autowired
    private AuditLogService auditLogService;
    @Autowired
    private TaiKhoanRepository taiKhoanRepository;

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

    public Long countQuantity(UUID id) {
        long count = spGiamGiaRepository.countSpGiamGia(id);
        return count;
    }

    @Override
    public List<ProductDetailResponse> getAllProduct(String tenGiamGia, Integer pageNumber, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Object[]> queryResult = Repository.listProductResponse(tenGiamGia, pageable);

        List<ProductDetailResponse> resultList = new ArrayList<>();
        for (Object[] result : queryResult.getContent()) {
            UUID id = (UUID) result[0];
            String image = (String) result[1];
            String tenSanPham = (String) result[2];
            BigDecimal giaBan = (BigDecimal) result[3];

            // Bổ sung tính toán giảm giá
            BigDecimal giaGiamCuoiCung = new BigDecimal(getGiaGiamCuoiCung(id));

            ProductDetailResponse productDetailResponse = new ProductDetailResponse(
                    id, image, tenSanPham, giaBan , countQuantity(id), giaGiamCuoiCung);

            resultList.add(productDetailResponse);
        }

        return resultList;
    }

    @Override
    @Transactional
    public MessageResponse updateGiamGia(UUID id, UpdateGiamGiaResquest updateGiamGiaRequest, String username)
            throws IOException, CsvValidationException {
        TaiKhoan taiKhoanUser = taiKhoanRepository.findByUsername(username).orElse(null);
        // Kiểm tra xem đối tượng GiamGia có tồn tại không
        GiamGia existingGiamGia = Repository.findById(id).orElse(null);

        if (existingGiamGia != null) {
            // Cập nhật thông tin của đối tượng GiamGia
            existingGiamGia.setMaGiamGia(updateGiamGiaRequest.getMaGiamGia());
            existingGiamGia.setTenGiamGia(updateGiamGiaRequest.getTenGiamGia());
            existingGiamGia.setNgayBatDau(updateGiamGiaRequest.getNgayBatDau());
            existingGiamGia.setNgayKetThuc(updateGiamGiaRequest.getNgayKetThuc());
            existingGiamGia.setHinhThucGiam(updateGiamGiaRequest.getHinhThucGiam());
            existingGiamGia.setTrangThai(updateGiamGiaRequest.getTrangThai());
            Date currentDate = new Date();
            existingGiamGia.setNgayCapNhap(currentDate);
            // Lưu cập nhật vào Repository
            Repository.save(existingGiamGia);
            // Xóa tất cả các liên kết giảm giá của sản phẩm với đối tượng giảm giá cũ
            spggRepository.deleteByGiamGia(existingGiamGia);
            // Lặp qua danh sách sản phẩm mới và tạo các liên kết giảm giá mới
            for (UUID sanPhamId : updateGiamGiaRequest.getIdsanpham()) {
                SanPham sanPham = spRepository.findById(sanPhamId).orElse(null);
                if (sanPham != null) {
                    SpGiamGia spGiamGia = new SpGiamGia();
                    spGiamGia.setId(UUID.randomUUID());
                    spGiamGia.setMucGiam(updateGiamGiaRequest.getMucGiam());
                    spGiamGia.setGiamGia(existingGiamGia);
                    spGiamGia.setSanPham(sanPham);
                    if (updateGiamGiaRequest.getHinhThucGiam() == 1) {
                        // HinhThucGiam = 1
                        // dongia = muc giam
                        BigDecimal dongia = BigDecimal.valueOf(updateGiamGiaRequest.getMucGiam());
                        spGiamGia.setDonGia(sanPham.getGiaBan());
                        spGiamGia.setGiaGiam(dongia);
                        // donGiaKhiGiam = gia ban - dongia
                        BigDecimal donGiaKhiGiam = sanPham.getGiaBan().subtract(dongia);
                        spGiamGia.setDonGiaKhiGiam(donGiaKhiGiam);
                    } else if (updateGiamGiaRequest.getHinhThucGiam() == 2) {
                        // HinhThucGiam = 2
                        // dongia = gia ban x (muc giam / 100)
                        BigDecimal dongia = sanPham.getGiaBan().multiply(
                                BigDecimal.valueOf(updateGiamGiaRequest.getMucGiam()).divide(BigDecimal.valueOf(100)));
                        // donGiaKhiGiam = gia ban -
                        spGiamGia.setDonGia(sanPham.getGiaBan());
                        spGiamGia.setGiaGiam(dongia);
                        BigDecimal donGiaKhiGiam = sanPham.getGiaBan().subtract(dongia);
                        // sanpham.giaban =
                        spGiamGia.setDonGiaKhiGiam(donGiaKhiGiam);
                    }

                    spggRepository.save(spGiamGia);
                    spRepository.save(sanPham);
                } else {
                    // Handle the case where the product is not found
                }
            }
            auditLogService.writeAuditLogKhuyenmai("Sửa Khuyến Mãi", username, taiKhoanUser.getEmail(), null,
                    "Mã :" + updateGiamGiaRequest.getMaGiamGia() + "," + "Tên:" + updateGiamGiaRequest.getTenGiamGia()
                            + "," + "Mức Giảm : " + updateGiamGiaRequest.getMucGiam() + "," + "Hình Thức Giảm : "
                            + updateGiamGiaRequest.getHinhThucGiam() + "," + "Ngày Bắt Đầu : "
                            + updateGiamGiaRequest.getNgayBatDau() + "," + "Ngày Kết Thúc : "
                            + updateGiamGiaRequest.getNgayKetThuc() + "," + "sản phẩm :"
                            + updateGiamGiaRequest.getIdsanpham(),
                    null, null, null);
            return null;
        } else {
            // Handle the case where the discount is not found
            return null;
        }
    }

    @Override
    @Transactional
    public MessageResponse updateGiamGiaStaus(UUID id) {
        GiamGia existingGiamGia = Repository.findById(id).orElse(null);
        if (existingGiamGia != null) {
            if (existingGiamGia.getTrangThai() == 2) {
                // TrangThai = 2: Đang kích hoạt, cập nhật thành TrangThai = 1 và setNgayKetthuc
                // = ngày hôm nay
                existingGiamGia.setTrangThai(1);
                Date currentDate = new Date();
                LocalDate tomorrow = LocalDate.now().plusDays(1);

                // Set giờ, phút, giây là 00:00:00
                LocalDateTime midnight = tomorrow.atStartOfDay();

                // Chuyển đổi thành kiểu Date
                Date midnightDate = Date.from(midnight.atZone(ZoneId.systemDefault()).toInstant());
                if (existingGiamGia.getNgayKetThuc() != null && existingGiamGia.getNgayKetThuc().before(currentDate)) {
                    existingGiamGia.setNgayKetThuc(midnightDate);
                }
                return null;
            } else {
                existingGiamGia.setTrangThai(2);
                // TrangThai không phải là 2, có thể xử lý thêm theo nhu cầu của bạn
                return null;
            }
        } else {
            return null;
        }
    }

    @Override
    public List<GiamGiaResponse> findbyValueString(String key) {
        return null;
    }

    @Override
    public List<ProductDetailResponse> findbyProduct(String key) {
        List<ProductDetailResponse> resultList = new ArrayList<>();
        List<Object[]> queryResult = Repository.ProductDetailResponse(key);
        for (Object[] result : queryResult) {
            UUID id = (UUID) result[0];
            String image = (String) result[1];
            String tenSanPham = (String) result[2];
            BigDecimal giaBan = (BigDecimal) result[3];
            ProductDetailResponse productDetailResponse = new ProductDetailResponse(id, image, tenSanPham, giaBan,
                    countQuantity(id), new BigDecimal(getGiaGiamCuoiCung(id)));
            resultList.add(productDetailResponse);
        }
        return resultList;
    }

    @Override
    public List<GiamGiaResponse> findbyValueDate(Date key1) {
        return null;
    }

    @Override
    public List<GiamGiaResponse> findbyValueStatus(Integer key) {
        return null;
    }

    @Override
    public MessageResponse checkAndSetStatus() {
        List<GiamGia> giamGiaList = Repository.findAll();
        Date currentDate = new Date(); // Ngày hiện tại

        for (GiamGia giamGia : giamGiaList) {
            if (giamGia.getNgayKetThuc().before(currentDate)) {
                // Nếu ngày kết thúc đã qua so với ngày hiện tại
                if (giamGia.getTrangThai() != null && giamGia.getTrangThai() == 1) {
                    // Nếu trạng thái là 1 (đang hoạt động), thì cập nhật trạng thái thành 2 (đã kết
                    // thúc)
                    giamGia.setTrangThai(2);
                    Repository.save(giamGia);
                }
            }
        }
        return null;
    }

    @Override
    public List<ProductDetailResponse> ListSearch(UUID id) {
        List<ProductDetailResponse> resultList = new ArrayList<>();
        List<Object[]> queryResult = Repository.ListSearchDanhMuc(id);
        for (Object[] result : queryResult) {
            UUID idProduct = (UUID) result[0];
            String image = (String) result[1];
            String tenSanPham = (String) result[2];
            BigDecimal giaBan = (BigDecimal) result[3];
            ProductDetailResponse productDetailResponse = new ProductDetailResponse(idProduct, image, tenSanPham,
                    giaBan, countQuantity(id), new BigDecimal(getGiaGiamCuoiCung(id)));
            resultList.add(productDetailResponse);
        }
        return resultList;
    }

    @Override
    public List<GiamGiaResponse> ListGiamGiaDeatil(UUID id) {
        return Repository.listGiamGiaDetail(id);
    }

    @Override
    public MessageResponse createGiamGia(GiamGiaRequest createKhachRequest, String username)
            throws IOException, CsvValidationException {
        // Tạo đối tượng GiamGia
        TaiKhoan taiKhoanUser = taiKhoanRepository.findByUsername(username).orElse(null);
        GiamGia giamGia = new GiamGia();
        giamGia.setId(UUID.randomUUID());
        giamGia.setMaGiamGia(createKhachRequest.getMaGiamGia());
        giamGia.setTenGiamGia(createKhachRequest.getTenGiamGia());
        giamGia.setNgayBatDau(createKhachRequest.getNgayBatDau());
        giamGia.setNgayKetThuc(createKhachRequest.getNgayKetThuc());
        giamGia.setHinhThucGiam(createKhachRequest.getHinhThucGiam());
        Date currentDate = new Date();
        giamGia.setNgayTao(currentDate);
        giamGia.setTrangThai(1);

        for (UUID sanPhamId : createKhachRequest.getIdsanpham()) {
            SanPham sanPham = spRepository.findById(sanPhamId).orElse(null);
            if (sanPham != null) {
                SpGiamGia spGiamGia = new SpGiamGia();
                spGiamGia.setId(UUID.randomUUID());
                spGiamGia.setMucGiam(createKhachRequest.getMucGiam());
                spGiamGia.setGiamGia(giamGia);
                spGiamGia.setSanPham(sanPham);

                if (createKhachRequest.getHinhThucGiam() == 1) {
                    // HinhThucGiam = 1
                    // dongia = muc giam
                    BigDecimal dongia = BigDecimal.valueOf(createKhachRequest.getMucGiam());
                    spGiamGia.setDonGia(sanPham.getGiaBan());
                    spGiamGia.setGiaGiam(dongia);
                    // donGiaKhiGiam = gia ban - dongia
                    BigDecimal donGiaKhiGiam = sanPham.getGiaBan().subtract(dongia);
                    if (donGiaKhiGiam.compareTo(BigDecimal.ZERO) < 0) {
                        spGiamGia.setDonGiaKhiGiam(BigDecimal.ZERO);
                    }else {
                        spGiamGia.setDonGiaKhiGiam(donGiaKhiGiam);
                    }
                } else if (createKhachRequest.getHinhThucGiam() == 2) {
                    // HinhThucGiam = 2
                    // dongia = gia ban x (muc giam / 100)
                    BigDecimal dongia = sanPham.getGiaBan().multiply(
                            BigDecimal.valueOf(createKhachRequest.getMucGiam()).divide(BigDecimal.valueOf(100)));
                    // donGiaKhiGiam = gia ban -
                    spGiamGia.setDonGia(sanPham.getGiaBan());
                    spGiamGia.setDonGia(sanPham.getGiaBan());
                    spGiamGia.setGiaGiam(dongia);
                    BigDecimal donGiaKhiGiam = sanPham.getGiaBan().subtract(dongia);
                    // sanpham.giaban =
                    if (donGiaKhiGiam.compareTo(BigDecimal.ZERO) < 0) {
                        spGiamGia.setDonGiaKhiGiam(BigDecimal.ZERO);
                    }else {
                        spGiamGia.setDonGiaKhiGiam(donGiaKhiGiam);
                    }
                }
                Repository.save(giamGia);
                spggRepository.save(spGiamGia);
                spRepository.save(sanPham);
            } else {
                // Handle the case where the product is not found
            }
        }

        // danh muc
        List<UUID> productIds = Repository.findProductIdsByDanhMucId(createKhachRequest.getIdDanhMuc());

        // Associate each product with the discount
        for (UUID sanPhamId : productIds) {
            SanPham sanPham = spRepository.findById(sanPhamId).orElse(null);
            if (sanPham != null) {
                SpGiamGia spGiamGia = new SpGiamGia();
                spGiamGia.setId(UUID.randomUUID());
                spGiamGia.setMucGiam(createKhachRequest.getMucGiam());
                spGiamGia.setGiamGia(giamGia);
                spGiamGia.setSanPham(sanPham);

                if (createKhachRequest.getHinhThucGiam() == 1) {
                    // HinhThucGiam = 1
                    // dongia = muc giam
                    BigDecimal dongia = BigDecimal.valueOf(createKhachRequest.getMucGiam());
                    spGiamGia.setDonGia(sanPham.getGiaBan());
                    spGiamGia.setGiaGiam(dongia);
                    // donGiaKhiGiam = gia ban - dongia
                    BigDecimal donGiaKhiGiam = sanPham.getGiaBan().subtract(dongia);
                    spGiamGia.setDonGiaKhiGiam(donGiaKhiGiam);
                    sanPham.setGiaBan(donGiaKhiGiam);
                } else if (createKhachRequest.getHinhThucGiam() == 2) {
                    // HinhThucGiam = 2
                    // dongia = gia ban x (muc giam / 100)
                    BigDecimal dongia = sanPham.getGiaBan().multiply(
                            BigDecimal.valueOf(createKhachRequest.getMucGiam()).divide(BigDecimal.valueOf(100)));
                    // donGiaKhiGiam = gia ban -
                    spGiamGia.setDonGia(sanPham.getGiaBan());
                    spGiamGia.setGiaGiam(dongia);
                    BigDecimal donGiaKhiGiam = sanPham.getGiaBan().subtract(dongia);
                    // sanpham.giaban =
                    spGiamGia.setDonGiaKhiGiam(donGiaKhiGiam);
                    sanPham.setGiaBan(donGiaKhiGiam);
                }
                Repository.save(giamGia);
                spggRepository.save(spGiamGia);
                spRepository.save(sanPham);
            } else {
                // Handle the case where the product is not found
            }
        }
        auditLogService.writeAuditLogKhuyenmai("Thêm Khuyến Mãi", username, taiKhoanUser.getEmail(), null,
                "Mã :" + createKhachRequest.getMaGiamGia() + "," + "Tên:" + createKhachRequest.getTenGiamGia() + ","
                        + "Mức Giảm : " + createKhachRequest.getMucGiam() + "," + "Hình Thức Giảm : "
                        + createKhachRequest.getHinhThucGiam() + "," + "Ngày Bắt Đầu : "
                        + createKhachRequest.getNgayBatDau() + "," + "Ngày Kết Thúc : "
                        + createKhachRequest.getNgayKetThuc() + "," + "sản phẩm :" + createKhachRequest.getIdsanpham(),
                null, null, null);
        // Trả về thông báo thành công
        return null;
    }

    @Override
    public boolean checkProductRecordCount(UUID productId) {
        int recordCount = Repository.countByProductId(productId);
        return recordCount < 9;
    }

    @Override
    public boolean isTenGiamGiaExists(String tenGiamGia) {
        return false;
    }

    @Override
    public boolean isTenGiamGiaExisted(String tenGiamGia) {
        return Repository.existsByTenGiamGia(tenGiamGia);
    }

}
