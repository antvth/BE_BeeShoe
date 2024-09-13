package com.example.duantotnghiep.service.ban_tai_quay_service;

import com.example.duantotnghiep.mapper.ChiTietSanPhamCustom;
import com.example.duantotnghiep.response.DetailQuantityToSizeReponse;
import com.example.duantotnghiep.response.SanPhamGetAllResponse;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface ProductCounterService {

    List<ChiTietSanPhamCustom> getAll(Integer pageNumber, Integer pageSize);

    List<ChiTietSanPhamCustom> getSanPhamLienQuan(UUID idthuonghieu, Integer pageNumber, Integer pageSize);

    ChiTietSanPhamCustom getOne(String name);

    List<ChiTietSanPhamCustom> searchByName(Integer pageNumber, Integer pageSize, String name);

    List<ChiTietSanPhamCustom> filterBrand(Integer pageNumber, Integer pageSize,
                                           String tenThuongHieu,
                                           String tenXuatXu,
                                           String tenDanhMuc,
                                           String tenDe,
                                           String tenChatLieu,
                                           String tenMauSac,
                                           Integer size);

    List<ChiTietSanPhamCustom> filterCategory(Integer pageNumber, Integer pageSize, String name);

    List<ChiTietSanPhamCustom> filterSole(Integer pageNumber, Integer pageSize, String name);

    List<ChiTietSanPhamCustom> filterOrigin(Integer pageNumber, Integer pageSize, String name);

    List<ChiTietSanPhamCustom> filterSize(Integer pageNumber, Integer pageSize, Integer size);

    List<ChiTietSanPhamCustom> filterMaterial(Integer pageNumber, Integer pageSize, String name);

    List<ChiTietSanPhamCustom> filterColor(Integer pageNumber, Integer pageSize, String name);

    Integer soLuong(UUID id);

}
