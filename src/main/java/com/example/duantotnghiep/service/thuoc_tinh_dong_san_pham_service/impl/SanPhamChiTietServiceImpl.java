package com.example.duantotnghiep.service.thuoc_tinh_dong_san_pham_service.impl;

import com.example.duantotnghiep.entity.*;
import com.example.duantotnghiep.repository.*;
import com.example.duantotnghiep.request.ProductDetailRequest;
import com.example.duantotnghiep.response.MessageResponse;
import com.example.duantotnghiep.response.SanPhamChiTietResponse;
import com.example.duantotnghiep.service.thuoc_tinh_dong_san_pham_service.SanPhamChiTietService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class SanPhamChiTietServiceImpl implements SanPhamChiTietService {

    @Autowired
    private ChiTietSanPhamRepository chiTietSanPhamRepository;

    @Autowired
    private SanPhamRepository sanPhamRepository;

    @Autowired
    private SizeRepository sizeRepository;

    @Autowired
    private MauSacRepository mauSacRepository;

    @Autowired
    private ChatLieuRepository chatLieuRepository;

    @Override
    public List<SanPhamChiTietResponse> getAll(UUID id, Integer pageNumber, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<SanPhamChiTietResponse> sanPhamChiTiet = chiTietSanPhamRepository.getAllSanPhamChiTiet(id, pageable);
        return sanPhamChiTiet.getContent();
    }

    @Override
    public List<SanPhamChiTietResponse> finByTrangThai(UUID id, Integer trangThai, Integer pageNumber, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<SanPhamChiTietResponse> sanPhamChiTiet = chiTietSanPhamRepository.finByTrangThai(id, trangThai, pageable);
        return sanPhamChiTiet.getContent();
    }

    @Override
    public List<SanPhamChiTietResponse> finByKey(UUID id, String key, Integer pageNumber, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<SanPhamChiTietResponse> sanPhamChiTiet = chiTietSanPhamRepository.finByKey(id, key, pageable);
        return sanPhamChiTiet.getContent();
    }

    @Override
    public List<SanPhamChiTietResponse> finBySize(UUID id, Integer size, Integer pageNumber, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<SanPhamChiTietResponse> sanPhamChiTiet = chiTietSanPhamRepository.finBySize(id, size, pageable);
        return sanPhamChiTiet.getContent();
    }

    @Override
    public MessageResponse createProductDetail(UUID idProduct, ProductDetailRequest productDetailRequest) {
        Optional<SanPham> sanPham = sanPhamRepository.findById(idProduct);
        Optional<Size> size = sizeRepository.findById(productDetailRequest.getIdSize());
        Optional<ChatLieu> chatLieu = chatLieuRepository.findById(productDetailRequest.getIdChatLieu());
        Optional<MauSac> mauSac = mauSacRepository.findById(productDetailRequest.getIdMauSac());
        SanPhamChiTiet findByMauSacAndChatLieuAndSize = chiTietSanPhamRepository.findBySpct(size.get().getId(), chatLieu.get().getId(), mauSac.get().getId(), sanPham.get().getId());
        if (findByMauSacAndChatLieuAndSize == null) {
            SanPhamChiTiet sanPhamChiTiet = new SanPhamChiTiet();
            sanPhamChiTiet.setId(UUID.randomUUID());
            sanPhamChiTiet.setSoLuong(productDetailRequest.getSoLuong());
            sanPhamChiTiet.setQrcode(UUID.randomUUID().toString());
            sanPhamChiTiet.setTrangThai(1);
            sanPhamChiTiet.setSanPham(sanPham.get());
            sanPhamChiTiet.setMauSac(mauSac.get());
            sanPhamChiTiet.setSize(size.get());
            sanPhamChiTiet.setChatLieu(chatLieu.get());
            chiTietSanPhamRepository.save(sanPhamChiTiet);
        } else {
            findByMauSacAndChatLieuAndSize.setSoLuong(findByMauSacAndChatLieuAndSize.getSoLuong() + productDetailRequest.getSoLuong());
            chiTietSanPhamRepository.save(findByMauSacAndChatLieuAndSize);
        }
        return MessageResponse.builder().message("Tạo thành công").build();
    }

    @Override
    public MessageResponse updateStatusHuy(UUID id) {
        Optional<SanPhamChiTiet> sanPhamChiTiet = chiTietSanPhamRepository.findById(id);
        sanPhamChiTiet.get().setTrangThai(1);
        chiTietSanPhamRepository.save(sanPhamChiTiet.get());
        return MessageResponse.builder().message("Thay đổi thành công").build();
    }

    @Override
    public MessageResponse updateStatusKichHoat(UUID id) {
        Optional<SanPhamChiTiet> sanPhamChiTiet = chiTietSanPhamRepository.findById(id);
        sanPhamChiTiet.get().setTrangThai(2);
        chiTietSanPhamRepository.save(sanPhamChiTiet.get());
        return MessageResponse.builder().message("Thay đổi thành công").build();
    }

    @Override
    public MessageResponse updateQuantity(UUID id, Integer soLuong) {
        Optional<SanPhamChiTiet> sanPhamChiTiet = chiTietSanPhamRepository.findById(id);
        sanPhamChiTiet.get().setSoLuong(soLuong);
        chiTietSanPhamRepository.save(sanPhamChiTiet.get());
        return MessageResponse.builder().message("Thay đổi thành công").build();
    }
}
