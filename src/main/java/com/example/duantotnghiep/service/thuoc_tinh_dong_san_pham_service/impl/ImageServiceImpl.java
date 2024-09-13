package com.example.duantotnghiep.service.thuoc_tinh_dong_san_pham_service.impl;

import com.example.duantotnghiep.entity.Image;
import com.example.duantotnghiep.entity.SanPham;
import com.example.duantotnghiep.repository.ImageRepository;
import com.example.duantotnghiep.repository.SanPhamRepository;
import com.example.duantotnghiep.response.MessageResponse;
import com.example.duantotnghiep.service.thuoc_tinh_dong_san_pham_service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ImageServiceImpl implements ImageService {

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private SanPhamRepository sanPhamRepository;

    @Override
    @Transactional
    public MessageResponse createImages(List<MultipartFile> files, UUID sanPhamId) throws IOException {
        Optional<SanPham> sanPhamOptional = sanPhamRepository.findById(sanPhamId);

        if (sanPhamOptional.isPresent()) {
            SanPham sanPham = sanPhamOptional.get();
            List<Image> findBySanPham = imageRepository.findBySanPham_Id(sanPhamId);

            boolean hasDefaultImage = findBySanPham.stream().anyMatch(Image::getIsDefault);

            String folderName = "D:\\DATN_BEESHOE\\assets\\ảnh giày"; // Tạo tên thư mục mới dựa trên UUID
//
//            File directory = new File(folderName);
//            if (!directory.exists()) {
//                directory.mkdirs(); // Tạo thư mục mới nếu chưa tồn tại
//            }

            for (int i = 0; i < files.size(); i++) {
                MultipartFile file = files.get(i);
                Image image = new Image();
                image.setId(UUID.randomUUID());
                image.setSanPham(sanPham);
                image.setTrangThai(1);

                if (!hasDefaultImage && i == 0) {
                    image.setIsDefault(true);
                } else {
                    image.setIsDefault(false);
                }

                String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
                Files.copy(file.getInputStream(), Paths.get(folderName, fileName), StandardCopyOption.REPLACE_EXISTING);
                image.setTenImage(fileName);
                imageRepository.save(image);
            }

            return MessageResponse.builder().message("Thành công").build();
        } else {
            return MessageResponse.builder().message("Không tìm thấy sản phẩm").build();
        }
    }

    @Override
    public List<Image> findBySanPham_Id(UUID id) {
        return imageRepository.findBySanPham_Id(id);
    }

    @Override
    public void removeImage(UUID id) {
        imageRepository.deleteById(id);
    }

    @Override
    public MessageResponse updateImage(UUID idImage, UUID idProduct) {
        List<Image> imageList = imageRepository.findBySanPham_Id(idProduct);
        for (Image i : imageList) {
            if (i.getIsDefault() == true) {
                i.setIsDefault(false);
            }
            imageRepository.save(i);
        }
        Optional<Image> image = imageRepository.findById(idImage);
        image.get().setIsDefault(true);
        imageRepository.save(image.get());
        return MessageResponse.builder().message("Update thành công").build();
    }

}
