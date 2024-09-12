package com.example.duantotnghiep.service.ban_tai_quay_service.impl;

import com.example.duantotnghiep.entity.SanPhamChiTiet;
import com.example.duantotnghiep.entity.SpGiamGia;
import com.example.duantotnghiep.mapper.ChiTietSanPhamCustom;
import com.example.duantotnghiep.repository.ChiTietSanPhamRepository;
import com.example.duantotnghiep.repository.SpGiamGiaRepository;
import com.example.duantotnghiep.response.ProductShopResponse;
import com.example.duantotnghiep.service.ban_tai_quay_service.ProductCounterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ProductCounterServiceImpl implements ProductCounterService {

    @Autowired
    private ChiTietSanPhamRepository chiTietSanPhamRepository;

    @Autowired
    private SpGiamGiaRepository spGiamGiaRepository;

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

    @Override
    public List<ChiTietSanPhamCustom> getAll(Integer pageNumber, Integer pageSize) {
        List<ChiTietSanPhamCustom> resultList = new ArrayList<>();
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Object[]> queryResult = chiTietSanPhamRepository.getAll(pageable);
        for (Object[] result : queryResult.getContent()) {
            UUID idSp = (UUID) result[0];
            UUID idCtsp = (UUID) result[1];
            String imgage = (String) result[2];
            String tenSanPham = (String) result[3];
            BigDecimal giaBan = (BigDecimal) result[4];
            Integer soLuong = (Integer) result[5];
            String mauSac = (String) result[6];
            Integer size = (Integer) result[7];
            String chatLieu = (String) result[8];
            UUID idThuongHieu = (UUID) result[9];
            BigDecimal giaGiam = new BigDecimal(getGiaGiamCuoiCung(idSp));

            ChiTietSanPhamCustom chiTietSanPhamCustom = new ChiTietSanPhamCustom(
                    idCtsp, imgage, tenSanPham, giaBan, giaBan.subtract(giaGiam), soLuong, mauSac, size, chatLieu, idThuongHieu);
            resultList.add(chiTietSanPhamCustom);
        }
        return resultList;
    }

    public List<ProductShopResponse> getAllShop(Integer pageNumber, Integer pageSize) {
        List<ProductShopResponse> resultList = new ArrayList<>();
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Object[]> queryResult = spGiamGiaRepository.getAllShop(pageable);
        for (Object[] result : queryResult.getContent()) {
            UUID idSp = (UUID) result[0];
            String imgage = (String) result[1];
            String tenSanPham = (String) result[2];
            BigDecimal giaBan = (BigDecimal) result[3];
            BigDecimal giaGiam = new BigDecimal(getGiaGiamCuoiCung(idSp));

            ProductShopResponse productShopResponse = new ProductShopResponse(idSp, imgage, tenSanPham, giaBan, giaBan.subtract(giaGiam));
            resultList.add(productShopResponse);
        }
        return resultList;
    }

    public List<ProductShopResponse> findByShopName(Integer pageNumber, Integer pageSize, String name) {
        List<ProductShopResponse> resultList = new ArrayList<>();
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Object[]> queryResult = spGiamGiaRepository.findByShopName(pageable, name);
        for (Object[] result : queryResult.getContent()) {
            UUID idSp = (UUID) result[0];
            String imgage = (String) result[1];
            String tenSanPham = (String) result[2];
            BigDecimal giaBan = (BigDecimal) result[3];
            BigDecimal giaGiam = new BigDecimal(getGiaGiamCuoiCung(idSp));

            ProductShopResponse productShopResponse = new ProductShopResponse(idSp, imgage, tenSanPham, giaBan, giaBan.subtract(giaGiam));
            resultList.add(productShopResponse);
        }
        return resultList;
    }

    public List<ProductShopResponse> findByGia(Integer pageNumber, Integer pageSize, BigDecimal key1, BigDecimal key2) {
        List<ProductShopResponse> resultList = new ArrayList<>();
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Object[]> queryResult = spGiamGiaRepository.findByGia(pageable, key1, key2);
        for (Object[] result : queryResult.getContent()) {
            UUID idSp = (UUID) result[0];
            String imgage = (String) result[1];
            String tenSanPham = (String) result[2];
            BigDecimal giaBan = (BigDecimal) result[3];
            BigDecimal giaGiam = new BigDecimal(getGiaGiamCuoiCung(idSp));

            ProductShopResponse productShopResponse = new ProductShopResponse(idSp, imgage, tenSanPham, giaBan, giaBan.subtract(giaGiam));
            resultList.add(productShopResponse);
        }
        return resultList;
    }

    public List<ProductShopResponse> filterCategoryShop(Integer pageNumber, Integer pageSize, UUID id) {
        List<ProductShopResponse> resultList = new ArrayList<>();
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Object[]> queryResult = spGiamGiaRepository.filterCategoryShop(pageable, id);
        for (Object[] result : queryResult.getContent()) {
            UUID idSp = (UUID) result[0];
            String imgage = (String) result[1];
            String tenSanPham = (String) result[2];
            BigDecimal giaBan = (BigDecimal) result[3];
            BigDecimal giaGiam = new BigDecimal(getGiaGiamCuoiCung(idSp));

            ProductShopResponse productShopResponse = new ProductShopResponse(idSp, imgage, tenSanPham, giaBan, giaBan.subtract(giaGiam));
            resultList.add(productShopResponse);
        }
        return resultList;
    }

    public List<ProductShopResponse> filterBrandShop(Integer pageNumber, Integer pageSize, UUID id) {
        List<ProductShopResponse> resultList = new ArrayList<>();
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Object[]> queryResult = spGiamGiaRepository.filterBrandShop(pageable, id);
        for (Object[] result : queryResult.getContent()) {
            UUID idSp = (UUID) result[0];
            String imgage = (String) result[1];
            String tenSanPham = (String) result[2];
            BigDecimal giaBan = (BigDecimal) result[3];
            BigDecimal giaGiam = new BigDecimal(getGiaGiamCuoiCung(idSp));

            ProductShopResponse productShopResponse = new ProductShopResponse(idSp, imgage, tenSanPham, giaBan, giaBan.subtract(giaGiam));
            resultList.add(productShopResponse);
        }
        return resultList;
    }

    public List<ProductShopResponse> filterSoleShop(Integer pageNumber, Integer pageSize, UUID id) {
        List<ProductShopResponse> resultList = new ArrayList<>();
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Object[]> queryResult = spGiamGiaRepository.filterSoleShop(pageable, id);
        for (Object[] result : queryResult.getContent()) {
            UUID idSp = (UUID) result[0];
            String imgage = (String) result[1];
            String tenSanPham = (String) result[2];
            BigDecimal giaBan = (BigDecimal) result[3];
            BigDecimal giaGiam = new BigDecimal(getGiaGiamCuoiCung(idSp));

            ProductShopResponse productShopResponse = new ProductShopResponse(idSp, imgage, tenSanPham, giaBan, giaBan.subtract(giaGiam));
            resultList.add(productShopResponse);
        }
        return resultList;
    }

    public List<ProductShopResponse> filterXuatXuShop(Integer pageNumber, Integer pageSize, UUID id) {
        List<ProductShopResponse> resultList = new ArrayList<>();
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Object[]> queryResult = spGiamGiaRepository.filterXuatXuShop(pageable, id);
        for (Object[] result : queryResult.getContent()) {
            UUID idSp = (UUID) result[0];
            String imgage = (String) result[1];
            String tenSanPham = (String) result[2];
            BigDecimal giaBan = (BigDecimal) result[3];
            BigDecimal giaGiam = new BigDecimal(getGiaGiamCuoiCung(idSp));

            ProductShopResponse productShopResponse = new ProductShopResponse(idSp, imgage, tenSanPham, giaBan, giaBan.subtract(giaGiam));
            resultList.add(productShopResponse);
        }
        return resultList;
    }

    public List<ChiTietSanPhamCustom> getSanPhamLienQuan(UUID idthuonghieu, Integer pageNumber, Integer pageSize) {
        List<ChiTietSanPhamCustom> resultList = new ArrayList<>();
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Object[]> queryResult = chiTietSanPhamRepository.getAll(pageable);
        for (Object[] result : queryResult.getContent()) {
            UUID idSp = (UUID) result[0];
            UUID idCtsp = (UUID) result[1];
            String imgage = (String) result[2];
            String tenSanPham = (String) result[3];
            BigDecimal giaBan = (BigDecimal) result[4];
            Integer soLuong = (Integer) result[5];
            String mauSac = (String) result[6];
            Integer size = (Integer) result[7];
            String chatLieu = (String) result[8];
            UUID idThuongHieu = (UUID) result[9];
            BigDecimal giaGiam = new BigDecimal(getGiaGiamCuoiCung(idSp));

            ChiTietSanPhamCustom chiTietSanPhamCustom = new ChiTietSanPhamCustom(
                    idCtsp, imgage, tenSanPham, giaBan, giaBan.subtract(giaGiam), soLuong, mauSac, size, chatLieu, idThuongHieu);
            resultList.add(chiTietSanPhamCustom);
        }
        return resultList;
    }

    @Override
    public ChiTietSanPhamCustom getOne(String name) {
        List<Object[]> resultList = chiTietSanPhamRepository.getOne(name);
        ChiTietSanPhamCustom chiTietSanPhamCustom = null; // Khởi tạo đối tượng ngoài vòng lặp

        if (resultList != null && !resultList.isEmpty()) {
            Object[] result = resultList.get(0);

            UUID idSp = (UUID) result[0];
            UUID idCtsp = (UUID) result[1];
            String imgage = (String) result[2];
            String tenSanPham = (String) result[3];
            BigDecimal giaBan = (BigDecimal) result[4];
            Integer soLuong = (Integer) result[5];
            String mauSac = (String) result[6];
            Integer size = (Integer) result[7];
            String chatLieu = (String) result[8];
            UUID idThuongHieu = (UUID) result[9];
            BigDecimal giaGiam = new BigDecimal(getGiaGiamCuoiCung(idSp));

            chiTietSanPhamCustom = new ChiTietSanPhamCustom(
                    idCtsp, imgage, tenSanPham, giaBan, giaBan.subtract(giaGiam), soLuong, mauSac, size, chatLieu, idThuongHieu);
        }
        return chiTietSanPhamCustom; // Trả về bên ngoài vòng lặp
    }

    @Override
    public List<ChiTietSanPhamCustom> searchByName(Integer pageNumber, Integer pageSize, String name) {
        List<ChiTietSanPhamCustom> resultList = new ArrayList<>();
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Object[]> queryResult = chiTietSanPhamRepository.searchByName(pageable, name);
        for (Object[] result : queryResult.getContent()) {
            UUID idSp = (UUID) result[0];
            UUID idCtsp = (UUID) result[1];
            String imgage = (String) result[2];
            String tenSanPham = (String) result[3];
            BigDecimal giaBan = (BigDecimal) result[4];
            Integer soLuong = (Integer) result[5];
            String mauSac = (String) result[6];
            Integer sizes = (Integer) result[7];
            String chatLieu = (String) result[8];
            UUID idThuongHieu = (UUID) result[9];
            BigDecimal giaGiam = new BigDecimal(getGiaGiamCuoiCung(idSp));

            ChiTietSanPhamCustom chiTietSanPhamCustom = new ChiTietSanPhamCustom(
                    idCtsp, imgage, tenSanPham, giaBan, giaBan.subtract(giaGiam), soLuong, mauSac, sizes, chatLieu, idThuongHieu);
            resultList.add(chiTietSanPhamCustom);
        }
        return resultList;
    }

    @Override
    public List<ChiTietSanPhamCustom> filterBrand(Integer pageNumber, Integer pageSize,
                                                  String tenThuongHieu,
                                                  String tenXuatXu,
                                                  String tenDanhMuc,
                                                  String tenDe,
                                                  String tenChatLieu,
                                                  String tenMauSac,
                                                  Integer size) {
        List<ChiTietSanPhamCustom> resultList = new ArrayList<>();
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Object[]> queryResult = chiTietSanPhamRepository.filterBrand(pageable, tenThuongHieu, tenXuatXu, tenDanhMuc, tenDe, tenChatLieu, tenMauSac, size);
        for (Object[] result : queryResult.getContent()) {
            UUID idSp = (UUID) result[0];
            UUID idCtsp = (UUID) result[1];
            String imgage = (String) result[2];
            String tenSanPham = (String) result[3];
            BigDecimal giaBan = (BigDecimal) result[4];
            Integer soLuong = (Integer) result[5];
            String mauSac = (String) result[6];
            Integer sizes = (Integer) result[7];
            String chatLieu = (String) result[8];
            UUID idThuongHieu = (UUID) result[9];
            BigDecimal giaGiam = new BigDecimal(getGiaGiamCuoiCung(idSp));

            ChiTietSanPhamCustom chiTietSanPhamCustom = new ChiTietSanPhamCustom(
                    idCtsp, imgage, tenSanPham, giaBan, giaBan.subtract(giaGiam), soLuong, mauSac, sizes, chatLieu, idThuongHieu);
            resultList.add(chiTietSanPhamCustom);
        }
        return resultList;
    }

    public List<ChiTietSanPhamCustom> filterBrandId(Integer pageNumber, Integer pageSize, UUID id) {
        List<ChiTietSanPhamCustom> resultList = new ArrayList<>();
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Object[]> queryResult = chiTietSanPhamRepository.filterBrandId(pageable, id);
        for (Object[] result : queryResult.getContent()) {
            UUID idSp = (UUID) result[0];
            UUID idCtsp = (UUID) result[1];
            String imgage = (String) result[2];
            String tenSanPham = (String) result[3];
            BigDecimal giaBan = (BigDecimal) result[4];
            Integer soLuong = (Integer) result[5];
            String mauSac = (String) result[6];
            Integer sizes = (Integer) result[7];
            String chatLieu = (String) result[8];
            UUID idThuongHieu = (UUID) result[9];
            BigDecimal giaGiam = new BigDecimal(getGiaGiamCuoiCung(idSp));

            ChiTietSanPhamCustom chiTietSanPhamCustom = new ChiTietSanPhamCustom(
                    idCtsp, imgage, tenSanPham, giaBan, giaBan.subtract(giaGiam), soLuong, mauSac, sizes, chatLieu, idThuongHieu);
            resultList.add(chiTietSanPhamCustom);
        }
        return resultList;
    }

    @Override
    public List<ChiTietSanPhamCustom> filterCategory(Integer pageNumber, Integer pageSize, String name) {
        List<ChiTietSanPhamCustom> resultList = new ArrayList<>();
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Object[]> queryResult = chiTietSanPhamRepository.filterCategory(pageable, name);
        for (Object[] result : queryResult.getContent()) {
            UUID idSp = (UUID) result[0];
            UUID idCtsp = (UUID) result[1];
            String imgage = (String) result[2];
            String tenSanPham = (String) result[3];
            BigDecimal giaBan = (BigDecimal) result[4];
            Integer soLuong = (Integer) result[5];
            String mauSac = (String) result[6];
            Integer size = (Integer) result[7];
            String chatLieu = (String) result[8];
            UUID idThuongHieu = (UUID) result[9];
            BigDecimal giaGiam = new BigDecimal(getGiaGiamCuoiCung(idSp));

            ChiTietSanPhamCustom chiTietSanPhamCustom = new ChiTietSanPhamCustom(
                    idCtsp, imgage, tenSanPham, giaBan, giaBan.subtract(giaGiam), soLuong, mauSac, size, chatLieu, idThuongHieu);
            resultList.add(chiTietSanPhamCustom);
        }
        return resultList;
    }

    public List<ChiTietSanPhamCustom> filterCategoryId(Integer pageNumber, Integer pageSize, UUID id) {
        List<ChiTietSanPhamCustom> resultList = new ArrayList<>();
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Object[]> queryResult = chiTietSanPhamRepository.filterCategoryId(pageable, id);
        for (Object[] result : queryResult.getContent()) {
            UUID idSp = (UUID) result[0];
            UUID idCtsp = (UUID) result[1];
            String imgage = (String) result[2];
            String tenSanPham = (String) result[3];
            BigDecimal giaBan = (BigDecimal) result[4];
            Integer soLuong = (Integer) result[5];
            String mauSac = (String) result[6];
            Integer size = (Integer) result[7];
            String chatLieu = (String) result[8];
            UUID idThuongHieu = (UUID) result[9];
            BigDecimal giaGiam = new BigDecimal(getGiaGiamCuoiCung(idSp));

            ChiTietSanPhamCustom chiTietSanPhamCustom = new ChiTietSanPhamCustom(
                    idCtsp, imgage, tenSanPham, giaBan, giaBan.subtract(giaGiam), soLuong, mauSac, size, chatLieu, idThuongHieu);
            resultList.add(chiTietSanPhamCustom);
        }
        return resultList;
    }

    @Override
    public List<ChiTietSanPhamCustom> filterSole(Integer pageNumber, Integer pageSize, String name) {
        List<ChiTietSanPhamCustom> resultList = new ArrayList<>();
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Object[]> queryResult = chiTietSanPhamRepository.filterSole(pageable, name);
        for (Object[] result : queryResult.getContent()) {
            UUID idSp = (UUID) result[0];
            UUID idCtsp = (UUID) result[1];
            String imgage = (String) result[2];
            String tenSanPham = (String) result[3];
            BigDecimal giaBan = (BigDecimal) result[4];
            Integer soLuong = (Integer) result[5];
            String mauSac = (String) result[6];
            Integer size = (Integer) result[7];
            String chatLieu = (String) result[8];
            UUID idThuongHieu = (UUID) result[9];
            BigDecimal giaGiam = new BigDecimal(getGiaGiamCuoiCung(idSp));

            ChiTietSanPhamCustom chiTietSanPhamCustom = new ChiTietSanPhamCustom(
                    idCtsp, imgage, tenSanPham, giaBan, giaBan.subtract(giaGiam), soLuong, mauSac, size, chatLieu, idThuongHieu);
            resultList.add(chiTietSanPhamCustom);
        }
        return resultList;
    }

    public List<ChiTietSanPhamCustom> filterSoleId(Integer pageNumber, Integer pageSize, UUID id) {
        List<ChiTietSanPhamCustom> resultList = new ArrayList<>();
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Object[]> queryResult = chiTietSanPhamRepository.filterSoleId(pageable, id);
        for (Object[] result : queryResult.getContent()) {
            UUID idSp = (UUID) result[0];
            UUID idCtsp = (UUID) result[1];
            String imgage = (String) result[2];
            String tenSanPham = (String) result[3];
            BigDecimal giaBan = (BigDecimal) result[4];
            Integer soLuong = (Integer) result[5];
            String mauSac = (String) result[6];
            Integer size = (Integer) result[7];
            String chatLieu = (String) result[8];
            UUID idThuongHieu = (UUID) result[9];
            BigDecimal giaGiam = new BigDecimal(getGiaGiamCuoiCung(idSp));

            ChiTietSanPhamCustom chiTietSanPhamCustom = new ChiTietSanPhamCustom(
                    idCtsp, imgage, tenSanPham, giaBan, giaBan.subtract(giaGiam), soLuong, mauSac, size, chatLieu, idThuongHieu);
            resultList.add(chiTietSanPhamCustom);
        }
        return resultList;
    }

    @Override
    public List<ChiTietSanPhamCustom> filterOrigin(Integer pageNumber, Integer pageSize, String name) {
        List<ChiTietSanPhamCustom> resultList = new ArrayList<>();
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Object[]> queryResult = chiTietSanPhamRepository.filterOrigin(pageable, name);
        for (Object[] result : queryResult.getContent()) {
            UUID idSp = (UUID) result[0];
            UUID idCtsp = (UUID) result[1];
            String imgage = (String) result[2];
            String tenSanPham = (String) result[3];
            BigDecimal giaBan = (BigDecimal) result[4];
            Integer soLuong = (Integer) result[5];
            String mauSac = (String) result[6];
            Integer size = (Integer) result[7];
            String chatLieu = (String) result[8];
            UUID idThuongHieu = (UUID) result[9];
            BigDecimal giaGiam = new BigDecimal(getGiaGiamCuoiCung(idSp));

            ChiTietSanPhamCustom chiTietSanPhamCustom = new ChiTietSanPhamCustom(
                    idCtsp, imgage, tenSanPham, giaBan, giaBan.subtract(giaGiam), soLuong, mauSac, size, chatLieu, idThuongHieu);
            resultList.add(chiTietSanPhamCustom);
        }
        return resultList;
    }

    public List<ChiTietSanPhamCustom> filterOriginId(Integer pageNumber, Integer pageSize, UUID id) {
        List<ChiTietSanPhamCustom> resultList = new ArrayList<>();
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Object[]> queryResult = chiTietSanPhamRepository.filterOriginId(pageable, id);
        for (Object[] result : queryResult.getContent()) {
            UUID idSp = (UUID) result[0];
            UUID idCtsp = (UUID) result[1];
            String imgage = (String) result[2];
            String tenSanPham = (String) result[3];
            BigDecimal giaBan = (BigDecimal) result[4];
            Integer soLuong = (Integer) result[5];
            String mauSac = (String) result[6];
            Integer size = (Integer) result[7];
            String chatLieu = (String) result[8];
            UUID idThuongHieu = (UUID) result[9];
            BigDecimal giaGiam = new BigDecimal(getGiaGiamCuoiCung(idSp));

            ChiTietSanPhamCustom chiTietSanPhamCustom = new ChiTietSanPhamCustom(
                    idCtsp, imgage, tenSanPham, giaBan, giaBan.subtract(giaGiam), soLuong, mauSac, size, chatLieu, idThuongHieu);
            resultList.add(chiTietSanPhamCustom);
        }
        return resultList;
    }

    @Override
    public List<ChiTietSanPhamCustom> filterSize(Integer pageNumber, Integer pageSize, Integer name) {
        List<ChiTietSanPhamCustom> resultList = new ArrayList<>();
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Object[]> queryResult = chiTietSanPhamRepository.filterSize(pageable, name);
        for (Object[] result : queryResult.getContent()) {
            UUID idSp = (UUID) result[0];
            UUID idCtsp = (UUID) result[1];
            String imgage = (String) result[2];
            String tenSanPham = (String) result[3];
            BigDecimal giaBan = (BigDecimal) result[4];
            Integer soLuong = (Integer) result[5];
            String mauSac = (String) result[6];
            Integer ssize = (Integer) result[7];
            String chatLieu = (String) result[8];
            UUID idThuongHieu = (UUID) result[9];
            BigDecimal giaGiam = new BigDecimal(getGiaGiamCuoiCung(idSp));

            ChiTietSanPhamCustom chiTietSanPhamCustom = new ChiTietSanPhamCustom(
                    idCtsp, imgage, tenSanPham, giaBan, giaBan.subtract(giaGiam), soLuong, mauSac, ssize, chatLieu, idThuongHieu);
            resultList.add(chiTietSanPhamCustom);
        }
        return resultList;
    }

    @Override
    public List<ChiTietSanPhamCustom> filterMaterial(Integer pageNumber, Integer pageSize, String name) {
        List<ChiTietSanPhamCustom> resultList = new ArrayList<>();
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Object[]> queryResult = chiTietSanPhamRepository.filterMaterial(pageable, name);
        for (Object[] result : queryResult.getContent()) {
            UUID idSp = (UUID) result[0];
            UUID idCtsp = (UUID) result[1];
            String imgage = (String) result[2];
            String tenSanPham = (String) result[3];
            BigDecimal giaBan = (BigDecimal) result[4];
            Integer soLuong = (Integer) result[5];
            String mauSac = (String) result[6];
            Integer ssize = (Integer) result[7];
            String chatLieu = (String) result[8];
            UUID idThuongHieu = (UUID) result[9];
            BigDecimal giaGiam = new BigDecimal(getGiaGiamCuoiCung(idSp));

            ChiTietSanPhamCustom chiTietSanPhamCustom = new ChiTietSanPhamCustom(
                    idCtsp, imgage, tenSanPham, giaBan, giaBan.subtract(giaGiam), soLuong, mauSac, ssize, chatLieu, idThuongHieu);
            resultList.add(chiTietSanPhamCustom);
        }
        return resultList;
    }

    @Override
    public List<ChiTietSanPhamCustom> filterColor(Integer pageNumber, Integer pageSize, String name) {
        List<ChiTietSanPhamCustom> resultList = new ArrayList<>();
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Object[]> queryResult = chiTietSanPhamRepository.filterColor(pageable, name);
        for (Object[] result : queryResult.getContent()) {
            UUID idSp = (UUID) result[0];
            UUID idCtsp = (UUID) result[1];
            String imgage = (String) result[2];
            String tenSanPham = (String) result[3];
            BigDecimal giaBan = (BigDecimal) result[4];
            Integer soLuong = (Integer) result[5];
            String mauSac = (String) result[6];
            Integer ssize = (Integer) result[7];
            String chatLieu = (String) result[8];
            UUID idThuongHieu = (UUID) result[9];
            BigDecimal giaGiam = new BigDecimal(getGiaGiamCuoiCung(idSp));

            ChiTietSanPhamCustom chiTietSanPhamCustom = new ChiTietSanPhamCustom(
                    idCtsp, imgage, tenSanPham, giaBan, giaBan.subtract(giaGiam), soLuong, mauSac, ssize, chatLieu, idThuongHieu);
            resultList.add(chiTietSanPhamCustom);
        }
        return resultList;
    }

    @Override
    public Integer soLuong(UUID id) {
        SanPhamChiTiet sanPhamChiTiet = chiTietSanPhamRepository.findById(id).get();
        return sanPhamChiTiet.getSoLuong();
    }

}
