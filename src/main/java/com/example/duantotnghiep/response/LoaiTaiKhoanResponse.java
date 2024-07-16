package com.example.duantotnghiep.response;

import com.example.duantotnghiep.enums.TypeAccountEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LoaiTaiKhoanResponse {
    private UUID id;

    private TypeAccountEnum tenVaiTro;
}
