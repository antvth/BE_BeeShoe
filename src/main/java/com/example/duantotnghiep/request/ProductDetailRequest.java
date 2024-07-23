package com.example.duantotnghiep.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductDetailRequest {

    @NotNull(message = "Số lượng không được để trống")
    @Min(value = 0, message = "Số lượng không được âm")
    private Integer soLuong;

    @NotNull(message = "Vui lòng chọn chất liệu")
    private UUID idChatLieu;

    private UUID idSanPham;

    @NotNull(message = "Vui lòng chọn size")
    private UUID idSize;

    @NotNull(message = "Vui lòng chọn màu sắc")
    private UUID idMauSac;

}
