package com.example.duantotnghiep.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequest {

    @NotBlank(message = "Mã sản phẩm không được để trống")
    private String maSanPham;

    @NotBlank(message = "Tên sản phẩm không được để trống")
    private String productName;

    @NotBlank(message = "Mô tả không được để trống")
    private String describe;

    @NotNull(message = "Giá bán không được để trống")
    @DecimalMin(value = "0.1", inclusive = false, message = "Giá sản phẩm phải lớn hơn 0")
    private BigDecimal price;

    @NotNull(message = "Vui lòng chọn kiểu đế")
    private UUID idKieuDe;

    @NotNull(message = "Vui lòng chọn xuất xứ")
    private UUID idXuatXu;

    @NotNull(message = "Vui lòng chọn thương hiệu")
    private UUID idBrand;

    @NotNull(message = "Vui lòng chọn danh mục")
    private UUID idCategory;

    // Getters and setters
}

