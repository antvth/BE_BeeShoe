package com.example.duantotnghiep.controller.thuoc_tinh_dong_san_pham;

import com.example.duantotnghiep.entity.ChatLieu;
import com.example.duantotnghiep.request.ChatLieuRequest;
import com.example.duantotnghiep.response.MessageResponse;
import com.example.duantotnghiep.schedulingtasks.UserExcelExporter;
import com.example.duantotnghiep.service.thuoc_tinh_dong_san_pham_service.impl.ChatLieuServiceImpl;
import com.opencsv.exceptions.CsvValidationException;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.security.Principal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/chat-lieu/")
public class ChatLieuController {

    @Autowired
    private ChatLieuServiceImpl chatLieuService;

    @GetMapping("show")
    public ResponseEntity<List<ChatLieu>> getAllSanPhamGiamGia() {
        return new ResponseEntity<>(chatLieuService.getAll(), HttpStatus.OK);
    }

    @GetMapping("hien-thi")
    public ResponseEntity<List<ChatLieu>> getAllChatLieu(
            @RequestParam(name = "tenChatLieu", required = false) String tenChatLieu,
            @RequestParam(name = "trangThai", required = false) Integer trangThai,
            @RequestParam(name = "pageNumber", defaultValue = "0") Integer pageNumber,
            @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize
    ) {
        return new ResponseEntity<>(chatLieuService.getAllChatLieu(trangThai, tenChatLieu, pageNumber, pageSize), HttpStatus.OK);
    }

    @GetMapping("/users/export/excel")
    public void exportToExcel(HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=users_" + currentDateTime + ".xlsx";
        response.setHeader(headerKey, headerValue);

        List<ChatLieu> listUsers = chatLieuService.getAll();

        UserExcelExporter excelExporter = new UserExcelExporter(listUsers);

        excelExporter.export(response);
    }

    @GetMapping("detail")
    public ChatLieu getDanhMucById(@RequestParam UUID id) {
        return chatLieuService.getById(id);
    }

    @PostMapping("create")
    public ResponseEntity<MessageResponse> createDanhMuc(
            @Valid @RequestBody ChatLieuRequest chatLieuRequest,
            Principal principal) throws IOException, CsvValidationException {
        return new ResponseEntity<>(chatLieuService.create(chatLieuRequest, principal.getName()), HttpStatus.CREATED);
    }

    @PutMapping("update")
    public ResponseEntity<MessageResponse> updateDanhMuc(@RequestParam UUID id, @Valid @RequestBody ChatLieuRequest chatLieuRequest, Principal principal) {
        try {
            MessageResponse response = chatLieuService.update(id, chatLieuRequest, principal.getName());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(MessageResponse.builder().message("Lỗi khi cập nhật").build(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("delete")
    public ResponseEntity<MessageResponse> deleteDanhMuc(@RequestParam UUID id) {
        return new ResponseEntity<>(chatLieuService.delete(id), HttpStatus.OK);
    }

    @GetMapping("/find-by-chat-lieu")
    public ResponseEntity<?> findchatlieu(@RequestParam("chatlieu") String chatlieu) {
        List<ChatLieu> thuocTinhList = chatLieuService.findChatLieu(chatlieu);
        return new ResponseEntity<>(thuocTinhList.size(), HttpStatus.OK);
    }
}
