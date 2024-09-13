package com.example.duantotnghiep.service.thuoc_tinh_dong_san_pham_service;

import com.example.duantotnghiep.entity.Image;
import com.example.duantotnghiep.response.MessageResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

public interface ImageService {

    MessageResponse createImages(List<MultipartFile> files, UUID sanPhamId) throws IOException;

    List<Image> findBySanPham_Id(UUID id);

    void removeImage(UUID id);

    MessageResponse updateImage(UUID idImage, UUID idProduct);
}
