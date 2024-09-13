package com.example.duantotnghiep.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class XuatXuRequest {

    @NotBlank(message = "Tên xuất xứ không được để trống")
    private String tenXuatXu;

    @NotNull(message = "Vui lòng chọn trạng thái")
    private Integer trangThai;

}
