package com.example.duantotnghiep.service.voucher_service;

import com.example.duantotnghiep.entity.Voucher;
import com.example.duantotnghiep.request.GiamGiaRequest;
import com.example.duantotnghiep.request.VoucherRequest;
import com.example.duantotnghiep.response.GiamGiaResponse;
import com.example.duantotnghiep.response.MessageResponse;
import com.opencsv.exceptions.CsvValidationException;
import org.springframework.data.domain.Page;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface VoucherService {
    List<Voucher> listVoucher(String maGiamGia, String tenGiamGia,Integer trangThai,  Integer pageNumber, Integer pageSize) ;

    MessageResponse updateVoucherstaus(UUID id);
    MessageResponse createVoucher(VoucherRequest createVoucherRequest, String username)
            throws IOException, CsvValidationException;

    MessageResponse updateVoucher(UUID id, VoucherRequest createVoucherRequest, String username)
            throws IOException, CsvValidationException;
    MessageResponse checkAndSetStatus();
    public Voucher findById(UUID id);

    List<Voucher> searchByTrangThai(Integer trangThai);

    public List<Voucher> searchByTenOrMaVoucher(String keyword);
}
