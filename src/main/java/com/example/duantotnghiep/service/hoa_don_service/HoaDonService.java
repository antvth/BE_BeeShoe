package com.example.duantotnghiep.service.hoa_don_service;

import com.example.duantotnghiep.entity.HoaDon;
import com.example.duantotnghiep.request.HoaDonGiaoThanhToanRequest;
import com.example.duantotnghiep.response.*;

import java.util.List;
import java.util.UUID;

public interface HoaDonService {

    List<HoaDonDTOResponse> getAllHoaDonAdmin(Integer trangThaiHD, Integer loaiDon, String tenNhanVien, String ma, String soDienThoai, Integer pageNumber, Integer pageSize);

    List<HoaDonDTOResponse> getAllHoaDonStaff(Integer trangThaiHD, Integer loaiDon, String ma, String soDienThoai, String username, Integer pageNumber, Integer pageSize);

    List<HoaDonDTOResponse> getAllHoaDonCTTStaff(Integer loaiDon, String ma, String soDienThoai, Integer pageNumber, Integer pageSize);

    HoaDon updateHoaDon(UUID hoaDonId,  String username);

    List<NhanVienResponse> nhanVienList(Integer pageNumber, Integer pageSize);

    EmployeeAndInvoiceResponse findById(UUID id);

}
