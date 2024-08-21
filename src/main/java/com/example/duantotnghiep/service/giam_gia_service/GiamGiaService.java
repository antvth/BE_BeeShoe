package com.example.duantotnghiep.service.giam_gia_service;

import com.example.duantotnghiep.entity.Size;
import com.example.duantotnghiep.request.GiamGiaRequest;
import com.example.duantotnghiep.request.UpdateGiamGiaResquest;
import com.example.duantotnghiep.response.*;
import com.opencsv.exceptions.CsvValidationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public interface GiamGiaService {

        List<GiamGiaResponse> getAll(Integer trangThai, String maGiamGia, String tenGiamGia, String tenSanPham,Date startDate, Integer pageNumber, Integer pageSize) ;

        List<ProductDetailResponse> getAllProduct( String tenGiamGia, Integer pageNumber, Integer pageSize) ;
    List<GiamGiaResponse> findbyValueString(String key);

        List<ProductDetailResponse> findbyProduct(String key);

        List<GiamGiaResponse> findbyValueDate(Date key1);

        List<GiamGiaResponse> findbyValueStatus(Integer key);

    MessageResponse checkAndSetStatus();

        List<ProductDetailResponse> ListSearch(UUID id);

        List<GiamGiaResponse> ListGiamGiaDeatil(UUID id);

        MessageResponse createGiamGia(GiamGiaRequest createKhachRequest, String username)
                        throws IOException, CsvValidationException;

        boolean isTenGiamGiaExists(String tenGiamGia);
     boolean isTenGiamGiaExisted(String tenGiamGia) ;
        boolean checkProductRecordCount(UUID productId);

        MessageResponse updateGiamGia(UUID id, UpdateGiamGiaResquest updateGiamGiaRequest, String username)
                        throws IOException, CsvValidationException;

        MessageResponse updateGiamGiaStaus(UUID id);
}
