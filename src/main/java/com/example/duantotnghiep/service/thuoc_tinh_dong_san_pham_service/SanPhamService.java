package com.example.duantotnghiep.service.thuoc_tinh_dong_san_pham_service;

import com.example.duantotnghiep.entity.SanPham;
import com.example.duantotnghiep.request.ProductRequest;
import com.example.duantotnghiep.response.MessageResponse;
import com.example.duantotnghiep.response.ProductDetailUpdateReponse;
import com.example.duantotnghiep.response.ProductResponse;
import com.example.duantotnghiep.response.ProductUpdateResponse;
import com.example.duantotnghiep.response.SanPhamResponse;
import com.opencsv.exceptions.CsvValidationException;
import io.jsonwebtoken.io.IOException;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
public interface SanPhamService {
    List<SanPhamResponse> getNewProduct();

    List<SanPhamResponse> getBestSellingProducts();

    List<ProductResponse> findByThuongHieu(Integer pageNumber, Integer pageSize, String value);

    List<ProductResponse> findByKieuDe(Integer pageNumber, Integer pageSize, String value);

    List<ProductResponse> findByXuatXu(Integer pageNumber, Integer pageSize, String value);

    List<ProductResponse> findByDanhMuc(Integer pageNumber, Integer pageSize, String value);

    List<ProductResponse> findByNameOrCode(Integer pageNumber, Integer pageSize, String value);

    List<ProductResponse> getHoaDonByFilter(Integer pageNumber, Integer pageSize);

    List<ProductResponse> findByStatus(Integer pageNumber, Integer pageSize, Integer status);

    SanPham createProduct(ProductRequest productRequest, String username)
            throws IOException, CsvValidationException, java.io.IOException;

    MessageResponse updateProduct(ProductRequest productRequest, UUID id);

    List<SanPhamResponse> getNewProductbyId(UUID id);

    List<SanPhamResponse> getBestSellingProductsbyId(UUID id);

    ProductUpdateResponse findByProduct(UUID id);

    List<ProductDetailUpdateReponse> findByProductDetail(UUID id);

    List<SanPhamResponse> getNewProductbyMoney(@Param("key1") BigDecimal key1, @Param("key2") BigDecimal key2);
}
