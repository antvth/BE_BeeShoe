package com.example.duantotnghiep.service.thuoc_tinh_dong_san_pham_service;

import com.example.duantotnghiep.entity.ChatLieu;
import com.example.duantotnghiep.request.ChatLieuRequest;
import com.example.duantotnghiep.response.MessageResponse;
import com.opencsv.exceptions.CsvValidationException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
public interface ChatLieuService {
    List<ChatLieu> getAll();

    List<ChatLieu> getAllChatLieu(Integer trangThai, String tenChatLieu, Integer pageNumber, Integer pageSize);

    ChatLieu getById(UUID id);

    MessageResponse create(ChatLieuRequest request,String username) throws IOException, CsvValidationException;

    MessageResponse update(UUID id, ChatLieuRequest request,String username) throws IOException, CsvValidationException;

    MessageResponse delete(UUID id);
}