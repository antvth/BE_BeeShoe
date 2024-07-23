package com.example.duantotnghiep.service.quan_ly_nhan_vien_khach_hang;

import com.example.duantotnghiep.request.CreateQLKhachHangRequest;
import com.example.duantotnghiep.response.MessageResponse;
import com.example.duantotnghiep.response.QLKhachHangResponse;
import com.opencsv.exceptions.CsvValidationException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

public interface QLKhachHangService {
    List<QLKhachHangResponse> getQLKhachHang(Integer trangThai, String name, String soDienThoai, String maTaiKhoan, Integer pageNumber, Integer pageSize);

    MessageResponse createKhachHang(MultipartFile file, CreateQLKhachHangRequest createQLKhachHangRequest, boolean sendEmail,String username) throws IOException, CsvValidationException;

    QLKhachHangResponse detailKhachHang(UUID id);

    MessageResponse updateKhachHang(MultipartFile file, UUID khachHangId, CreateQLKhachHangRequest createQLKhachHangRequest,String username) throws IOException, CsvValidationException;


}
