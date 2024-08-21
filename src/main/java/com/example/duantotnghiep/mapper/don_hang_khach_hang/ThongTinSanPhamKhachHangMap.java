package com.example.duantotnghiep.mapper.don_hang_khach_hang;

import java.util.UUID;

public interface ThongTinSanPhamKhachHangMap {
    UUID getId();

    String getTenImage();

    String getTenSanPham();

    String getDonGia();

    String getDonGiaSauGiam();

    Integer getSoLuong();

    String getSize();

    String getTenMauSac();

    String getTenChatLieu();

    Integer getTrangThai();
}
