package com.example.duantotnghiep.mapper.don_hang_khach_hang;

import java.util.UUID;

public interface DonHangKhachHangMap {
       UUID getIdHoaDon();
       String getMaHoaDon();
       UUID getIdSanPham();
       String getTenSanPham();
       String getTenImage();
       String getSize();
       String getTenChatLieu();
       String getTenMauSac();
       Integer getSoLuong();
       String getDonGia();
       String getDonGiaSauGiam();
       String getThanhTien();
       Integer getTongSoLuong();
       Integer getTrangThaiHoaDon();
}
