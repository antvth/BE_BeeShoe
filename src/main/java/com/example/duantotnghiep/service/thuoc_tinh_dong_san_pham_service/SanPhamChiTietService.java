package com.example.duantotnghiep.service.thuoc_tinh_dong_san_pham_service;

import com.example.duantotnghiep.request.ProductDetailRequest;
import com.example.duantotnghiep.response.MessageResponse;
import com.example.duantotnghiep.response.SanPhamChiTietResponse;

import java.util.List;
import java.util.UUID;

public interface SanPhamChiTietService {

    List<SanPhamChiTietResponse> getAll(UUID id, Integer pageNumber, Integer pageSize);

    List<SanPhamChiTietResponse> finByTrangThai(UUID id, Integer trangThai, Integer pageNumber, Integer pageSize);

    List<SanPhamChiTietResponse> finByKey(UUID id, String key, Integer pageNumber, Integer pageSize);

    List<SanPhamChiTietResponse> finBySize(UUID id, Integer size, Integer pageNumber, Integer pageSize);

    MessageResponse createProductDetail(UUID idProduct, ProductDetailRequest productDetailRequest);

    MessageResponse updateStatusHuy(UUID id);

    MessageResponse updateStatusKichHoat(UUID id);

    MessageResponse updateQuantity(UUID id, Integer soLuong);
}
