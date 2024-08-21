package com.example.duantotnghiep.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TraHangRequest {

    @NotNull(message = "Số lượng không được để trống")
    private Integer soLuong;

    @NotBlank(message = "Ghi chú không được để trống")
    private String ghiChu;
}
