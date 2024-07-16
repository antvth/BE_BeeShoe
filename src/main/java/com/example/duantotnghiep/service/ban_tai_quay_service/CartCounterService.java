package com.example.duantotnghiep.service.ban_tai_quay_service;

import java.util.UUID;

public interface CartCounterService {

    MessageResponse updateGioHang(UUID idGioHang, UUID idAccount);
}
