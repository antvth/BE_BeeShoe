package com.example.duantotnghiep.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AuditLog {
    private String action;
    private String username;
    private String password;
    private String Id;
    private String Ma;
    private String Ten;
    private String TenKhachHang;
    private String LoaiThanhToan;
    private LocalDateTime timestamp;

}