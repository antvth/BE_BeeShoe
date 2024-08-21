package com.example.duantotnghiep.service.pdf.impl;

import com.example.duantotnghiep.entity.HinhThucThanhToan;
import com.example.duantotnghiep.entity.HoaDon;
import com.example.duantotnghiep.entity.HoaDonChiTiet;
import com.example.duantotnghiep.repository.HoaDonChiTietRepository;
import com.example.duantotnghiep.repository.HoaDonRepository;
import com.example.duantotnghiep.service.pdf.PDFGeneratorService;
import com.example.duantotnghiep.util.FormatNumber;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PDFGeneratorServiceImpl implements PDFGeneratorService {

    private static final String FONT_ARIAL = "D:\\BE_BeShoe\\lib\\font\\oki.ttf";

    @Autowired
    private HoaDonRepository hoaDonRepository;

    @Autowired
    private HoaDonChiTietRepository hoaDonChiTietRepository;

    /**
     * True or False
     *
     * @param id
     * @return
     */
    @Override
    public boolean traHang(UUID id) {
        List<HoaDonChiTiet> hoaDonChiTiet = hoaDonChiTietRepository.findByHoaDon_Id(id);
        for (HoaDonChiTiet x : hoaDonChiTiet) {
            if (x.getTrangThai() == 7) {
                return true;
            }
        }
        return false;
    }

    /**
     * IN hóa đơn
     *
     * @param response
     * @param idHoaDon
     * @throws IOException
     */
    @Override
    public void orderCouter(HttpServletResponse response, UUID idHoaDon) throws IOException {

        Optional<HoaDon> hoaDon = hoaDonRepository.findById(idHoaDon);

        List<HinhThucThanhToan> hinhThucThanhToanList = hoaDon.get().getHinhThucThanhToanList();

        BigDecimal tongTienKhachTra = BigDecimal.ZERO;
        BigDecimal tienChuyenKhoan = BigDecimal.ZERO;
        BigDecimal tienMat = BigDecimal.ZERO;

        BigDecimal tongTienHoan = BigDecimal.ZERO;

        for (HinhThucThanhToan h : hinhThucThanhToanList) {
            if (h.getPhuongThucThanhToan() == 1 && h.getLoaiHinhThucThanhToan().getTenLoai().equalsIgnoreCase("Khách thanh toán")) {
                tienMat = tienMat.add(h.getTongSoTien());
            }

            if (h.getPhuongThucThanhToan() == 2 && h.getLoaiHinhThucThanhToan().getTenLoai().equalsIgnoreCase("Khách thanh toán")) {
                tienChuyenKhoan = tienChuyenKhoan.add(h.getTongSoTien());
            }

            if (h.getLoaiHinhThucThanhToan().getTenLoai().equalsIgnoreCase("Khách thanh toán")) {
                tongTienKhachTra = tongTienKhachTra.add(h.getTongSoTien());
            }
            if (h.getLoaiHinhThucThanhToan().getTenLoai().equalsIgnoreCase("Nhân viên hoàn tiền")) {
                tongTienHoan = tongTienHoan.add(h.getTongSoTien());
            }
        }

        Document document = new Document(PageSize.A4);
        try {
            PdfWriter.getInstance(document, response.getOutputStream());
            document.open();

            BaseFont bf = BaseFont.createFont(FONT_ARIAL, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            Font fontTitle = new Font(bf, 18, Font.BOLD);
            Font fontParagraph = new Font(bf, 12, Font.BOLD);

            PdfPTable lineTable = new PdfPTable(1);
            lineTable.setWidthPercentage(100);
            PdfPCell lineCell = new PdfPCell(new Phrase(""));
            lineCell.setBorder(Rectangle.BOTTOM);
            lineCell.setBorderWidth(1); // Độ dày của đường gạch
            lineCell.setPaddingBottom(5); // Khoảng cách giữa bảng và đường gạch
            lineCell.setBorderColor(BaseColor.BLACK); // Màu của đường gạch, có thể thay đổi
            lineTable.addCell(lineCell);

            Paragraph paragraph = new Paragraph("BEE SHOE", fontTitle);
            paragraph.setAlignment(Element.ALIGN_CENTER);
            document.add(paragraph);
            document.add(new Paragraph("\n"));
            document.add(lineTable);

            Paragraph paragraph2 = new Paragraph("Số điện thoại: 0971066455", fontParagraph);
            paragraph2.setAlignment(Element.ALIGN_CENTER);
            document.add(paragraph2);

            Paragraph paragraph3 = new Paragraph("Email: beeshoepoly@gmail.com", fontParagraph);
            paragraph3.setAlignment(Element.ALIGN_CENTER);
            document.add(paragraph3);

            Paragraph paragraph4 = new Paragraph("Địa chỉ: Cổng số 1, Tòa nhà FPT Polytechnic, 13 phố Trịnh Văn Bô, phường Phương Canh, quận Nam Từ Liêm, TP Hà Nội", fontParagraph);
            paragraph4.setAlignment(Element.ALIGN_CENTER);
            document.add(paragraph4);

            Paragraph paragraph5 = new Paragraph("Hóa đơn bán hàng", fontTitle);
            paragraph5.setAlignment(Element.ALIGN_CENTER);
            document.add(paragraph5);

            Paragraph paragraph12 = new Paragraph(hoaDon.get().getMa(), fontParagraph);
            paragraph12.setAlignment(Element.ALIGN_CENTER);
            document.add(paragraph12);

            Paragraph paragraph6 = new Paragraph("Ngày mua: " + hoaDon.get().getNgayTao(), fontParagraph);
            paragraph6.setAlignment(Element.ALIGN_LEFT);
            document.add(paragraph6);

            Paragraph paragraph7 = new Paragraph("Khách hàng: " + hoaDon.get().getTenNguoiNhan(), fontParagraph);
            paragraph7.setAlignment(Element.ALIGN_LEFT);
            document.add(paragraph7);

            if (hoaDon.get().getDiaChi() != null) {
                Paragraph paragraph8 = new Paragraph("Địa chỉ: " + hoaDon.get().getDiaChi(), fontParagraph);
                paragraph8.setAlignment(Element.ALIGN_LEFT);
                document.add(paragraph8);
            }

            if (hoaDon.get().getSdtNguoiNhan() != null) {
                Paragraph paragraph9 = new Paragraph("Số điện thoại: " + hoaDon.get().getDiaChi(), fontParagraph);
                paragraph9.setAlignment(Element.ALIGN_LEFT);
                document.add(paragraph9);
            }

            Paragraph paragraph10 = new Paragraph("Nhân viên bán hàng: " + hoaDon.get().getTaiKhoanNhanVien().getName(), fontParagraph);
            paragraph10.setAlignment(Element.ALIGN_LEFT);
            document.add(paragraph10);

            Paragraph paragraph11 = new Paragraph("DANH SÁCH SẢN PHẨM KHÁCH HÀNG MUA", fontParagraph);
            paragraph11.setSpacingBefore(15.0f);
            paragraph11.setAlignment(Element.ALIGN_CENTER);
            document.add(paragraph11);
            document.add(new Paragraph("\n")); // Thêm một dòng trống

            Font fontTableHeader = new Font(bf, 12, Font.BOLD);
            String[] tableHeaders = {"STT", "Sản phẩm", "Số lượng", "Đơn giá", "Thành tiền"};
            PdfPTable table = new PdfPTable(5);
            table.setWidthPercentage(100); // Chiều rộng của bảng sẽ chiếm 100% trên trang PDF
            table.setWidths(new int[]{1, 4, 1, 2, 2}); // Các số trong mảng đại diện cho phần trăm chiều rộng của từng cột
            for (String header : tableHeaders) {
                PdfPCell cell = new PdfPCell(new Phrase(header, fontTableHeader));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);
            }

            List<HoaDonChiTiet> hoaDonChiTietList = hoaDon.get().getHoaDonChiTietList();

            int stt = 1;
            BigDecimal tongTienSanPham = BigDecimal.ZERO;
            for (HoaDonChiTiet hoaDonChiTiet : hoaDonChiTietList) {
                if (hoaDonChiTiet.getTrangThai() != 7) {
                    table.addCell(Integer.toString(stt)); // STT
                    table.addCell(hoaDonChiTiet.getSanPhamChiTiet().getSanPham().getTenSanPham()); // Tên sản phẩm
                    table.addCell(Integer.toString(hoaDonChiTiet.getSoLuong())); // Số lượng
                    table.addCell(FormatNumber.formatBigDecimal(hoaDonChiTiet.getDonGiaSauGiam()) + " VND"); // Đơn giá
                    BigDecimal thanhTien = hoaDonChiTiet.getDonGiaSauGiam().multiply(new BigDecimal(hoaDonChiTiet.getSoLuong()));
                    table.addCell(FormatNumber.formatBigDecimal(thanhTien) + " VND"); // Thành tiền
                    tongTienSanPham = tongTienSanPham.add(thanhTien);
                    stt++;
                }
            }

            Font fontTotal = new Font(bf, 12, Font.BOLDITALIC);
            PdfPCell cellTotalLabel = new PdfPCell(new Phrase("Tổng tiền sản phẩm: " + FormatNumber.formatBigDecimal(tongTienSanPham) + " VND", fontTotal));
            cellTotalLabel.setColspan(5);
            cellTotalLabel.setHorizontalAlignment(Element.ALIGN_RIGHT);
            table.addCell(cellTotalLabel);

            document.add(table);
            document.add(new Paragraph("\n")); // Thêm một dòng trống

            if (traHang(idHoaDon)) {
                Paragraph paragraph21 = new Paragraph("DANH SÁCH SẢN PHẨM KHÁCH HÀNG TRẢ", fontParagraph);
                paragraph21.setSpacingBefore(15.0f);
                paragraph21.setAlignment(Element.ALIGN_CENTER);
                document.add(paragraph21);
                document.add(new Paragraph("\n")); // Thêm một dòng trống

                Font fontTableHeader1 = new Font(bf, 12, Font.BOLD);
                String[] tableHeaders1 = {"STT", "Sản phẩm", "Số lượng", "Đơn giá", "Thành tiền", "Nhân viên xử lý", "Ghi chú"};
                PdfPTable table1 = new PdfPTable(7);
                table1.setWidthPercentage(100); // Chiều rộng của bảng sẽ chiếm 100% trên trang PDF
                table1.setWidths(new float[]{1.5f, 5f, 2f, 3.5f, 2f, 4f, 4f}); // Các số trong mảng đại diện cho phần trăm chiều rộng của từng cột
                table1.setSpacingBefore(10);
                for (String header : tableHeaders1) {
                    PdfPCell cell = new PdfPCell(new Phrase(header, fontTableHeader1));
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    table1.addCell(cell);
                }

                int sttTra = 1;
                BigDecimal tongTienSanPhamTra = BigDecimal.ZERO;
                for (HoaDonChiTiet hoaDonChiTiet : hoaDonChiTietList) {
                    if (hoaDonChiTiet.getTrangThai() == 7) {
                        table1.addCell(Integer.toString(sttTra)); // STT
                        table1.addCell(hoaDonChiTiet.getSanPhamChiTiet().getSanPham().getTenSanPham()); // Tên sản phẩm
                        table1.addCell(Integer.toString(hoaDonChiTiet.getSoLuong())); // Số lượng
                        table1.addCell(FormatNumber.formatBigDecimal(hoaDonChiTiet.getDonGiaSauGiam()) + " VND"); // Đơn giá
                        BigDecimal thanhTien = hoaDonChiTiet.getDonGiaSauGiam().multiply(new BigDecimal(hoaDonChiTiet.getSoLuong()));
                        table1.addCell(FormatNumber.formatBigDecimal(thanhTien) + " VND"); // Thành tiền
                        tongTienSanPhamTra = tongTienSanPhamTra.add(thanhTien);
                        table1.addCell(hoaDon.get().getTaiKhoanNhanVien().getName());
                        table1.addCell(hoaDonChiTiet.getComment());
                        sttTra++;
                    }
                }

                Font fontTotal1 = new Font(bf, 12, Font.BOLDITALIC);
                PdfPCell cellTotalLabel1 = new PdfPCell(new Phrase("Tổng tiền sản phẩm: " + FormatNumber.formatBigDecimal(tongTienSanPhamTra) + " VND", fontTotal1));
                cellTotalLabel1.setColspan(7);
                cellTotalLabel1.setHorizontalAlignment(Element.ALIGN_RIGHT);
                table1.addCell(cellTotalLabel1);

                document.add(table1);
                document.add(new Paragraph("\n")); // Thêm một dòng trống
            }

            if (hoaDon.get().getTienShip() != null) {
                Paragraph paragraph20 = new Paragraph("Tiền ship: " + FormatNumber.formatBigDecimal(hoaDon.get().getTienShip()) + " VND", fontParagraph);
                paragraph20.setAlignment(Element.ALIGN_LEFT);
                document.add(paragraph20);
            }

            Paragraph paragraph19;
            if (hoaDon.get().getTienGiamGia() == null) {
                paragraph19 = new Paragraph("Tiền giảm giá: 0 VND", fontParagraph);
            } else {
                paragraph19 = new Paragraph("Tiền giảm giá: " + FormatNumber.formatBigDecimal(hoaDon.get().getTienGiamGia()) + " VND", fontParagraph);
            }
            paragraph19.setAlignment(Element.ALIGN_LEFT);
            document.add(paragraph19);

            Paragraph paragraph18 = new Paragraph("Tổng tiền phải thanh toán: " + FormatNumber.formatBigDecimal(hoaDon.get().getThanhTien()) + " VND", fontParagraph);
            paragraph18.setAlignment(Element.ALIGN_LEFT);
            document.add(paragraph18);

            if (tongTienKhachTra != BigDecimal.ZERO) {
                Paragraph paragraph13 = new Paragraph("Tổng tiền khách trả: " + FormatNumber.formatBigDecimal(tongTienKhachTra) + " VND", fontParagraph);
                paragraph13.setAlignment(Element.ALIGN_LEFT);
                document.add(paragraph13);

                if (tienMat != BigDecimal.ZERO) {
                    Paragraph paragraph14 = new Paragraph("      +)Tiền Mặt: " + FormatNumber.formatBigDecimal(tienMat) + " VND", fontParagraph);
                    paragraph14.setAlignment(Element.ALIGN_LEFT);
                    document.add(paragraph14);
                }

                if (tienChuyenKhoan != BigDecimal.ZERO) {
                    Paragraph paragraph15 = new Paragraph("      +)Chuyển khoản: " + FormatNumber.formatBigDecimal(tienChuyenKhoan) + " VND", fontParagraph);
                    paragraph15.setAlignment(Element.ALIGN_LEFT);
                    document.add(paragraph15);
                }
            }

            if (tongTienHoan != BigDecimal.ZERO) {
                Paragraph paragraph22 = new Paragraph("Tổng tiền hoàn: " + FormatNumber.formatBigDecimal(tongTienHoan) + " VND", fontParagraph);
                paragraph22.setAlignment(Element.ALIGN_LEFT);
                document.add(paragraph22);
            }

            document.add(new Paragraph("\n")); // Thêm một dòng trống

            Paragraph paragraph17 = new Paragraph("----Cảm ơn quý khách !----", fontParagraph);
            paragraph17.setAlignment(Element.ALIGN_CENTER);
            document.add(paragraph17);

        } catch (DocumentException e) {
            e.printStackTrace();
        } finally {
            if (document != null) {
                document.close();
            }
        }
    }

}
