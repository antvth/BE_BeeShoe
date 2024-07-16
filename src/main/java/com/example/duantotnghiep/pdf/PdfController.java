package com.example.duantotnghiep.pdf;

import com.example.duantotnghiep.service.pdf.impl.PDFGeneratorServiceImpl;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/pdf/")
public class PdfController {

    @Autowired
    private PDFGeneratorServiceImpl pdfGeneratorService;

    @GetMapping("pdf/generate/{idHoaDon}")
    public void generatePDF(HttpServletResponse response,@PathVariable("idHoaDon") UUID idHoaDon) throws IOException {
        response.setContentType("application/pdf");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=pdf_" + currentDateTime + ".pdf";
        response.setHeader(headerKey, headerValue);

        this.pdfGeneratorService.orderCouter(response, idHoaDon);
    }

}
