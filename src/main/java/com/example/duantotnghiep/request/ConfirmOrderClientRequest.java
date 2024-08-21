package com.example.duantotnghiep.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class ConfirmOrderClientRequest {

    @NotNull(message = "Tiền ship không được để trống")
    @DecimalMin(value = "0.1", inclusive = false, message = "Tiền ship phải lớn hơn 0")
    private BigDecimal tienShip;

    @NotBlank(message = "Địa chỉ không được để trống")
    private String diaChi;

    @NotBlank(message = "Địa chỉ không được để trống")
    private String hoVatenClient;

    @NotBlank(message = "Email không được để trống")
    @Pattern(regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$", message = "Email không hợp lệ")
    private String email;

    @NotBlank(message = "Số điện thoại không được để trống")
    private String sdtClient;
}
