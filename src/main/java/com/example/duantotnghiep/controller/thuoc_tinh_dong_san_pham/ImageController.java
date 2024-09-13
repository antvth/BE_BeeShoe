package com.example.duantotnghiep.controller.thuoc_tinh_dong_san_pham;

import com.example.duantotnghiep.entity.Image;
import com.example.duantotnghiep.response.MessageResponse;
import com.example.duantotnghiep.service.thuoc_tinh_dong_san_pham_service.impl.ImageServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/images/")
public class ImageController {

    @Autowired
    private ImageServiceImpl imageService;

    @PostMapping("create")
    public ResponseEntity<MessageResponse> createImages(
            @RequestParam("files") List<MultipartFile> files,
            @RequestParam("sanPhamId") UUID sanPhamId) {
        try {
            MessageResponse response = imageService.createImages(files, sanPhamId);
            return ResponseEntity.ok().body(response);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }

    @GetMapping("image/{id}")
    public ResponseEntity<List<Image>> getImage(@PathVariable(name = "id") UUID id) {
        return new ResponseEntity<>(imageService.findBySanPham_Id(id), HttpStatus.OK);
    }

    @DeleteMapping("remove")
    public ResponseEntity<Void> removeImage(@RequestParam(name = "id") UUID id) {
        imageService.removeImage(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("update")
    public ResponseEntity<MessageResponse> removeImage(
            @RequestParam(name = "idImage") UUID idImage,
            @RequestParam(name = "idProduct") UUID idProduct) {
        return new ResponseEntity<>(imageService.updateImage(idImage, idProduct), HttpStatus.OK);
    }
}
