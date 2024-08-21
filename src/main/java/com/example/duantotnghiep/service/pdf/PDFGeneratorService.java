package com.example.duantotnghiep.service.pdf;

import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.UUID;

public interface PDFGeneratorService {

    void orderCouter(HttpServletResponse response, UUID idHoaDon) throws IOException;

    boolean traHang(UUID id);

}
