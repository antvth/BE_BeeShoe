package com.example.duantotnghiep.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ChatLieuRequest {

    @NotBlank(message = "Tên chất liệu không được để trống")
    private String tenChatLieu;

    @NotNull(message = "Vui lòng chọn trạng thái")
    private Integer trangThai;
}
