package com.example.duantotnghiep.controller;

import com.example.duantotnghiep.util.QRCodeGenerator;
import com.google.zxing.WriterException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@RestController
@RequestMapping("/api/qrcode/")
public class QRCodeController {

    @GetMapping(value = "generate-product/{data}", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> generateQRCodeProduct(@PathVariable String data) {
        int width = 300;
        int height = 300;
        try {
            BufferedImage image = QRCodeGenerator.generateQRCodeImage(data, width, height);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ImageIO.write(image, "png", outputStream);
            return ResponseEntity.ok(outputStream.toByteArray());
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        } catch (WriterException e) {
            throw new RuntimeException(e);
        }
    }
}
