package com.example.duantotnghiep.mapper.not_login;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.TemporalType;
import lombok.Data;
import org.springframework.data.jpa.repository.Temporal;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class TaiKhoanDto {
        private String username;
        private String hoTen;
        private String soDienThoai;
        private String email;
        private Date ngaySinh;
        private Boolean gioiTinh;
}
