package com.example.duantotnghiep.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.logging.log4j.message.Message;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ConfirmOrderDeliver {

    @NotBlank(message = "Tên người giao không được để trống")
    private String tenNguoiGiao;

    @NotBlank(message = "Số điện thoại người giao không được để trống")
    private String soDienThoaiGiao;
}
